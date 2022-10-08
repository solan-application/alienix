package com.worldofwaffle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.worldofwaffle.database.OrderDetailEntity
import com.worldofwaffle.database.OrderHistoryDatabase
import com.worldofwaffle.database.OrderHistoryEntity
import com.worldofwaffle.database.WaffleMenuDatabase
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

const val HOME_SCREEN = 1
const val DASHBOARD_SCREEN = 2

class OrderHistoryViewModel @Inject constructor(
    private val transientDataProvider: TransientDataProvider,
    private val menuDatabase: WaffleMenuDatabase,
    val adapter: OrderHistoryAdapter,
    private val orderHistoryDatabase: OrderHistoryDatabase,
    private val orderHistoryEditItemViewModelFactory: OrderHistoryEditItemViewModel.Factory,
    private val orderHistoryHeaderItemViewModelFactory: OrderHistoryHeaderItemViewModel.Factory
): BaseLifecycleViewModel() {

    private val comma = ", "
    private var allUndeliveredHistoryItemViewModelList: MutableList<BaseOrderHistoryViewModel> = arrayListOf()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {

        if (transientDataProvider.containsUseCase(OrderDetailUseCase::class.java)) {
            val orderDetailList = transientDataProvider.remove(OrderDetailUseCase::class.java).orderDetailEntityList
            val detailedOrderHistory = getDetailedOrderHistory(orderDetailList)

            val orderHistoryEdit = OrderHistoryEdit(detailedOrderHistory.userOrderId)
            val orderedHistoryHeader = OrderedHistoryHeader(detailedOrderHistory.userOrderId,
                detailedOrderHistory.totalItemTotalPrice.toString())

            orderHistoryDatabase.orderStateDao().addOrderHistoryDetail(OrderHistoryEntity(userId = detailedOrderHistory.userOrderId,
                dateOfOrder = getCurrentDate(),
                timeOfOrder = getCurrentTime(),
                orderHistoryEdit = orderHistoryEdit,
                orderHistoryHeader = orderedHistoryHeader,
                orderHistoryDetail = detailedOrderHistory.orderHistoryDetail))
        }
      addOrderHistory()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (transientDataProvider.containsUseCase(OrderEditStatusUseCase::class.java)) {
            val orderEditStatusUseCase = transientDataProvider.remove(OrderEditStatusUseCase::class.java)
            allUndeliveredHistoryItemViewModelList.clear()
            val detailedOrderHistory = getDetailedOrderHistory(orderEditStatusUseCase.orderDetailEntityList)
            val orderHistoryEdit = OrderHistoryEdit(detailedOrderHistory.userOrderId)
            val orderedHistoryHeader = OrderedHistoryHeader(detailedOrderHistory.userOrderId,
                detailedOrderHistory.totalItemTotalPrice.toString())
            val oldOrderHistory = orderHistoryDatabase.orderStateDao().getSingleOrderHistory(detailedOrderHistory.userOrderId)
            orderHistoryDatabase.orderStateDao().updateEditedOrderHistory(OrderHistoryEntity(
                id = oldOrderHistory.id,
                userId = detailedOrderHistory.userOrderId,
                dateOfOrder = getCurrentDate(),
                timeOfOrder = getCurrentTime(),
                orderHistoryEdit = orderHistoryEdit,
                orderHistoryHeader = orderedHistoryHeader,
                orderHistoryDetail = detailedOrderHistory.orderHistoryDetail,
            paidStatus = oldOrderHistory.paidStatus))
            addOrderHistory()
        }
    }

    private fun getDetailedOrderHistory(orderDetailEntity: List<OrderDetailEntity>): DetailedOrderHistory {
        var userOrderId = ""
        var totalItemTotalPrice = 0
        var hasTakeAway = 0
        val orderedHistoryDetail = orderDetailEntity.map {
            //userOrderId = "UserID: "+it.userOrderId.takeLast(8)
            userOrderId = it.userOrderId
            var itemTotalPrice  = 0
            val waffleDetail = menuDatabase.waffleMenuDao().getWaffleDetail(it.waffleId)
            itemTotalPrice += waffleDetail.wafflePrice
            hasTakeAway = if (it.hasTakeAway == 1) { 5 }else{ 0 }
            val addOnNames = it.addedOnItemDetails.joinToString(comma) {
                    addOnItem ->
                itemTotalPrice += addOnItem.addOnPrice
                addOnItem.addOnName
            }
            totalItemTotalPrice += (itemTotalPrice + hasTakeAway) * it.waffleCount
            OrderedHistoryDetail(userOrderId, waffleDetail.waffleName, addOnNames,
                it.hasTakeAway == 1, it.waffleCount.toString())
        }

        return DetailedOrderHistory(userOrderId = userOrderId,
        totalItemTotalPrice = totalItemTotalPrice,
        orderHistoryDetail = orderedHistoryDetail)
    }

    private fun addOrderHistory() {
        val unDeliveredOrderHistory = if (transientDataProvider.containsUseCase(OrderHistoryStatusUseCase::class.java)
            && transientDataProvider.remove(OrderHistoryStatusUseCase::class.java).screenId == HOME_SCREEN) {
            orderHistoryDatabase.orderStateDao().getOrderHistory(getCurrentDate())
        }else {
            orderHistoryDatabase.orderStateDao().getDeliveredOrderHistory(0)
        }
        if (unDeliveredOrderHistory.isNotEmpty()) {
            unDeliveredOrderHistory.map {
                allUndeliveredHistoryItemViewModelList.add(orderHistoryEditItemViewModelFactory.newInstance(it.orderHistoryEdit))
                allUndeliveredHistoryItemViewModelList.addAll(it.orderHistoryDetail.map { orderedHistoryDetail ->  OrderHistoryDetailItemViewModel(orderedHistoryDetail) })
                allUndeliveredHistoryItemViewModelList.add(orderHistoryHeaderItemViewModelFactory.newInstance(it.orderHistoryHeader, refreshOrderHistoryListener))
            }
        }
        adapter.setAdapterData(allUndeliveredHistoryItemViewModelList)

    }

    private fun getCurrentDate() = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

    private fun getCurrentTime(): String {
        val d = Date()
        val sdf = SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(Date())
        return sdf.format(d)
    }

    private val refreshOrderHistoryListener: (userId: String) -> Unit = { userId ->
        allUndeliveredHistoryItemViewModelList.removeIf { orderHistory -> userId == orderHistory.userId }
        adapter.setAdapterData(allUndeliveredHistoryItemViewModelList)
    }

    override fun onBackPressed(): Boolean {
        transientDataProvider.save(OrderHistoryBackUseCase(true))
        return super.onBackPressed()
    }
}