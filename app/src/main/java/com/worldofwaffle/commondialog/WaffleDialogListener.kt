package com.worldofwaffle.commondialog

import android.view.View
import android.widget.CompoundButton

interface WaffleDialogListener {
    fun onButtonClickedAtIndex(index: Int) {}
    fun onDoNotShowAgainCheckBoxCheckedChanged(
        compoundButton: CompoundButton?,
        isChecked: Boolean
    ) {
    }

    fun onDialogDismissed() {}
    fun onMultipleSelection(multipleSelections: List<BaseWaffleMultipleSelectionItemViewModel>) {}
    fun dismissDialog(): Boolean {
        return true
    }

    fun onSingleSelection(singleSelection: BaseWaffleSingleSelectionItemViewModel) {

    }

    fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    fun onButtonCreated(index: Int, button: View?) {}
    fun onSpannableTextClicked() {}
}