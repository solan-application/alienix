package com.worldofwaffle.menu.viewmodel

import androidx.core.util.Pair
import androidx.fragment.app.FragmentManager
import com.worldofwaffle.R
import com.worldofwaffle.RepeatOrderFragment
import com.worldofwaffle.RepeatOrderUseCase
import com.worldofwaffle.TransientDataProvider
import com.worldofwaffle.commondialog.BaseWaffleMultipleSelectionItemViewModel
import com.worldofwaffle.commondialog.DialogButtonTypes.PRIMARY
import com.worldofwaffle.commondialog.DialogButtonTypes.SECONDARY
import com.worldofwaffle.commondialog.WaffleDialogEvent
import com.worldofwaffle.commondialog.WaffleDialogListener
import com.worldofwaffle.commondialog.WaffleMultipleSelectionViewAdapter
import com.worldofwaffle.database.AddOnsDatabase
import com.worldofwaffle.database.OrderDetailEntity
import com.worldofwaffle.database.OrderDetailRoomDatabase
import com.worldofwaffle.database.WaffleMenuEntity
import com.worldofwaffle.eventbus.UnboundViewEventBus
import com.worldofwaffle.menu.AddOnItem
import java.util.*
import javax.inject.Inject

class MenuItemViewModel(
    private val eventBus: UnboundViewEventBus,
    private val orderDetailRoomDatabase: OrderDetailRoomDatabase,
    private val fragmentManager: FragmentManager,
    private val transientDataProvider: TransientDataProvider,
    private val addOnsDatabase: AddOnsDatabase,
    private val waffleId: String,
    val waffleName: String,
    val wafflePrice: Int
): BaseMenuItemViewModel() {

    //val addOns = ObservableField("")
    //val comma = ","
    //val addedItemCount= ObservableInt(0)
    //var tempTotalPriceWithAddOn = 0
    private var addOnItemList: List<BaseWaffleMultipleSelectionItemViewModel> = listOf()
    private val waffleMultipleSelectionViewAdapter = WaffleMultipleSelectionViewAdapter()

    init {
        addOnItemList = addOnsDatabase.addOnDao().getAllAddOns().map {
                AddOnItem(it.addOnId, it.addOnName, it.addOnPrice)
        }
    }

    fun clickAdd() {
            addingAddOnsDialog()
    }

    fun clickRepeat() {
        if (orderDetailRoomDatabase.orderDetailDataModelDao().isOrderExist(waffleId)) {
            transientDataProvider.save(RepeatOrderUseCase(waffleId))
            val fragment = RepeatOrderFragment()
            fragment.show(fragmentManager, "repeat_order_bottom_sheet")
        }
    }

    private fun addingAddOnsDialog() {
        addOnItemList.map { it.clearCheckedAddOn() }
        val buttons = listOf(
            Pair.create(R.string.common_ok_button, PRIMARY),
            Pair.create(R.string.common_cancel_button, SECONDARY))
        val event: WaffleDialogEvent = WaffleDialogEvent
            .build(this)
            .setDialogTitle("AddOns")
            .setMultiSelectionAdapter(waffleMultipleSelectionViewAdapter)
            .setMultipleSelectionContents(addOnItemList)
            .setListener(waffleDialogListener)
            .setButtonListWithType(buttons)
        eventBus.send(event)
    }

    /*private fun addNewOrRepeatDialog() {
        val buttons = listOf(
            Pair.create(R.string.add_new_button, PRIMARY),
            Pair.create(R.string.repeat_order_btn, SECONDARY)
        )
        val event: WaffleDialogEvent = WaffleDialogEvent
            .build(this)
            .setDialogTitle("Select option")
            .setListener(addNewOrRepeatListener)
            .setButtonListWithType(buttons)
        eventBus.send(event)
    }*/

    /*fun clickMinus() {
        if (addedItemCount.get() != 0) {
            val removedItemPrice = tempTotalPriceWithAddOn.div(addedItemCount.get())
            waffleMenuViewModel.removedWaffleTotalPrice(removedItemPrice)
            tempTotalPriceWithAddOn -= removedItemPrice
            addedItemCount.set(addedItemCount.get().minus(1))
        } else {
            addOns.set("")
        }
    }*/

    /*private val addNewOrRepeatListener: WaffleDialogListener = object: WaffleDialogListener {
        override fun onButtonClickedAtIndex(index: Int) {
            if (index == 0) {
                addingAddOnsDialog()
            }else{
                val existingWaffleCount = orderDetailRoomDatabase.orderDetailDataModelDao().getOrderDetail(waffleId).waffleCount
                orderDetailRoomDatabase.orderDetailDataModelDao().addItemCount(existingWaffleCount+1, waffleId)
            }
        }
    }*/

    private val waffleDialogListener: WaffleDialogListener = object : WaffleDialogListener {
        override fun onButtonClickedAtIndex(index: Int) {
            dismissDialog()
        }

        override fun onMultipleSelection(multipleSelections: List<BaseWaffleMultipleSelectionItemViewModel>) {
            //var addedAddOnPrice = 0
            val addOnItems = (multipleSelections as List<AddOnItem>)
                .filter { it.isChecked.get() }
                .map { AddOnItem(it.addOnId, it.addOnName, it.addOnPrice) }
          //  addOns.set(addOnNames)
            //addedItemCount.set(addedItemCount.get() + 1)
            //val addedItemPrice = wafflePrice + addedAddOnPrice
            //tempTotalPriceWithAddOn += addedItemPrice
           // waffleMenuViewModel.addedWaffleTotalPrice(addedItemPrice)
            val orderId = UUID.randomUUID().toString()
         orderDetailRoomDatabase.orderDetailDataModelDao().addOrderedWaffleDetail(OrderDetailEntity(waffleId, orderId, addOnItems))
        }
    }

    class Factory @Inject constructor(
        private val eventBus: UnboundViewEventBus,
        private val orderDetailRoomDatabase: OrderDetailRoomDatabase,
        private val addOnsDatabase: AddOnsDatabase,
        private val transientDataProvider: TransientDataProvider
    ) {
        fun newInstance(
            waffleMenuEntity: WaffleMenuEntity,
            fragmentManager: FragmentManager
        ): MenuItemViewModel {
            return MenuItemViewModel(eventBus,
                orderDetailRoomDatabase,
                fragmentManager,
                transientDataProvider,
                addOnsDatabase,
                waffleMenuEntity.waffleId,
                waffleMenuEntity.waffleName,
                waffleMenuEntity.wafflePrice)
        }
    }

}