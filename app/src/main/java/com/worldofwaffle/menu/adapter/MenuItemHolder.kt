package com.worldofwaffle.menu.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.worldofwaffle.databinding.ItemMenuBinding
import com.worldofwaffle.menu.viewmodel.MenuItemViewModel

class MenuItemHolder(private val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

    fun bind(viewModel: MenuItemViewModel) {
        (viewDataBinding as ItemMenuBinding).viewModel = viewModel
        viewDataBinding.executePendingBindings()
    }
}