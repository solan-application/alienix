package com.worldofwaffle

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.worldofwaffle.databinding.ItemOrderDetailBinding
import com.worldofwaffle.databinding.ItemWaffleFillingsBinding

class WaffleFillingsItemHolder(private val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

    fun bind(viewModel: WaffleFillingsItemViewModel) {
        (viewDataBinding as ItemWaffleFillingsBinding).viewModel = viewModel
        viewDataBinding.executePendingBindings()
    }
}