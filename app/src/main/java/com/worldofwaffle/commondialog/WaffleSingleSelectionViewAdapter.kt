package com.worldofwaffle.commondialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.worldofwaffle.databinding.ItemSingleSelectionBinding


open class WaffleSingleSelectionViewAdapter : RecyclerView.Adapter<WaffleSingleSelectionViewHolder>() {

    private val items: MutableList<BaseWaffleSingleSelectionItemViewModel> by lazy { mutableListOf()}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaffleSingleSelectionViewHolder {
        ItemSingleSelectionBinding.inflate(LayoutInflater.from(parent.context),parent,false) ?.let {
            return WaffleSingleSelectionViewHolder(it)
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holderBase: WaffleSingleSelectionViewHolder, position: Int) {
        holderBase.bind(items[position])
    }

    fun setAdapterItems(multiSelectionItems: List<BaseWaffleSingleSelectionItemViewModel>,
                        itemClick: (item: BaseWaffleSingleSelectionItemViewModel) -> Unit){
        items.clear()
        items.addAll(multiSelectionItems
            .map { it.setOnClickListener(itemClick)
                it})
    }
}