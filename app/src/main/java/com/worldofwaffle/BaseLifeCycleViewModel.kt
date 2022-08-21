package com.worldofwaffle

import androidx.core.content.PermissionChecker
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseLifecycleViewModel : ViewCallbackObserver {

    companion object {
        private val lifecycleSubscriptions = CompositeDisposable()
        private val onHiddenDisposables = CompositeDisposable()
    }

    open var viewCallbackEmitter: ViewCallbackEmitter? = null
        set(viewCallbackEmitter) {
            field = viewCallbackEmitter
            this.viewCallbackEmitter?.addObserver(this)
        }

    protected open infix fun subscribeOnLifecycle(disposable: Disposable?) {
        disposable?.let { lifecycleSubscriptions.add(it) }
    }

    protected infix fun disposeOnHidden(disposable: Disposable) {
        onHiddenDisposables.add(disposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
        onHiddenDisposables.clear()
        lifecycleSubscriptions.clear()
    }

    override fun onHidden() = onHiddenDisposables.clear()

    // After onPermissionsResult(), onResume() is called. so to prevent re-registering events.
    override fun onPermissionsResult(result: PermissionChecker.PermissionResult) = lifecycleSubscriptions.clear()
}