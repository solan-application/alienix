package com.worldofwaffle

import com.worldofwaffle.database.OrderDetailRoomDatabase
import com.worldofwaffle.eventbus.StartActivityEvent
import com.worldofwaffle.eventbus.UnboundViewEventBus
import javax.inject.Inject

class OrderHistoryEditItemViewModel(private val orderHistoryEdit: OrderHistoryEdit,
                                    private val orderDetailRoomDatabase: OrderDetailRoomDatabase,
private val eventBus: UnboundViewEventBus)
    : BaseOrderHistoryViewModel(orderHistoryEdit.userOrderId)  {


    fun onClickEdit() {
        orderDetailRoomDatabase.userOrderIdDao().updateUserOrderId(orderHistoryEdit.userOrderId)
        eventBus.send(StartActivityEvent.build(this).activityName(OrderEditActivity::class.java))
    }

    class Factory @Inject constructor(private val eventBus: UnboundViewEventBus,
    private val orderDetailRoomDatabase: OrderDetailRoomDatabase) {
        fun newInstance(orderHistoryEdit: OrderHistoryEdit): OrderHistoryEditItemViewModel {
            return OrderHistoryEditItemViewModel(orderHistoryEdit, orderDetailRoomDatabase,
                eventBus)
        }
    }
}