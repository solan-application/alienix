package com.worldofwaffle

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.worldofwaffle.databinding.ActivityDashboardBinding
import com.worldofwaffle.eventbus.UnboundViewEventBus
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DashBoardActivity: BaseActivity() {

    @Inject
    lateinit var viewModel: DashBoardViewModel

    @Inject
    lateinit var eventBus: UnboundViewEventBus

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding: ActivityDashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        viewModel.setAdapter(supportFragmentManager)
        binding.viewModel = viewModel
        viewModel.viewCallbackEmitter = getViewCallbackEmitter()
    }

    override fun registerUnboundViewEvents() = CompositeDisposable().apply {
        add(eventBus.startActivity(viewModel).safeSubscribe { startActivity(it) })
    }
}