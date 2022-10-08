package com.worldofwaffle

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.worldofwaffle.databinding.FragmentOrderDetailBinding
import com.worldofwaffle.databinding.FragmentRepeatOrderBinding
import dagger.android.support.DaggerAppCompatDialogFragment
import javax.inject.Inject

class RepeatOrderFragment(private val onDismissedListener: () -> Unit): DaggerAppCompatDialogFragment() {

    @Inject
    lateinit var viewModel: RepeatOrderViewModel

    private val viewCallbackEmitter = ViewCallbackEmitter()

    private lateinit var onDismissListener: () -> Unit


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.onDismissListener = onDismissedListener
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewCallbackEmitter.init(lifecycle)
        val binding = DataBindingUtil.inflate<FragmentRepeatOrderBinding>(inflater,
            R.layout.fragment_repeat_order, container, false)
        binding.viewModel = viewModel
        viewModel.viewCallbackEmitter = viewCallbackEmitter
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme)
    }

    override fun onStop() {
        super.onStop()
        onDismissListener.invoke()
    }

}