package com.worldofwaffle.eventbus

import android.util.Log
import com.worldofwaffle.FinishActivityEvent
import com.worldofwaffle.commondialog.WaffleDialogEvent
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnboundViewEventBus @Inject constructor(){
    private val startActivitySubject: PublishSubject<StartActivityEvent> = PublishSubject.create()
    private val waffleDialogSubject: PublishSubject<WaffleDialogEvent> = PublishSubject.create()
    private val finishActivitySubject = PublishSubject.create<FinishActivityEvent>()


    fun send(event: StartActivityEvent) {
        startActivitySubject.onNext(event)
    }

    fun send(event: WaffleDialogEvent) {
        waffleDialogSubject.onNext(event)
    }

    fun send(event: FinishActivityEvent) {
        finishActivitySubject.onNext(event)
    }

    fun finishActivity(viewModelClass: Class<*>): Observable<FinishActivityEvent> {
        return finishActivitySubject.filter { event: FinishActivityEvent ->
            fromEmitter(event, viewModelClass)
        }
    }

    fun finishActivity(viewModel: Any): Observable<FinishActivityEvent> {
        return finishActivity(viewModel.javaClass)
    }

    fun startActivity(viewModel: Any): Observable<StartActivityEvent> {
        return startActivity(viewModel.javaClass)
    }

    fun startActivity(viewModelClass: Class<*>): Observable<StartActivityEvent> {
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
        Log.e("Viewmodel2 ", viewModelClass.simpleName)
        Log.e("Viewmodel3 ", event.emitter?.javaClass?.simpleName.toString())
        return viewModelClass.simpleName == event.emitter?.javaClass?.simpleName.toString()
    }
}