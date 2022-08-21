package com.worldofwaffle.commondialog

import androidx.core.util.Pair
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.worldofwaffle.R
import java.util.*

class WaffleDialogFactory: DialogFactory {

    override fun createDialog(
        context: Context,
        title: String,
        multiSelectItems: List<BaseWaffleMultipleSelectionItemViewModel>,
        waffleMultiSelectionAdapter: WaffleMultipleSelectionViewAdapter?,
        singleselectitem: List<BaseWaffleSingleSelectionItemViewModel>,
        waffleSingleSelectionViewAdapter: WaffleSingleSelectionViewAdapter?,
        isDismissable: Boolean,
        buttons: List<Pair<Int, String>>?,
        listener: WaffleDialogListener
    ): Dialog {
        return createBaseDialog(context, title, multiSelectItems,
            waffleMultiSelectionAdapter, singleselectitem, waffleSingleSelectionViewAdapter,
            isDismissable, buttons, listener)
    }

    private fun createBaseDialog(
        context: Context,
        title: String,
        multiSelectItems: List<BaseWaffleMultipleSelectionItemViewModel>,
        waffleMultipleSelectionViewAdapter: WaffleMultipleSelectionViewAdapter?,
        singleselectitem: List<BaseWaffleSingleSelectionItemViewModel>,
        waffleSingleSelectionViewAdapter: WaffleSingleSelectionViewAdapter?,
        isDismissable: Boolean,
        buttons: List<Pair<Int, String>>?,
        listener: WaffleDialogListener?
    ): Dialog{
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.common_dialog, null)
        val titleTextView = view.findViewById<TextView>(R.id.common_dialog_title)
        val waffleMultipleSelectionRecyclerView: RecyclerView =
            view.findViewById(R.id.common_dialog_multi_select_list)
        val buttonContainer: ConstraintLayout =
            view.findViewById(R.id.common_dialog_button_container)
        val resources = context.resources

        val alertDialog: Dialog = AlertDialog.Builder(context).setView(view).create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val isMultiSelectItemsAvailable = isMultiSelectItemsAvailable(multiSelectItems)
        val isSingleSelectItemAvailable = isSingleSelectItemAvailable(singleselectitem)


        if (TextUtils.isEmpty(title)) {
            titleTextView.visibility = View.GONE
        } else {
            titleTextView.text = title
        }

        buttons?.let {
            if (it.isNotEmpty()) {
                var shouldAddButtonMargin = true
                for (button in it) {
                    if (button.second.equals(SECONDARY, ignoreCase = true)) {
                        shouldAddButtonMargin = false
                        break
                    }
                }
                val buttonMargin = if (shouldAddButtonMargin) 10 else 0
                buttonContainer.visibility = View.VISIBLE
                for ((index, button) in buttons.withIndex()) {
                    val buttonText: String = resources.getString(button.first)
                    val buttonType = button.second
                    val buttonView = LayoutInflater.from(context)
                        .inflate(R.layout.button_waffle_dialog, null) as AppCompatTextView
                    createButtons(
                        listener, buttonContainer, alertDialog, index, buttonText, buttonView,
                        (isMultiSelectItemsAvailable),
                        waffleMultipleSelectionRecyclerView
                    )
                    setupStyleForPrimaryButton(
                        buttonContainer, resources, index, buttonView, buttonType,
                        buttonMargin
                    )
                }
            } else {
                buttonContainer.visibility = View.GONE
            }
        }

        when {
            isMultiSelectItemsAvailable -> {
                waffleMultipleSelectionRecyclerView.visibility = View.VISIBLE
                if (multiSelectItems.size < 6) {
                    val params: ViewGroup.LayoutParams =
                        waffleMultipleSelectionRecyclerView.layoutParams
                    params.height = getDialogHeight(context, multiSelectItems.size)
                    waffleMultipleSelectionRecyclerView.layoutParams = params
                }
                waffleMultipleSelectionRecyclerView.layoutManager = LinearLayoutManager(context)
                waffleMultipleSelectionViewAdapter?.setAdapterItems(multiSelectItems)
                waffleMultipleSelectionRecyclerView.adapter = waffleMultipleSelectionViewAdapter
            }
            isSingleSelectItemAvailable -> {
                waffleMultipleSelectionRecyclerView.visibility = View.VISIBLE
                if (singleselectitem.size < 6) {
                    val params: ViewGroup.LayoutParams =
                        waffleMultipleSelectionRecyclerView.layoutParams
                    params.height = getDialogHeight(context, singleselectitem.size)
                    waffleMultipleSelectionRecyclerView.layoutParams = params
                }
                waffleMultipleSelectionRecyclerView.layoutManager = LinearLayoutManager(context)

                fun setSingleSelectionItemListener(selectedItem: BaseWaffleSingleSelectionItemViewModel) {
                    listener?.onSingleSelection(selectedItem)
                    alertDialog.dismiss()
                }
                waffleSingleSelectionViewAdapter?.setAdapterItems(singleselectitem, ::setSingleSelectionItemListener)

                waffleMultipleSelectionRecyclerView.adapter = waffleSingleSelectionViewAdapter
            }
            else -> {
                waffleMultipleSelectionRecyclerView.visibility = View.GONE
            }
        }

        listener?.let { alertDialog.setOnDismissListener { listener.onDialogDismissed() } }

        Log.e("Dialog", "Show alert")
        alertDialog.setCancelable(isDismissable)
        return alertDialog
    }

    private fun isMultiSelectItemsAvailable(checkBoxItems: List<BaseWaffleMultipleSelectionItemViewModel>?): Boolean {
        return checkBoxItems != null && checkBoxItems.isNotEmpty()
    }

    private fun isSingleSelectItemAvailable(items: List<BaseWaffleSingleSelectionItemViewModel>?): Boolean {
        return items != null && items.isNotEmpty()
    }

    private fun getDialogHeight(context: Context, size: Int): Int {
        Log.e("Dialog", "item size $size")
        return context.resources.getDimensionPixelSize(R.dimen.dialog_multi_selection_title_height)+
                size * context.resources.getDimensionPixelSize(R.dimen.dialog_multi_selection_item_height)
    }

    private fun createButtons(
        listener: WaffleDialogListener?, buttonContainer: ConstraintLayout, alertDialog: Dialog,
        index: Int, buttonText: String, button: AppCompatTextView, isMultipleSelection: Boolean,
        recyclerView: RecyclerView
    ) {
        button.id = index + 1
        button.layoutParams =
            ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        button.minHeight = 120
        button.text = buttonText
        listener?.let {
            if (isMultipleSelection) {
                Log.e("Dialog", " Button onclick listener set")
                button.setOnClickListener(
                    WaffleDialogButtonClickListener(
                        index,
                        listener,
                        alertDialog,
                        true,
                        recyclerView
                    )
                )
            } else {
                button.setOnClickListener(
                    WaffleDialogButtonClickListener(
                        index,
                        listener,
                        alertDialog
                    )
                )
            }
        }
        button.isAllCaps = true
        buttonContainer.addView(button)
        listener?.onButtonCreated(index, button)
    }

    private fun setupStyleForPrimaryButton(
        buttonContainer: ConstraintLayout,
        resources: Resources,
        index: Int,
        button: AppCompatTextView,
        buttonType: String,
        buttonMargin: Int
    ) {
        if (buttonType.equals(SECONDARY, ignoreCase = true)) {
            button.setTextColor(resources.getColor(R.color.primary_blue))
        } else {
            button.setBackgroundColor(resources.getColor(R.color.primary_blue))
            button.setTextColor(resources.getColor(R.color.surface))
        }
        val constraintSet = ConstraintSet()
        constraintSet.clone(buttonContainer)
        if (index == 0) {
            constraintSet.connect(
                button.id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                0
            )
        } else {
            constraintSet.connect(
                button.id, ConstraintSet.TOP, index, ConstraintSet.BOTTOM,
                resources.getDimension(R.dimen.standard_margin_small).toInt()
            )
        }
        constraintSet.connect(
            button.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,
            0
        )
        constraintSet.connect(
            button.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END,
            0
        )
        constraintSet.applyTo(buttonContainer)
    }


    companion object ButtonTypes {
        val PRIMARY: String = DialogButtonTypes.PRIMARY
        val SECONDARY: String = DialogButtonTypes.SECONDARY
        val TERTIARY: String = DialogButtonTypes.TERTIARY

        private class WaffleDialogButtonClickListener constructor(
            private val buttonIndex: Int, fordDialogListener: WaffleDialogListener, dialog: Dialog,
            isMultipleSelectionDialog: Boolean, recyclerView: RecyclerView?
        ) :
            View.OnClickListener {
            private val fordDialogListener: WaffleDialogListener
            private val dialog: Dialog
            private val isMultipleSelectionDialog: Boolean
            private val recyclerView: RecyclerView?

            constructor(
                buttonIndex: Int,
                fordDialogListener: WaffleDialogListener,
                dialog: Dialog
            ) : this(buttonIndex, fordDialogListener, dialog, false, null) {}

            override fun onClick(v: View) {
                fordDialogListener.onButtonClickedAtIndex(buttonIndex)
                if (isMultipleSelectionDialog && buttonIndex == 0) {
                    val list: MutableList<BaseWaffleMultipleSelectionItemViewModel> =
                        (recyclerView?.adapter as WaffleMultipleSelectionViewAdapter).items
                    fordDialogListener.onMultipleSelection(list)
                }
                if (fordDialogListener.dismissDialog()) {
                    dialog.dismiss()
                }
            }

            init {
                Log.e("Dialog", " Button onclick init")
                this.fordDialogListener = fordDialogListener
                this.dialog = dialog
                this.isMultipleSelectionDialog = isMultipleSelectionDialog
                this.recyclerView = recyclerView
            }
        }
    }

}