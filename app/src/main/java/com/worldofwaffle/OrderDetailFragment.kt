package com.worldofwaffle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.worldofwaffle.databinding.FragmentOrderDetailBinding
import javax.inject.Inject

class OrderDetailFragment: BaseFragment() {

    @Inject
    lateinit var orderDetailViewModel: OrderDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentOrderDetailBinding>(inflater,
            R.layout.fragment_order_detail, container, false)
        binding.viewModel = orderDetailViewModel
        orderDetailViewModel.viewCallbackEmitter = getViewCallbackEmitter()
        return binding.root
    }

    fun onPageShow() {
        orderDetailViewModel.setDescriptionItems()
    }
}