package com.worldofwaffle.commondialog

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField

const val EMPTY_STRING = ""

open class BaseWaffleMultipleSelectionItemViewModel constructor(
    private val itemId: String? = EMPTY_STRING,
    private val multipleSelectionItem: String?,
    private var isItemSelected: Boolean = false) {
    val item = ObservableField<String>()
    val id = ObservableField<String>()
    val isChecked = ObservableBoolean()

    init {
        id.set(itemId)
        item.set(multipleSelectionItem)
        isChecked.set(isItemSelected)
    }

    fun onItemSelected() {
        isChecked.set(!isChecked.get())
    }

    fun clearCheckedAddOn() {
        isChecked.set(false)
    }
}