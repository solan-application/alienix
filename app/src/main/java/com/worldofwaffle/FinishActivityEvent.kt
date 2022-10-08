package com.worldofwaffle

import com.worldofwaffle.eventbus.BaseUnboundViewEvent

class FinishActivityEvent(emitter: Any): BaseUnboundViewEvent() {

    private var finishAffinity = false
    private var result: Any? = null

    init {
        this.emitter = emitter
    }

    companion object {
        fun build(emitter: Any): FinishActivityEvent {
            return FinishActivityEvent(emitter)
        }
    }

    fun finishActivityEvent(): FinishActivityEvent {
        return this
    }

    fun finishAffinity(): FinishActivityEvent {
        finishAffinity = true
        return this
    }

    fun setResult(result: Any?): FinishActivityEvent {
        this.result = result
        return this
    }

    fun isFinishAffinity(): Boolean {
        return finishAffinity
    }
}