package com.worldofwaffle

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.worldofwaffle.databinding.ActivityHomeBinding
import com.worldofwaffle.databinding.ActivityWaffleFillingsBinding
import com.worldofwaffle.eventbus.UnboundViewEventBus
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class WaffleFillingsActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: WaffleFillingsViewModel

    @Inject
    lateinit var eventBus: UnboundViewEventBus

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding: ActivityWaffleFillingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_waffle_fillings)
        binding.viewModel = viewModel
        viewModel.viewCallbackEmitter = getViewCallbackEmitter()
    }

    /*override fun registerUnboundViewEvents() = CompositeDisposable().apply {
        add(eventBus.startActivity(CommonHomeItemViewModel::class.java).safeSubscribe { startActivity(it) })
    }*/
}