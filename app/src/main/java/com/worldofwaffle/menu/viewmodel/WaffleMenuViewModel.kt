package com.worldofwaffle.menu.viewmodel

import androidx.databinding.ObservableInt
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.worldofwaffle.BaseLifecycleViewModel
import com.worldofwaffle.database.WaffleMenuDatabase
import com.worldofwaffle.menu.adapter.MenuAdapter
import javax.inject.Inject

class WaffleMenuViewModel @Inject constructor(val menuAdapter: MenuAdapter,
                                              private val waffleMenuDatabase: WaffleMenuDatabase,
                                              private val menuItemViewModelFactory: MenuItemViewModel.Factory): BaseLifecycleViewModel() {

    val waffleTotalPrice = ObservableInt(0)

    fun setDescriptionItems(fragmentManager: FragmentManager) {
        val waffleMenuItems = waffleMenuItems(fragmentManager)
        waffleMenuItems.forEach { it.waffleMenuViewModel = this }
        menuAdapter.setAdapterData(waffleMenuItems)
    }

    private fun waffleMenuItems(fragmentManager: FragmentManager) = waffleMenuDatabase.waffleMenuDao()
        .getWaffleMenu().map { menuItemViewModelFactory.newInstance(it, fragmentManager) }

    /*fun addedWaffleTotalPrice(itemPrice: Int) {
        val totalWafflePrice = waffleTotalPrice.get()
        val updatedWafflePrice = totalWafflePrice + itemPrice
        waffleTotalPrice.set(updatedWafflePrice)
    }

    fun removedWaffleTotalPrice(itemPrice: Int) {
        val totalWafflePrice = waffleTotalPrice.get()
        val updatedWafflePrice = totalWafflePrice - itemPrice
        waffleTotalPrice.set(updatedWafflePrice)
    }*/


}