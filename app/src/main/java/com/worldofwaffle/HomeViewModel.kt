package com.worldofwaffle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.worldofwaffle.eventbus.UnboundViewEventBus
import javax.inject.Inject

const val ORDER_HISTORY = 0
const val WAFFLE_FILLINGS = 1

class HomeViewModel @Inject constructor(val eventBus: UnboundViewEventBus,
                                        val adapter: HomeAdapter,
private val commonHomeItemViewModelFactory: CommonHomeItemViewModel.Factory,
private val waffleMixItemViewModelFactory: WaffleMixItemViewModel.Factory): BaseLifecycleViewModel() {


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        val homeItems = listOf(commonHomeItemViewModelFactory.newInstance(ORDER_HISTORY),
            commonHomeItemViewModelFactory.newInstance(WAFFLE_FILLINGS), waffleMixItemViewModelFactory.newInstance())
        homeItems.forEach { it.viewCallbackEmitter = viewCallbackEmitter }
        adapter.setAdapterData(homeItems)
    }
}