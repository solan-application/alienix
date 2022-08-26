package com.worldofwaffle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.worldofwaffle.databinding.ItemOrderDetailBinding
import com.worldofwaffle.databinding.ItemWaffleFillingsBinding
import javax.inject.Inject

class WaffleFillingsAdapter @Inject constructor() : RecyclerView.Adapter<WaffleFillingsItemHolder>() {
    private var items: MutableList<WaffleFillingsItemViewModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaffleFillingsItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return WaffleFillingsItemHolder(ItemWaffleFillingsBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: WaffleFillingsItemHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setAdapterData(listToAdd: List<WaffleFillingsItemViewModel>) {
        items.clear()
        items.addAll(listToAdd)
        notifyDataSetChanged()
    }

}