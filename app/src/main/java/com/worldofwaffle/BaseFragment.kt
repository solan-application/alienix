package com.worldofwaffle

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.worldofwaffle.commondialog.DialogFactory
import com.worldofwaffle.commondialog.WaffleDialogEvent
import com.worldofwaffle.dependencyInjection.ApplicationComponent
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment: Fragment() {

    lateinit var dialogFactory: DialogFactory

    private lateinit var fordDialog: Dialog

    protected open fun registerUnboundViewEvents(): CompositeDisposable? {
        return null
    }

    private val lifecycleSubscriptions = CompositeDisposable()
    private val viewCallbackEmitter = ViewCallbackEmitter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component: ApplicationComponent = WaffleApplication.getComponent()
        dialogFactory = component.waffleDialogFactory()
    }

    open fun onInject() {
        AndroidSupportInjection.inject(this)
    }

    override fun onAttach(context: Context) {
        onInject()
        super.onAttach(context)
    }

    override fun onResume() {
        subscribeToEventBus()
        super.onResume()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            viewCallbackEmitter.fireEvent(ViewCallbackEmitter.ViewCallback.ON_HIDDEN, null)
        } else {
            viewCallbackEmitter.fireEvent(ViewCallbackEmitter.ViewCallback.ON_UNHIDDEN, null)
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        viewCallbackEmitter.fireEvent(
            ViewCallbackEmitter.ViewCallback.ON_FRAGMENT_VISIBILITY_CHANGE,
            isVisibleToUser
        )
    }

    //endregion

    //endregion
    open fun getViewCallbackEmitter(): ViewCallbackEmitter? {
        return viewCallbackEmitter.init(lifecycle)
    }

    private fun subscribeToEventBus() {
        lifecycleSubscriptions.clear()
        val eventsSubscriptionV2 = registerUnboundViewEvents()
        if (eventsSubscriptionV2 != null) {
            lifecycleSubscriptions.add(eventsSubscriptionV2)
        }
    }

    fun showWaffleDialog(event: WaffleDialogEvent) {
        fordDialog = dialogFactory.createDialog(
            requireContext(),
            event.getDialogTitle(),
            event.getMultipleSelectionContents(),
            event.getMultipleSelectionViewAdapter(),
            event.getSingleSelectionContents(),
            event.getSingleSelectionViewAdapter(),
            event.getWaffleIsDismissable(),
            event.getButtons(),
            event.getListener()
        )
        fordDialog.show()
    }
}