package com.worldofwaffle

import android.app.Instrumentation
import androidx.annotation.IdRes
import androidx.core.content.PermissionChecker
import androidx.lifecycle.LifecycleObserver

interface ViewCallbackObserver : LifecycleObserver {
    fun onHidden() {}
    fun onUnhidden() {}
    fun onOptionsItemSelected(@IdRes itemId: Int) {}
    fun onPermissionsResult(result: PermissionChecker.PermissionResult) {}
    fun onActivityResult(results: Instrumentation.ActivityResult?) {}
    fun onBackPressed(): Boolean {
        return ViewCallbackEmitter.LEAVE_ACTIVITY
    }

    fun setUserVisibleHint(isVisibleToUser: Boolean) {}
    fun onSaveInstanceState() {}
    fun onPostResume() {}
    fun onWindowFocusChanged(data: Boolean) {}
    fun onFragmentBackPressed(): Boolean {
        return false
    }
}