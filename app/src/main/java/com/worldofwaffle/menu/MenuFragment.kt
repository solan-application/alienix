package com.worldofwaffle.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.worldofwaffle.BaseFragment
import com.worldofwaffle.R
import com.worldofwaffle.databinding.FragmentMenuBinding
import com.worldofwaffle.eventbus.UnboundViewEventBus
import com.worldofwaffle.menu.viewmodel.MenuItemViewModel
import com.worldofwaffle.menu.viewmodel.WaffleMenuViewModel
import com.worldofwaffle.safeSubscribe
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MenuFragment: BaseFragment() {

    @Inject
    lateinit var eventBus: UnboundViewEventBus

    @Inject
    lateinit var viewModel: WaffleMenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentMenuBinding>(inflater,
            R.layout.fragment_menu, container, false)
        viewModel.setDescriptionItems(requireActivity().supportFragmentManager)
        binding.viewModel = viewModel
        viewModel.viewCallbackEmitter = getViewCallbackEmitter()
        return binding.root
    }

    override fun registerUnboundViewEvents(): CompositeDisposable {
        return CompositeDisposable().apply {
            add(eventBus.fordDialog(MenuItemViewModel::class.java).safeSubscribe{showWaffleDialog(it)})
        }
    }

    fun onPageShow() {
        viewModel.setDescriptionItems(requireActivity().supportFragmentManager)
    }
}