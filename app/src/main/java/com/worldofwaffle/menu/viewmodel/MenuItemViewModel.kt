package com.worldofwaffle.menu.viewmodel

import androidx.core.util.Pair
import androidx.databinding.ObservableBoolean
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
    val waffleId: String,
    val waffleName: String,
    val wafflePrice: Int): BaseMenuItemViewModel() {

    //val addOns = ObservableField("")
    //val comma = ","
    //val addedItemCount= ObservableInt(0)
    //var tempTotalPriceWithAddOn = 0
    private var addOnItemList: List<BaseWaffleMultipleSelectionItemViewModel> = listOf()
    private val waffleMultipleSelectionViewAdapter = WaffleMultipleSelectionViewAdapter()
    private var userOrderId = ""
    val itemSelected = ObservableBoolean(false)

    init {
        addOnItemList = addOnsDatabase.addOnDao().getAllAddOns().map {
                AddOnItem(it.addOnId, it.addOnName, it.addOnPrice)
        }
        userOrderId = orderDetailRoomDatabase.userOrderIdDao().getUserOrderId().userOrderId
        isOrderExist()
    }

    fun clickAdd() {
            addingAddOnsDialog()
    }

    fun clickRepeat() {
        if (orderDetailRoomDatabase.orderDetailDataModelDao()
                .isOrderExist(userOrderId = userOrderId, waffleId = waffleId)) {
            transientDataProvider.save(RepeatOrderUseCase(userOrderId, waffleId))
            val fragment = RepeatOrderFragment(fragmentDialogOnDismissListener)
            fragment.show(fragmentManager, "repeat_order_bottom_sheet")
        }
    }

    private val fragmentDialogOnDismissListener: () -> Unit = {
        isOrderExist()
    }

    private fun isOrderExist() {
        val orderExistStatus = orderDetailRoomDatabase.orderDetailDataModelDao()
            .getOrderDetail(userOrderId = userOrderId, waffleId = waffleId).isNotEmpty()
        itemSelected.set(orderExistStatus)
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

    private val waffleDialogListener: WaffleDialogListener = object : WaffleDialogListener {
        override fun onButtonClickedAtIndex(index: Int) {
            dismissDialog()
        }

        override fun onMultipleSelection(multipleSelections: List<BaseWaffleMultipleSelectionItemViewModel>) {
            val addOnItems = (multipleSelections as List<AddOnItem>)
                .filter { it.isChecked.get() }
                .map { AddOnItem(it.addOnId, it.addOnName, it.addOnPrice) }
            val orderId = UUID.randomUUID().toString()
            orderDetailRoomDatabase.orderDetailDataModelDao()
                .addOrderedWaffleDetail(OrderDetailEntity(userOrderId = userOrderId,
                    waffleId =  waffleId,
                    orderId = orderId,
                    addedOnItemDetails = addOnItems))
           isOrderExist()
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