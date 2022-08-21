package com.worldofwaffle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.worldofwaffle.databinding.ItemOrderDetailBinding
import javax.inject.Inject

class OrderDetailAdapter @Inject constructor() : RecyclerView.Adapter<OrderDetailItemHolder>() {
    private var menuItems: MutableList<OrderDetailItemViewModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return OrderDetailItemHolder(ItemOrderDetailBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: OrderDetailItemHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount() = menuItems.size

    fun setAdapterData(listToAdd: List<OrderDetailItemViewModel>) {
        menuItems.clear()
        menuItems.addAll(listToAdd)
        notifyDataSetChanged()
    }

}