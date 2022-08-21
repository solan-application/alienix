package com.worldofwaffle.commondialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.worldofwaffle.databinding.ItemMultiSelectionBinding


open class WaffleMultipleSelectionViewAdapter : RecyclerView.Adapter<WaffleMultipleSelectionViewHolder>() {

    val items: MutableList<BaseWaffleMultipleSelectionItemViewModel> by lazy { mutableListOf()}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaffleMultipleSelectionViewHolder {
        ItemMultiSelectionBinding.inflate(LayoutInflater.from(parent.context),parent,false) ?.let {
            return WaffleMultipleSelectionViewHolder(it)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holderBase: WaffleMultipleSelectionViewHolder, position: Int) {
        holderBase.bind(items[position])
    }

    fun setAdapterItems(multiSelectionItems: List<BaseWaffleMultipleSelectionItemViewModel>){
        items.clear()
        items.addAll(multiSelectionItems)
    }
}