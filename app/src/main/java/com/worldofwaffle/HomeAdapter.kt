package com.worldofwaffle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.worldofwaffle.databinding.ItemCashInBoxBinding
import com.worldofwaffle.databinding.ItemCommonHomeBinding
import com.worldofwaffle.databinding.ItemWaffleMixBinding
import javax.inject.Inject

private const val COMMON_HOME_ITEM = 0
private const val WAFFLE_MIX_ITEM = 1
private const val CASH_IN_BOX_ITEM = 2

class HomeAdapter @Inject constructor() : RecyclerView.Adapter<HomeItemHolder>() {
    private var homeItemViewModels: MutableList<BaseHomeItemViewModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            COMMON_HOME_ITEM -> HomeItemHolder(ItemCommonHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            WAFFLE_MIX_ITEM -> HomeItemHolder(ItemWaffleMixBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            CASH_IN_BOX_ITEM -> HomeItemHolder(ItemCashInBoxBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> error("view type not handled")
        }

    override fun onBindViewHolder(holder: HomeItemHolder, position: Int) {
        when (val adapterItem = homeItemViewModels[position]) {
            is CommonHomeItemViewModel -> holder.bind(adapterItem)
            is WaffleMixItemViewModel -> holder.bind(adapterItem)
            is CashInBoxItemViewModel -> holder.bind(adapterItem)
            else -> error("view type not handled")
        }    }

    override fun getItemCount() = homeItemViewModels.size

    fun setAdapterData(items: List<BaseHomeItemViewModel>, notifier: () -> Unit = this::notifyDataSetChanged) {
        homeItemViewModels.clear()
        homeItemViewModels.addAll(items)
        notifier()
    }

    override fun getItemViewType(position: Int) =
        when (homeItemViewModels[position]) {
            is CommonHomeItemViewModel -> COMMON_HOME_ITEM
            is WaffleMixItemViewModel -> WAFFLE_MIX_ITEM
            is CashInBoxItemViewModel -> CASH_IN_BOX_ITEM
            else -> -1
        }

}