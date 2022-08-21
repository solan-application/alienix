package com.worldofwaffle

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.tabs.TabLayout

@BindingAdapter("onPageChange")
fun onPageChange(
    viewPager: ViewPager,
    listener: ViewPagerPageChangeListener
) {
    viewPager.addOnPageChangeListener(object : OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            listener.onPageSelected(position)
        }

        override fun onPageScrollStateChanged(state: Int) {}
    })
}

fun interface ViewPagerPageChangeListener {
    fun onPageSelected(position: Int)
}

@BindingAdapter("pager")
fun setPager(tabLayout: TabLayout, viewPager: ViewPager) {
    tabLayout.setupWithViewPager(viewPager)
}