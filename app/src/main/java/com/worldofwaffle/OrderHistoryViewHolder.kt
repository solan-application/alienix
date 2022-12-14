package com.worldofwaffle

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.worldofwaffle.databinding.*

class OrderHistoryViewHolder(private val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

    fun bind(viewModel: OrderHistoryDetailItemViewModel) {
        (viewDataBinding as ItemOrderStateBinding).viewModel = viewModel
        viewDataBinding.executePendingBindings()
    }

    fun bind(viewModel: OrderHistoryHeaderItemViewModel) {
        (viewDataBinding as ItemOrderStateHeaderBinding).viewModel = viewModel
        viewDataBinding.executePendingBindings()
    }

    fun bind(viewModel: OrderHistoryEditItemViewModel) {
        (viewDataBinding as ItemOrderEditBinding).viewModel = viewModel
        viewDataBinding.executePendingBindings()
    }
}