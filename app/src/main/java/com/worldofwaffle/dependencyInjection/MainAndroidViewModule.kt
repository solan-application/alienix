package com.worldofwaffle.dependencyInjection

import com.worldofwaffle.*
import com.worldofwaffle.menu.MenuFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainAndroidViewModule {

    @ContributesAndroidInjector
    abstract fun bindDashBoardActivity(): DashBoardActivity

    @ContributesAndroidInjector
    abstract fun bindMenuFragment(): MenuFragment

    @ContributesAndroidInjector
    abstract fun bindOrderDetailFragment(): OrderDetailFragment

    @ContributesAndroidInjector
    abstract fun bindRepeatOrderFragment(): RepeatOrderFragment

    @ContributesAndroidInjector
    abstract fun bindOrderStateActivity(): OrderHistoryActivity

    @ContributesAndroidInjector
    abstract fun bindHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun bindWaffleFillingsActivity(): WaffleFillingsActivity
}