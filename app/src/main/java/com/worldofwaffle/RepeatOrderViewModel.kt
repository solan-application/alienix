package com.worldofwaffle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.worldofwaffle.database.OrderDetailRoomDatabase
import com.worldofwaffle.database.WaffleMenuDatabase
import javax.inject.Inject

class RepeatOrderViewModel @Inject constructor(val menuAdapter: OrderDetailAdapter,
                                               val orderDetailRoomDatabase: OrderDetailRoomDatabase,
                                               val waffleMenuDatabase: WaffleMenuDatabase,
                                               private val transientDataProvider: TransientDataProvider,
                                               private val orderDetailItemViewModelFactory: OrderDetailItemViewModel.Factory)
    : BaseLifecycleViewModel() {

    private val comma = ","
    private var waffleId = ""
    private var userOrderId = ""

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        if (transientDataProvider.containsUseCase(RepeatOrderUseCase::class.java)) {
            val repeatOrderUseCase = transientDataProvider.remove(RepeatOrderUseCase::class.java)
            userOrderId = repeatOrderUseCase.userOrderId
            waffleId = repeatOrderUseCase.waffleId
            setDescriptionItems()
        }
    }

    private fun setDescriptionItems() {
        val waffleMenuItems = waffleMenuItems()
        menuAdapter.setAdapterData(waffleMenuItems)
    }

    private fun waffleMenuItems(): List<OrderDetailItemViewModel> {
        if (waffleId.isNotEmpty()) {
            val waffleMenuItems = orderDetailRoomDatabase.orderDetailDataModelDao().getOrderDetail(userOrderId,
                waffleId).map {
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
                    refreshOrderDetailListener)
            }
            return waffleMenuItems
        }
        return listOf()
    }

    private val refreshOrderDetailListener: () -> Unit = {
        setDescriptionItems()
    }
}