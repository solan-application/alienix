package com.worldofwaffle.eventbus

import android.util.Log
import com.worldofwaffle.commondialog.WaffleDialogEvent
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnboundViewEventBus @Inject constructor(){
    private val startActivitySubject: PublishSubject<StartActivityEvent> = PublishSubject.create()
    private val waffleDialogSubject: PublishSubject<WaffleDialogEvent> = PublishSubject.create()


    fun send(event: StartActivityEvent) {
        startActivitySubject.onNext(event)
    }

    fun send(event: WaffleDialogEvent) {
        waffleDialogSubject.onNext(event)
    }

    fun startActivity(viewModel: Any): Observable<StartActivityEvent> {
        return startActivity(viewModel.javaClass)
    }

    private fun startActivity(viewModelClass: Class<*>): Observable<StartActivityEvent> {
        return startActivitySubject.filter { event: StartActivityEvent ->
            fromEmitter(event, viewModelClass) }
    }

    fun fordDialog(viewModel: Any): Observable<WaffleDialogEvent> {
        return fordDialog(viewModel as Class<*>)
    }

    private fun fordDialog(viewModelClass: Class<*>): Observable<WaffleDialogEvent> {
        return waffleDialogSubject.filter { event: WaffleDialogEvent ->
            fromEmitter(event, viewModelClass) }
    }

    private fun fromEmitter(event: BaseUnboundViewEvent, viewModelClass: Class<*>): Boolean {
        return viewModelClass.simpleName == event.emitter?.javaClass?.simpleName.toString()
    }
}