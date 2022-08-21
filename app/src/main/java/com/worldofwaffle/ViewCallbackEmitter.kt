package com.worldofwaffle

import android.app.Instrumentation
import androidx.core.content.PermissionChecker
import androidx.lifecycle.Lifecycle


class ViewCallbackEmitter {
    enum class ViewCallback {
        ON_HIDDEN,  //Fragment ON_HIDDEN_CHANGED, hidden = true
        ON_UNHIDDEN,  //Fragment ON_HIDDEN_CHANGED, hidden = false
        ON_OPTIONS_ITEM_SELECTED, ON_REQUEST_PERMISSIONS_RESULT, ON_ACTIVITY_RESULT, ON_ACTIVITY_POST_RESUME, ON_BACK_PRESSED, ON_FRAGMENT_VISIBILITY_CHANGE, ON_SAVE_INSTANCE_STATE, ON_BACK_PRESSED_STAY_ON_SCREEN, ON_WINDOW_FOCUS_CHANGED
    }

    var baseLifecycle: Lifecycle? = null
        private set
    private val viewCallbackObservers: MutableList<ViewCallbackObserver> = ArrayList()
    fun init(lifecycle: Lifecycle?): ViewCallbackEmitter {
        baseLifecycle = lifecycle
        return this
    }

    fun fireEvent(callback: ViewCallback, data: Any?) {
        for (observer in viewCallbackObservers) {
            when (callback) {
                ViewCallback.ON_HIDDEN -> observer.onHidden()
                ViewCallback.ON_UNHIDDEN -> observer.onUnhidden()
                ViewCallback.ON_OPTIONS_ITEM_SELECTED -> observer.onOptionsItemSelected(data as Int)
                ViewCallback.ON_REQUEST_PERMISSIONS_RESULT -> if (data != null) {
                    observer.onPermissionsResult(data as PermissionChecker.PermissionResult)
                }
                ViewCallback.ON_ACTIVITY_RESULT -> if (data != null) {
                    observer.onActivityResult(data as Instrumentation.ActivityResult?)
                }
                ViewCallback.ON_FRAGMENT_VISIBILITY_CHANGE -> observer.setUserVisibleHint(data as Boolean)
                ViewCallback.ON_SAVE_INSTANCE_STATE -> observer.onSaveInstanceState()
                ViewCallback.ON_ACTIVITY_POST_RESUME -> observer.onPostResume()
                ViewCallback.ON_WINDOW_FOCUS_CHANGED -> observer.onWindowFocusChanged(data as Boolean)
            }
        }
    }

    fun fireOnBackPressed(): Boolean {
        for (observer in viewCallbackObservers) {
            if (!observer.onBackPressed()) {
                return CLOSE_MODAL
            }
        }
        return LEAVE_ACTIVITY
    }

    fun addObserver(viewCallbackObserver: ViewCallbackObserver) {
        baseLifecycle!!.addObserver(viewCallbackObserver)
        viewCallbackObservers.add(viewCallbackObserver)
    }

    fun fireOnFragmentBackPressed(): Boolean {
        for (observer in viewCallbackObservers) {
            if (observer.onFragmentBackPressed()) {
                return true
            }
        }
        return false
    }

    companion object {
        const val LEAVE_ACTIVITY = true
        const val CLOSE_MODAL = false
    }
}
