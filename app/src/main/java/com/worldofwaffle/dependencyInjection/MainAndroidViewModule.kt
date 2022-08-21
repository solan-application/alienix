package com.worldofwaffle.dependencyInjection

import com.worldofwaffle.DashBoardActivity
import com.worldofwaffle.OrderDetailFragment
import com.worldofwaffle.OrdersStateActivity
import com.worldofwaffle.RepeatOrderFragment
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
    abstract fun bindOrderStateActivity(): OrdersStateActivity
}