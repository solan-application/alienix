package com.worldofwaffle.commondialog

import androidx.core.util.Pair
import com.worldofwaffle.eventbus.BaseUnboundViewEvent

class WaffleDialogEvent(wEmitter: Any): BaseUnboundViewEvent() {

    init {
        this.emitter = wEmitter
    }

    private var dialogTitle: String = ""
    private var multipleSelectionContents: List<BaseWaffleMultipleSelectionItemViewModel> = arrayListOf()
    private var singleSelectionContents: List<BaseWaffleSingleSelectionItemViewModel> = arrayListOf()
    private var waffleMultipleSelectionViewAdapter: WaffleMultipleSelectionViewAdapter? = null
    private var waffleSingleSelectionViewAdapter: WaffleSingleSelectionViewAdapter? = null
    private var isDismissable: Boolean = true
    private var buttons: List<Pair<Int, String>>? = null
    private lateinit var listener: WaffleDialogListener


    companion object {
        fun build(emitter: Any): WaffleDialogEvent {
            return WaffleDialogEvent(emitter)
        }
    }

    fun setDialogTitle(wDialogTitle: String): WaffleDialogEvent {
        dialogTitle = wDialogTitle
        return this
    }

    fun setListener(wListener: WaffleDialogListener): WaffleDialogEvent {
        this.listener = wListener
        return this
    }

    fun setMultiSelectionAdapter(waffleMultipleSelectionViewAdapter: WaffleMultipleSelectionViewAdapter): WaffleDialogEvent {
        this.waffleMultipleSelectionViewAdapter = waffleMultipleSelectionViewAdapter
        return this
    }

    fun setButtonListWithType(buttons: List<Pair<Int, String>>): WaffleDialogEvent {
        this.buttons = buttons
        return this
    }

    fun setMultipleSelectionContents(wMultipleSelectionContents: List<BaseWaffleMultipleSelectionItemViewModel>): WaffleDialogEvent {
        multipleSelectionContents = wMultipleSelectionContents
        return this
    }

    fun setSingleSelectionAdapter(waffleSingleSelectionViewAdapter: WaffleSingleSelectionViewAdapter): WaffleDialogEvent {
        this.waffleSingleSelectionViewAdapter = waffleSingleSelectionViewAdapter
        return this
    }

    fun setSingleSelectionContents(singleSelectionItemViewModel: List<BaseWaffleSingleSelectionItemViewModel>): WaffleDialogEvent {
        this.singleSelectionContents = singleSelectionItemViewModel
        return this
    }

    fun getDialogTitle() = dialogTitle

    fun getMultipleSelectionContents() = multipleSelectionContents

    fun getMultipleSelectionViewAdapter() = waffleMultipleSelectionViewAdapter

    fun getSingleSelectionViewAdapter() = waffleSingleSelectionViewAdapter

    fun getSingleSelectionContents() = singleSelectionContents

    fun getWaffleIsDismissable() = isDismissable

    fun getButtons() = buttons

    fun getListener() = listener
}