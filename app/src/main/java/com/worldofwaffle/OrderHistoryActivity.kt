package com.worldofwaffle

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.worldofwaffle.databinding.ActivityOrderHistoryBinding
import com.worldofwaffle.eventbus.UnboundViewEventBus
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class OrderHistoryActivity: BaseActivity() {

    @Inject
    lateinit var viewModel: OrderHistoryViewModel

    @Inject
    lateinit var eventBus: UnboundViewEventBus

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding: ActivityOrderHistoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_history)
        binding.viewModel = viewModel
        viewModel.viewCallbackEmitter = getViewCallbackEmitter()
    }

    override fun registerUnboundViewEvents() = CompositeDisposable().apply {
        add(eventBus.fordDialog(OrderHistoryHeaderItemViewModel::class.java).safeSubscribe { showWaffleDialog(it) })
    }
}