package com.worldofwaffle

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.worldofwaffle.databinding.ActivityOrderStateBinding
import com.worldofwaffle.eventbus.UnboundViewEventBus
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class OrdersStateActivity: BaseActivity() {

    @Inject
    lateinit var viewModel: OrderStateViewModel

    @Inject
    lateinit var eventBus: UnboundViewEventBus

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding: ActivityOrderStateBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_state)
        binding.viewModel = viewModel
        viewModel.viewCallbackEmitter = getViewCallbackEmitter()
    }

    override fun registerUnboundViewEvents() = CompositeDisposable().apply {
        add(eventBus.fordDialog(OrderHistoryHeaderItemViewModel::class.java).safeSubscribe { showWaffleDialog(it) })
    }
}