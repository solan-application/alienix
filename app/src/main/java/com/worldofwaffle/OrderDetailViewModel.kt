package com.worldofwaffle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.worldofwaffle.database.OrderDetailRoomDatabase
import com.worldofwaffle.database.WaffleMenuDatabase
import javax.inject.Inject

class OrderDetailViewModel @Inject constructor(val menuAdapter: OrderDetailAdapter,
                                               val orderDetailRoomDatabase: OrderDetailRoomDatabase,
                                               val waffleMenuDatabase: WaffleMenuDatabase,
                                               private val orderDetailItemViewModelFactory: OrderDetailItemViewModel.Factory,
private val userOrderIdUtil: UserOrderIdUtil): BaseLifecycleViewModel() {

    private val comma = ", "

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun setDescriptionItems() {
        val waffleMenuItems = waffleMenuItems()
        waffleMenuItems.forEach { it.viewCallbackEmitter = viewCallbackEmitter }
        menuAdapter.setAdapterData(waffleMenuItems)
    }

    private fun waffleMenuItems(): List<OrderDetailItemViewModel> {
        val userOrderId = userOrderIdUtil.getUserOrderId()
        val orderDetailsList = orderDetailRoomDatabase.orderDetailDataModelDao().getAllOrderDetails(userOrderId)

        if (orderDetailsList.isNotEmpty()) {
           val waffleMenuItems = orderDetailsList.map {
                val waffleDetail = waffleMenuDatabase.waffleMenuDao().getWaffleDetail(it.waffleId)
                val addOnNames = it.addedOnItemDetails.joinToString(comma) { addOnItem -> addOnItem.addOnName }
                orderDetailItemViewModelFactory.newInstance(
                    it.orderId,
                    it.waffleId,
                    waffleDetail.waffleName,
                    waffleDetail.wafflePrice,
                    it.waffleCount,
                    it.hasTakeAway,
                    addOnNames,
                    userOrderId,
                    refreshOrderDetailListener) }
            return waffleMenuItems
        }

        return listOf()
    }

    private val refreshOrderDetailListener: () -> Unit = {
        setDescriptionItems()
    }
}