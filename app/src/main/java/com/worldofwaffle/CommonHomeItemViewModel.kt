package com.worldofwaffle

import androidx.databinding.ObservableField
import com.worldofwaffle.eventbus.StartActivityEvent
import com.worldofwaffle.eventbus.UnboundViewEventBus
import javax.inject.Inject

class CommonHomeItemViewModel(
    private val eventBus: UnboundViewEventBus,
    private val transientDataProvider: TransientDataProvider,
    private val itemId: Int
): BaseHomeItemViewModel() {

    val itemName = ObservableField<String>("")

    init {
        if (itemId == ORDER_HISTORY){
            itemName.set("ORDER HISTORY")
        }else if (itemId == WAFFLE_FILLINGS) {
            itemName.set("WAFFLE FILLINGS")
        }
    }

    fun onClick() {
        if (itemId == ORDER_HISTORY){
            transientDataProvider.save(OrderHistoryStatusUseCase(HOME_SCREEN))
            eventBus.send(StartActivityEvent.build(this).activityName(OrderHistoryActivity::class.java))
        }else if (itemId == WAFFLE_FILLINGS) {
            eventBus.send(StartActivityEvent.build(this).activityName(WaffleFillingsActivity::class.java))
        }
    }


    class Factory @Inject constructor(private val eventBus: UnboundViewEventBus,
    private val transientDataProvider: TransientDataProvider) {

        fun newInstance(itemId: Int): CommonHomeItemViewModel {
            return CommonHomeItemViewModel(eventBus, transientDataProvider, itemId)
        }
    }

}