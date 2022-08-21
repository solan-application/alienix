package com.worldofwaffle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

open class DashboardFragmentPagerAdapter(fragmentManager: FragmentManager, private val screens: List<Fragment>): FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return screens[position]
    }

    override fun getPageTitle(position: Int) = when (position) {
        0 -> getMenuTabName()
        else -> getOrderDetailTabName()
    }

    override fun getCount(): Int {
        return screens.size
    }

    private fun getOrderDetailTabName(): String {
        return "ORDER DETAIL"
    }

    private fun getMenuTabName(): String {
        return "MENU"
    }
}