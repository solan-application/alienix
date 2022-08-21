package com.worldofwaffle.menu

import com.worldofwaffle.commondialog.WaffleMultipleSelectionItemViewModel


data class AddOnItem(val addOnId: String, val addOnName: String, val addOnPrice: Int) :
    WaffleMultipleSelectionItemViewModel(addOnId, addOnName)