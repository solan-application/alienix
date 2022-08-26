package com.worldofwaffle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.worldofwaffle.database.WaffleFillingsDatabase
import javax.inject.Inject

class WaffleFillingsViewModel @Inject constructor(val adapter: WaffleFillingsAdapter,
                                                  private val waffleFillingsDatabase: WaffleFillingsDatabase,
                                                  val waffleFillingsItemViewModelFactory: WaffleFillingsItemViewModel.Factory): BaseLifecycleViewModel() {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        val waffleFillingEntities = waffleFillingsDatabase.waffleFillingsDao().getWaffleFillings()
        val fillingItems = waffleFillingEntities
            .map { waffleFillingsItemViewModelFactory.newInstance(it.fillingId, it.fillingsName) }
        adapter.setAdapterData(fillingItems)
    }

}