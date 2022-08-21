package com.worldofwaffle.commondialog

import com.worldofwaffle.BaseLifecycleViewModel

open class BaseWaffleSingleSelectionItemViewModel(val id: String,
                                                  val itemName: String)
    : BaseLifecycleViewModel() {

    private lateinit var itemClick: (item: BaseWaffleSingleSelectionItemViewModel) -> Unit

    fun setOnClickListener(itemClick: (item: BaseWaffleSingleSelectionItemViewModel) -> Unit) {
        this.itemClick = itemClick
    }

    fun onItemClicked(){
        itemClick.invoke(this)
    }
}