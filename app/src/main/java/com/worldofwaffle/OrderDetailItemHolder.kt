package com.worldofwaffle

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.worldofwaffle.databinding.ItemOrderDetailBinding

class OrderDetailItemHolder(private val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

    fun bind(viewModel: OrderDetailItemViewModel) {
        (viewDataBinding as ItemOrderDetailBinding).viewModel = viewModel
        viewDataBinding.executePendingBindings()
    }
}