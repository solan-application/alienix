package com.worldofwaffle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.worldofwaffle.database.OrderDetailRoomDatabase
import com.worldofwaffle.eventbus.UnboundViewEventBus
import com.worldofwaffle.menu.MenuFragment
import javax.inject.Inject

class OrderEditViewModel @Inject constructor(private val orderDetailRoomDatabase: OrderDetailRoomDatabase,
                                             private val transientDataProvider: TransientDataProvider,
                                             private val eventBus: UnboundViewEventBus) : BaseLifecycleViewModel() {

    private lateinit var adapter: DashboardFragmentPagerAdapter
    private var screens = listOf<Fragment>()

    fun setAdapter(fragmentManager: FragmentManager) {
        this.adapter = DashboardFragmentPagerAdapter(fragmentManager, screens)
    }

    fun getAdapter(): DashboardFragmentPagerAdapter = adapter

    init {
        screens = setScreens()
    }

    private fun setScreens() = listOf( MenuFragment(), OrderDetailFragment())

    fun onClickReConfirmOrder() {
        val existingUserOrderId = orderDetailRoomDatabase.userOrderIdDao().getUserOrderId().userOrderId
        val orderDetailList = orderDetailRoomDatabase.orderDetailDataModelDao().getAllOrderDetails(existingUserOrderId)
        transientDataProvider.save(OrderEditStatusUseCase(true, orderDetailList))
        eventBus.send(FinishActivityEvent.build(this).finishActivityEvent())
    }

    fun onClickCancelOrder() {

    }

    fun onPageChange(position: Int) {
        if (position == 1){
            val orderDetailFragment = screens[1] as OrderDetailFragment
            orderDetailFragment.onPageShow()
        }
    }

}