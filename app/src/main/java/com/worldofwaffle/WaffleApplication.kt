package com.worldofwaffle

import android.app.Application
import com.worldofwaffle.dependencyInjection.ApplicationComponent
import com.worldofwaffle.dependencyInjection.ApplicationModule
import com.worldofwaffle.dependencyInjection.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class WaffleApplication : Application(), HasAndroidInjector {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
        component.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    companion object {
        private lateinit var component: ApplicationComponent

        fun getComponent(): ApplicationComponent {
            return component
        }
    }

}