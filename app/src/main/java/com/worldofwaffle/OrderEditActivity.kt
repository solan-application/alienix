package com.worldofwaffle

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.worldofwaffle.databinding.ActivityOrderEditBinding
import com.worldofwaffle.eventbus.UnboundViewEventBus
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class OrderEditActivity: BaseActivity() {

    @Inject
    lateinit var viewModel: OrderEditViewModel

    @Inject
    lateinit var eventBus: UnboundViewEventBus

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding: ActivityOrderEditBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_edit)
        viewModel.setAdapter(supportFragmentManager)
        binding.viewModel = viewModel
        viewModel.viewCallbackEmitter = getViewCallbackEmitter()
    }

    override fun registerUnboundViewEvents() = CompositeDisposable().apply {
        add(eventBus.finishActivity(viewModel).safeSubscribe { finishActivity(it) })
    }
}