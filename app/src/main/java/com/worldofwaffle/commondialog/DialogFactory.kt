package com.worldofwaffle.commondialog

import android.app.Dialog
import android.content.Context
import androidx.core.util.Pair

interface DialogFactory {

    fun createDialog(
        context: Context,
        title: String,
        multiSelectItems: List<BaseWaffleMultipleSelectionItemViewModel>,
        waffleMultiSelectionAdapter: WaffleMultipleSelectionViewAdapter?,
        singleselectitem: List<BaseWaffleSingleSelectionItemViewModel>,
        waffleSingleSelectionViewAdapter: WaffleSingleSelectionViewAdapter?,
        isDismissable: Boolean,
        buttons: List<Pair<Int, String>>?,
        listener: WaffleDialogListener
    ): Dialog

}