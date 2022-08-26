package com.worldofwaffle

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.worldofwaffle.databinding.*

class HomeItemHolder(private val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

    fun bind(viewModel: CommonHomeItemViewModel) {
        (viewDataBinding as ItemCommonHomeBinding).viewModel = viewModel
        viewDataBinding.executePendingBindings()
    }

    fun bind(viewModel: WaffleMixItemViewModel) {
        (viewDataBinding as ItemWaffleMixBinding).viewModel = viewModel
        viewDataBinding.executePendingBindings()
    }

    fun bind(viewModel: CashInBoxItemViewModel) {
        (viewDataBinding as ItemCashInBoxBinding).viewModel = viewModel
        viewDataBinding.executePendingBindings()
    }
}