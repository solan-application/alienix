package com.worldofwaffle.commondialog

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.worldofwaffle.databinding.ItemMultiSelectionBinding
import com.worldofwaffle.databinding.ItemSingleSelectionBinding

open class WaffleSingleSelectionViewHolder(private val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

    open fun bind(viewModel: BaseWaffleSingleSelectionItemViewModel) {
        (viewDataBinding as ItemSingleSelectionBinding).let {
            it.viewModel = viewModel as WaffleSIngleSelectionItemViewModel
            it.executePendingBindings()
        }
    }
}