package com.worldofwaffle.dependencyInjection

import com.worldofwaffle.WaffleApplication
import com.worldofwaffle.commondialog.WaffleDialogFactory
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        MainAndroidViewModule::class,
        AndroidSupportInjectionModule::class,
        ApplicationModule::class]
)
@Singleton
interface ApplicationComponent: AndroidInjector<WaffleApplication> {

    fun waffleDialogFactory(): WaffleDialogFactory


    /* @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(waffleApplication: WaffleApplication)*/
}