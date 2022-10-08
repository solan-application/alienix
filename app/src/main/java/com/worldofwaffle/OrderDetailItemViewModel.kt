package com.worldofwaffle

import android.view.View
import android.widget.CheckBox
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.worldofwaffle.database.OrderDetailRoomDatabase
import com.worldofwaffle.eventbus.UnboundViewEventBus
import javax.inject.Inject

class OrderDetailItemViewModel(
    private val eventBus: UnboundViewEventBus,
    private val orderDetailRoomDatabase: OrderDetailRoomDatabase,
    private val orderId: String,
    private val waffleId: String,
    val waffleName: String,
    val wafflePrice: Int,
    private val waffleCount: Int,
    private val isTakeAway: Int,
    private val addOnNames: String,
    private val userOrderId: String,
    private val refreshOrderDetailListener: () -> Unit): BaseLifecycleViewModel() {

    val addOns = ObservableField("")
    var addedItemCount= ObservableInt(0)
    val hasTakeAway = ObservableBoolean(false)

    init {
        addedItemCount.set(waffleCount)
        addOns.set(addOnNames)
        hasTakeAway.set(isTakeAway == 1)
    }

    fun onCheckBoxClicked(view: View) {
        val isChecked = (view as CheckBox).isChecked
        hasTakeAway.set(isChecked)
        if (isChecked) {
            orderDetailRoomDatabase.orderDetailDataModelDao()
                .updateTakeAwayStatue(hasTakeAway = 1, userOrderId = userOrderId,
                    waffleId = waffleId, orderId = orderId)
        } else {
            orderDetailRoomDatabase.orderDetailDataModelDao()
                .updateTakeAwayStatue(hasTakeAway = 0, userOrderId = userOrderId,
                    waffleId =  waffleId, orderId = orderId)
        }
    }

    fun clickAdd() {
        addedItemCount.set(addedItemCount.get() + 1)
        orderDetailRoomDatabase.orderDetailDataModelDao()
            .addItemCount(addedItemCount.get(), userOrderId = userOrderId,
                waffleId = waffleId, orderId = orderId)
    }

    fun clickMinus() {
        if (addedItemCount.get() > 1) {
            addedItemCount.set(addedItemCount.get() - 1)
            orderDetailRoomDatabase.orderDetailDataModelDao()
                .addItemCount(addedItemCount.get(), userOrderId = userOrderId,
                    waffleId = waffleId, orderId = orderId)
        } else {
            orderDetailRoomDatabase.orderDetailDataModelDao().deleteOrderDetail(userOrderId = userOrderId,
                waffleId = waffleId, orderId = orderId)
            refreshOrderDetailListener.invoke()
        }
    }

    class Factory @Inject constructor(private val eventBus: UnboundViewEventBus,
                                      private val orderDetailRoomDatabase: OrderDetailRoomDatabase) {
        fun newInstance(
            orderId: String,
            waffleId: String,
            waffleName: String,
            wafflePrice: Int,
            waffleCount: Int,
            isTakeAway: Int,
            addOnNames: String,
            userOrderId: String,
            refreshOrderDetailListener: () -> Unit): OrderDetailItemViewModel {
            return OrderDetailItemViewModel(eventBus, orderDetailRoomDatabase,
                orderId, waffleId, waffleName, wafflePrice, waffleCount, isTakeAway,
                addOnNames, userOrderId, refreshOrderDetailListener)
        }
    }

}