package com.worldofwaffle

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.worldofwaffle.databinding.ActivityHomeBinding
import com.worldofwaffle.eventbus.UnboundViewEventBus
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var eventBus: UnboundViewEventBus

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewModel = viewModel
        viewModel.viewCallbackEmitter = getViewCallbackEmitter()
    }

    override fun registerUnboundViewEvents() = CompositeDisposable().apply {
        add(eventBus.startActivity(CommonHomeItemViewModel::class.java).safeSubscribe { startActivity(it) })
    }
}