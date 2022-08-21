package com.worldofwaffle.menu.viewmodel

import com.worldofwaffle.BaseLifecycleViewModel

abstract class BaseMenuItemViewModel: BaseLifecycleViewModel() {
    lateinit var waffleMenuViewModel: WaffleMenuViewModel
}