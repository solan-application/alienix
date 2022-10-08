package com.worldofwaffle

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.worldofwaffle.ViewCallbackEmitter.Companion.LEAVE_ACTIVITY
import com.worldofwaffle.commondialog.WaffleDialogEvent
import com.worldofwaffle.commondialog.WaffleDialogFactory
import com.worldofwaffle.dependencyInjection.ApplicationComponent
import com.worldofwaffle.eventbus.StartActivityEvent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity: AppCompatActivity() {

    private lateinit var waffleDialogFactory: WaffleDialogFactory

    private lateinit var fordDialog: Dialog

    private val viewCallbackEmitter = ViewCallbackEmitter()
    private val lifecycleSubscriptions = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component: ApplicationComponent = WaffleApplication.getComponent()
        waffleDialogFactory = component.waffleDialogFactory()
    }

    override fun onResume() {
        super.onResume()
        subscribeToEventBus()
    }

    override fun onPause() {
        lifecycleSubscriptions.clear()
        super.onPause()
    }

    private fun subscribeToEventBus() {
        lifecycleSubscriptions.clear()
        val eventsSubscriptionV2 = registerUnboundViewEvents()
        if (eventsSubscriptionV2 != null) {
            lifecycleSubscriptions.add(eventsSubscriptionV2)
        }
    }

    protected open fun registerUnboundViewEvents(): CompositeDisposable? {
        return null
    }

    override fun onBackPressed() {
        if (viewCallbackEmitter.fireOnBackPressed() === LEAVE_ACTIVITY) {
            super.onBackPressed()
        }
    }

    fun getViewCallbackEmitter(): ViewCallbackEmitter {
        return viewCallbackEmitter.init(lifecycle)
    }

    protected open fun finishActivity(event: FinishActivityEvent) {
        if (event.isFinishAffinity()) {
            finishAffinity()
        } else {
            finish()
        }
    }

    protected fun startActivity(event: StartActivityEvent) {
        val flags: Int = event.getIntentFlags()
        val hasExtras: Boolean = event.hasExtras()
        if (event.isLaunchingExternalApplication()) {
            launchExternalApplication(event)
        } else if (event.isStartActivityForResult()) {
            if (event.getIntent() != null) {
                if (hasExtras) {
                    val intent: Intent =
                        Intent(this, event.getStartActivity()).putExtras(event.getIntent()!!)
                    startActivityForResult(intent, event.getRequestCode())
                } else {
                    startActivityForResult(event.getIntent(), event.getRequestCode())
                }
            } else {
                startActivityForResult(
                    Intent(this, event.getStartActivity()),
                    event.getRequestCode()
                )
            }
        } else {
            val startIntent = Intent(this, event.getStartActivity())
            if (hasExtras) {
                startIntent.putExtra("NAVIGATOR_STATE", event.getIntentParameter())
            }
            if (flags != 0) {
                startIntent.flags = event.getIntentFlags()
            }
            startActivity(startIntent)
        }
    }

    open fun launchExternalApplication(event: StartActivityEvent) {
        try {
            if (event.getIntent() != null) {
                Log.e("Intent" , "1")
                startActivity(event.getIntent())
            } else {
                Log.e("Intent" , "2")
                startActivity(
                    Intent(
                        event.getIntentAction(),
                        if (event.getIntentUri() != null) event.getIntentUri() else Uri.parse(event.getIntentParameter())
                    )
                )
            }
        } catch (ex: ActivityNotFoundException) {
            if (TextUtils.isEmpty(event.getActivityNotFoundUri())) {
                Log.e("Intent" , "Error$ex")
                //exceptionLogger.logException(ex)
            } else {
                startActivity(
                    Intent(
                        event.getIntentAction(),
                        Uri.parse(event.getActivityNotFoundUri())
                    )
                )
            }
        }
    }

    fun showWaffleDialog(event: WaffleDialogEvent) {
        fordDialog = waffleDialogFactory.createDialog(
            this,
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