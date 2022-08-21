package com.worldofwaffle.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.worldofwaffle.databinding.ItemMenuBinding
import com.worldofwaffle.menu.viewmodel.MenuItemViewModel
import javax.inject.Inject

class MenuAdapter @Inject constructor() : RecyclerView.Adapter<MenuItemHolder>() {
    private var menuItems: MutableList<MenuItemViewModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MenuItemHolder(ItemMenuBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: MenuItemHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount() = menuItems.size

    fun setAdapterData(listToAdd: List<MenuItemViewModel>) {
        menuItems.clear()
        menuItems.addAll(listToAdd)
        notifyDataSetChanged()
    }

}