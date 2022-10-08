package com.worldofwaffle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.worldofwaffle.databinding.ItemOrderEditBinding
import com.worldofwaffle.databinding.ItemOrderStateBinding
import com.worldofwaffle.databinding.ItemOrderStateHeaderBinding
import javax.inject.Inject

private const val ITEM_HEADER = 0
private const val ITEMS = 1
private const val ITEM_EDIT = 2

class OrderHistoryAdapter @Inject constructor() : RecyclerView.Adapter<OrderHistoryViewHolder>() {
    private var orderStateItemViewModels: MutableList<BaseOrderHistoryViewModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            ITEM_HEADER -> OrderHistoryViewHolder(ItemOrderStateHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            ITEMS -> OrderHistoryViewHolder(ItemOrderStateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            ITEM_EDIT -> OrderHistoryViewHolder(ItemOrderEditBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> error("view type not handled")
        }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        when (val adapterItem = orderStateItemViewModels[position]) {
            is OrderHistoryDetailItemViewModel -> holder.bind(adapterItem)
            is OrderHistoryHeaderItemViewModel -> holder.bind(adapterItem)
            is OrderHistoryEditItemViewModel -> holder.bind(adapterItem)
            else -> error("view type not handled")
        }    }

    override fun getItemCount() = orderStateItemViewModels.size

    fun setAdapterData(listToAdd: List<BaseOrderHistoryViewModel>, notifier: () -> Unit = this::notifyDataSetChanged) {
        orderStateItemViewModels.clear()
        orderStateItemViewModels.addAll(listToAdd)
        notifier()
    }

    override fun getItemViewType(position: Int) =
        when (orderStateItemViewModels[position]) {
            is OrderHistoryDetailItemViewModel -> ITEMS
            is OrderHistoryHeaderItemViewModel -> ITEM_HEADER
            is OrderHistoryEditItemViewModel -> ITEM_EDIT
            else -> -1
        }

}