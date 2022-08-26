package com.worldofwaffle.eventbus

import android.content.Intent
import android.net.Uri

class StartActivityEvent(emitter: Any): BaseUnboundViewEvent() {

    val RESULT_KEY = "result"
    private var startActivityClazz: Class<*>? = null
    private var launchExternalApplication = false
    private var isStartActivityForResult = false
    private var intentAction: String? = null
    private var intentParameter: String? = null
    private var activityNotFoundUri: String? = null
    private var intentFlags = 0
    private var requestCode = 0
    private var intent: Intent? = null
    private var extras = false
    private var intentUri: Uri? = null

    init {
        this.emitter = emitter
    }

    fun activityName(startActivityClazz: Class<*>?): StartActivityEvent {
        this.startActivityClazz = startActivityClazz
        return this
    }

    companion object {
        fun build(emitter: Any): StartActivityEvent {
            return StartActivityEvent(emitter)
        }
    }

    fun launchExternalApplication(value: Boolean): StartActivityEvent {
        launchExternalApplication = value
        return this
    }

    fun isLaunchingExternalApplication(): Boolean {
        return launchExternalApplication
    }

    fun intentAction(value: String?): StartActivityEvent {
        intentAction = value
        return this
    }

    fun getIntentAction(): String? {
        return intentAction
    }

    fun intentParameter(value: String?): StartActivityEvent {
        intentParameter = value
        return this
    }

    fun getIntentParameter(): String? {
        return intentParameter
    }

    fun intentUri(value: Uri?): StartActivityEvent {
        intentUri = value
        return this
    }

    fun getIntentUri(): Uri? {
        return intentUri
    }

    fun activityNotFoundUri(value: String?): StartActivityEvent {
        activityNotFoundUri = value
        return this
    }

    fun getActivityNotFoundUri(): String? {
        return activityNotFoundUri
    }

    fun intentFlags(intentFlags: Int): StartActivityEvent {
        this.intentFlags = intentFlags
        return this
    }

    fun getStartActivity(): Class<*>? {
        return startActivityClazz
    }

    fun getIntentFlags(): Int {
        return intentFlags
    }

    fun setStartActivityForResult(startActivityForResult: Boolean): StartActivityEvent {
        isStartActivityForResult = startActivityForResult
        return this
    }

    fun setRequestCode(requestCode: Int): StartActivityEvent? {
        this.requestCode = requestCode
        return this
    }

    fun isStartActivityForResult(): Boolean {
        return isStartActivityForResult
    }

    fun getRequestCode(): Int {
        return requestCode
    }

    fun getIntent(): Intent? {
        return intent
    }

    fun setIntent(intent: Intent): StartActivityEvent {
        this.intent = intent
        return this
    }

    fun hasExtras(): Boolean {
        return extras
    }

    fun setExtras(extras: Boolean): StartActivityEvent? {
        this.extras = extras
        return this
    }
}