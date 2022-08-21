package com.worldofwaffle.commondialog

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.worldofwaffle.databinding.ItemMultiSelectionBinding

open class WaffleMultipleSelectionViewHolder(private val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

    open fun bind(viewModel: BaseWaffleMultipleSelectionItemViewModel) {
        (viewDataBinding as ItemMultiSelectionBinding).let {
            it.viewModel = viewModel as WaffleMultipleSelectionItemViewModel
            it.executePendingBindings()
        }
    }
}