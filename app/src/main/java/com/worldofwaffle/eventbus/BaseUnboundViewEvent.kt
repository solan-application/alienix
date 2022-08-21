package com.worldofwaffle.eventbus


abstract class BaseUnboundViewEvent {
    var emitter: Any? = null
        protected set
}