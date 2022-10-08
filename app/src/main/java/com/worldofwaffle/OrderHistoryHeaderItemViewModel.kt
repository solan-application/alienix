package com.worldofwaffle

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.worldofwaffle.commondialog.*
import com.worldofwaffle.database.OrderDetailRoomDatabase
import com.worldofwaffle.database.OrderHistoryDatabase
import com.worldofwaffle.eventbus.UnboundViewEventBus
import javax.inject.Inject

class OrderHistoryHeaderItemViewModel(
    private val orderedHistoryHeader: OrderedHistoryHeader,
    private val orderHistoryDatabase: OrderHistoryDatabase,
    private val eventBus: UnboundViewEventBus,
    private val orderDetailRoomDatabase: OrderDetailRoomDatabase,
    private val refreshOrderHistoryListener: (userId: String) -> Unit)
    : BaseOrderHistoryViewModel(orderedHistoryHeader.userOrderId){

    val userOrderId = ObservableField("")
    val itemTotalPrice = ObservableField("")
    val paidAndDeliveredStatus = ObservableField("")
    val hasOrderDelivered = ObservableBoolean(true)
    private var paidStatusList: List<BaseWaffleSingleSelectionItemViewModel> = listOf()
    private val waffleSingleSelectionViewAdapter = WaffleSingleSelectionViewAdapter()


    init {
        userOrderId.set(orderedHistoryHeader.userOrderId)
        itemTotalPrice.set("Total: "+ orderedHistoryHeader.itemTotalPrice)
        when(orderHistoryDatabase.orderStateDao().getSingleOrderHistory(orderedHistoryHeader.userOrderId).paidStatus) {
            0 ->  paidAndDeliveredStatus.set("Yet to Pay")
           else ->  when(orderHistoryDatabase.orderStateDao().getSingleOrderHistory(orderedHistoryHeader.userOrderId).deliveredStatus) {
               0 -> paidAndDeliveredStatus.set("Delivery In Progress")
               else -> {
                   paidAndDeliveredStatus.set("DELIVERED")
                   hasOrderDelivered.set(false)
               }
           }
        }
    }

    private fun paymentModeDialog() {
        paidStatusList = listOf(WaffleSIngleSelectionItemViewModel("221","cash"),
            WaffleSIngleSelectionItemViewModel("222","upi"),
            WaffleSIngleSelectionItemViewModel("223","zomato"),
            WaffleSIngleSelectionItemViewModel("224","zaaroz"))
        /*val buttons = listOf(
            Pair.create(R.string.common_ok_button, DialogButtonTypes.PRIMARY),
            Pair.create(R.string.common_cancel_button, DialogButtonTypes.SECONDARY))*/
        val event: WaffleDialogEvent = WaffleDialogEvent
            .build(this)
            .setDialogTitle("PAYMENT MODE")
            .setSingleSelectionAdapter(waffleSingleSelectionViewAdapter)
            .setSingleSelectionContents(paidStatusList)
            .setListener(paymentDialogListener)
        eventBus.send(event)
    }

    private val paymentDialogListener = object : WaffleDialogListener {
        override fun onSingleSelection(singleSelection: BaseWaffleSingleSelectionItemViewModel) {
            paidAndDeliveredStatus.set("Deliver In Progress")
            orderHistoryDatabase.orderStateDao().updatePaidStatus(getPaidStatus(singleSelection.id), orderedHistoryHeader.userOrderId)
        }
    }

    private fun getPaidStatus(id: String) = when(id) {
        "221" -> 1
        "222" -> 2
        "223" -> 3
        "224" -> 4
        else -> 0
    }

    fun onClickDeliveredOrder() {
        if (orderHistoryDatabase.orderStateDao().getSingleOrderHistory(orderedHistoryHeader.userOrderId).paidStatus == 0 ) {
            paymentModeDialog()
        }else {
            orderDetailRoomDatabase.orderDetailDataModelDao().deleteAllOrderDetail(orderedHistoryHeader.userOrderId)
            orderHistoryDatabase.orderStateDao()
                .updateDeliveredOrderStatus(1, orderedHistoryHeader.userOrderId)
            refreshOrderHistoryListener.invoke(orderedHistoryHeader.userOrderId)
        }
    }

    class Factory @Inject constructor(private val orderHistoryDatabase: OrderHistoryDatabase,
                                      private val orderDetailRoomDatabase: OrderDetailRoomDatabase,
                                      private val eventBus: UnboundViewEventBus
    ) {
        fun newInstance(orderedHistoryHeader: OrderedHistoryHeader,
            refreshOrderHistoryListener: (userId: String) -> Unit): OrderHistoryHeaderItemViewModel {
            return OrderHistoryHeaderItemViewModel(orderedHistoryHeader, orderHistoryDatabase, eventBus,
               orderDetailRoomDatabase, refreshOrderHistoryListener)
        }
    }
}