package com.worldofwaffle

import android.util.Log
import androidx.databinding.ObservableInt
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.worldofwaffle.database.OrderDetailRoomDatabase
import com.worldofwaffle.database.WaffleMenuDatabase
import javax.inject.Inject

class OrderDetailViewModel @Inject constructor(val menuAdapter: OrderDetailAdapter,
                                               val orderDetailRoomDatabase: OrderDetailRoomDatabase,
                                               val waffleMenuDatabase: WaffleMenuDatabase,
                                               private val orderDetailItemViewModelFactory: OrderDetailItemViewModel.Factory): BaseLifecycleViewModel() {

    val waffleTotalPrice = ObservableInt(0)
    private val comma = ","

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun setDescriptionItems() {
        val waffleMenuItems = waffleMenuItems()
        waffleMenuItems.forEach { it.viewCallbackEmitter = viewCallbackEmitter }
        menuAdapter.setAdapterData(waffleMenuItems)
    }

    private fun waffleMenuItems(): List<OrderDetailItemViewModel> {
        val orderDetailsList = orderDetailRoomDatabase.orderDetailDataModelDao().getAllOrderDetails()

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
                    addOnNames, refreshOrderDetailListener) }
            return waffleMenuItems
        }

        return listOf()
    }

    private val refreshOrderDetailListener: () -> Unit = {
        setDescriptionItems()
    }

    fun addedWaffleTotalPrice(itemPrice: Int) {
        val totalWafflePrice = waffleTotalPrice.get()
        val updatedWafflePrice = totalWafflePrice + itemPrice
        waffleTotalPrice.set(updatedWafflePrice)
    }

    fun removedWaffleTotalPrice(itemPrice: Int) {
        val totalWafflePrice = waffleTotalPrice.get()
        val updatedWafflePrice = totalWafflePrice - itemPrice
        waffleTotalPrice.set(updatedWafflePrice)
    }


}