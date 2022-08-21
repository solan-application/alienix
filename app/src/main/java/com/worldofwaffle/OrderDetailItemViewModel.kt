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
    private val addOnNames: String,
    private val refreshOrderDetailListener: () -> Unit): BaseLifecycleViewModel() {

    val addOns = ObservableField("")
    val comma = ","
    var addedItemCount= ObservableInt(0)
    val hasTakeAway = ObservableBoolean(false)
    var tempTotalPriceWithAddOn = 0
   // private var addOnItemList: List<BaseWaffleMultipleSelectionItemViewModel> = listOf()
    //private val waffleMultipleSelectionViewAdapter = WaffleMultipleSelectionViewAdapter()

    init {
        /*addOnItemList =
            mutableListOf(
                AddOnItem("111", "Gems", 20),
                AddOnItem("112", "Ice cream", 20),
                AddOnItem("113", "Choco Chips", 20),
                AddOnItem("114", "Oreo", 20)
            )*/
        addedItemCount.set(waffleCount)
        addOns.set(addOnNames)
    }

    fun onCheckBoxClicked(view: View) {
        val isChecked = (view as CheckBox).isChecked
        hasTakeAway.set(isChecked)
        if (isChecked) {
            orderDetailRoomDatabase.orderDetailDataModelDao().updateTakeAwayStatue(1, waffleId, orderId)
        } else {
            orderDetailRoomDatabase.orderDetailDataModelDao().updateTakeAwayStatue(0, waffleId, orderId)
        }
    }

    fun clickAdd() {
        addedItemCount.set(addedItemCount.get() + 1)
        orderDetailRoomDatabase.orderDetailDataModelDao().addItemCount(addedItemCount.get(), waffleId, orderId)
        /* val buttons = listOf(
             Pair.create(R.string.common_ok_button, DialogButtonTypes.PRIMARY),
             Pair.create(R.string.common_cancel_button, DialogButtonTypes.SECONDARY)
         )
         val event: WaffleDialogEvent = WaffleDialogEvent
             .build(this)
             .setDialogTitle("AddOns")
             .setMultiSelectionAdapter(waffleMultipleSelectionViewAdapter)
             .setMultipleSelectionContents(addOnItemList)
             .setListener(waffleDialogListener)
             .setButtonListWithType(buttons)
         eventBus.send(event)*/
    }

    fun clickMinus() {
        if (addedItemCount.get() > 1) {
            addedItemCount.set(addedItemCount.get() - 1)
            orderDetailRoomDatabase.orderDetailDataModelDao()
                .addItemCount(addedItemCount.get(), waffleId, orderId)
        } else {
            orderDetailRoomDatabase.orderDetailDataModelDao().deleteOrderDetail(waffleId, orderId)
            refreshOrderDetailListener.invoke()
        }

        /*if (addedItemCount.get() != 0) {
            val removedItemPrice = tempTotalPriceWithAddOn.div(addedItemCount.get())
            orderDetailViewModel.removedWaffleTotalPrice(removedItemPrice)
            tempTotalPriceWithAddOn -= removedItemPrice
            addedItemCount.set(addedItemCount.get().minus(1))
        } else {
            addOns.set("")
        }*/
    }

    /*private val waffleDialogListener: WaffleDialogListener = object : WaffleDialogListener {
        override fun onButtonClickedAtIndex(index: Int) {
            dismissDialog()
        }

        override fun onMultipleSelection(multipleSelections: List<BaseWaffleMultipleSelectionItemViewModel>) {
            var addedAddOnPrice = 0
            val addOnNames = (multipleSelections as List<AddOnItem>).filter { it.isChecked.get() }
                .joinToString(comma) {
                    addedAddOnPrice += it.addOnPrice
                    it.addOnName
                }
            addOns.set(addOnNames)
            addedItemCount.set(addedItemCount.get() + 1)
            val addedItemPrice = wafflePrice + addedAddOnPrice
            tempTotalPriceWithAddOn += addedItemPrice
            orderDetailViewModel.addedWaffleTotalPrice(addedItemPrice)
        }
    }*/

    class Factory @Inject constructor(private val eventBus: UnboundViewEventBus,
                                      private val orderDetailRoomDatabase: OrderDetailRoomDatabase) {
        fun newInstance(
            orderId: String,
            waffleId: String,
            waffleName: String,
            wafflePrice: Int,
            waffleCount: Int,
            addOnNames: String,
            refreshOrderDetailListener: () -> Unit): OrderDetailItemViewModel {
            return OrderDetailItemViewModel(eventBus, orderDetailRoomDatabase,
                orderId, waffleId, waffleName, wafflePrice, waffleCount,
                addOnNames, refreshOrderDetailListener)
        }
    }

}