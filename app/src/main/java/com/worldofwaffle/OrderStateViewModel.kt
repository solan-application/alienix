package com.worldofwaffle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.worldofwaffle.database.OrderHistoryDatabase
import com.worldofwaffle.database.OrderHistoryEntity
import com.worldofwaffle.database.WaffleMenuDatabase
import javax.inject.Inject

class OrderStateViewModel @Inject constructor(
    private val transientDataProvider: TransientDataProvider,
    private val menuDatabase: WaffleMenuDatabase,
    val adapter: OrderStateAdapter,
    private val orderHistoryDatabase: OrderHistoryDatabase,
    private val orderHistoryHeaderItemViewModelFactory: OrderHistoryHeaderItemViewModel.Factory
): BaseLifecycleViewModel() {

    private val comma = ","
    private var allUndeliveredHistoryItemViewModelList: MutableList<BaseOrderHistoryViewModel> = arrayListOf()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {

        if (transientDataProvider.containsUseCase(OrderDetailUseCase::class.java)) {
            val orderDetailList = transientDataProvider.remove(OrderDetailUseCase::class.java).orderDetailEntityList
            var userOrderId = ""
            var totalItemTotalPrice = 0
            var hasTakeAway = 0
            val orderedHistoryDetail = orderDetailList.map {
                userOrderId = userOrderId.plus("UserID: ").plus(it.userOrderId.takeLast(8))
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
            val orderedHistoryHeader = OrderedHistoryHeader(userOrderId, totalItemTotalPrice.toString())
            orderHistoryDatabase.orderStateDao().addOrderHistoryDetail(OrderHistoryEntity(0, userOrderId, orderedHistoryHeader,
                orderedHistoryDetail,0, 0))
        }
        val unDeliveredOrderHistory = orderHistoryDatabase.orderStateDao().getUnDeliveredOrderHistory()
        if (unDeliveredOrderHistory.isNotEmpty()) {
            unDeliveredOrderHistory.map {
                allUndeliveredHistoryItemViewModelList.addAll(it.orderHistoryDetail.map { orderedHistoryDetail ->  OrderHistoryDetailItemViewModel(orderedHistoryDetail) })
                allUndeliveredHistoryItemViewModelList.add(orderHistoryHeaderItemViewModelFactory.newInstance(it.orderHistoryHeader, refreshOrderHistoryListener))
            }
        }
        adapter.setAdapterData(allUndeliveredHistoryItemViewModelList)

    }

    private val refreshOrderHistoryListener: (userId: String) -> Unit = { userId ->
        allUndeliveredHistoryItemViewModelList.removeIf { orderHistory -> userId == orderHistory.userId }
        adapter.setAdapterData(allUndeliveredHistoryItemViewModelList)
    }
}