package com.worldofwaffle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.worldofwaffle.database.OrderDetailRoomDatabase
import com.worldofwaffle.eventbus.StartActivityEvent
import com.worldofwaffle.eventbus.UnboundViewEventBus
import com.worldofwaffle.menu.MenuFragment
import java.util.*
import javax.inject.Inject

class DashBoardViewModel @Inject constructor(private val orderDetailRoomDatabase: OrderDetailRoomDatabase,
private val transientDataProvider: TransientDataProvider,
private val eventBus: UnboundViewEventBus)
    : BaseLifecycleViewModel() {

    private lateinit var adapter: DashboardFragmentPagerAdapter
    private var screens = listOf<Fragment>()

    init {
        screens = setScreens()
    }

    private fun setScreens() = listOf( MenuFragment(), OrderDetailFragment())


    fun onPageChange(position: Int) {
        if (position == 1){
            val orderDetailFragment = screens[1] as OrderDetailFragment
            orderDetailFragment.onPageShow()
        }
    }

    fun setAdapter(fragmentManager: FragmentManager) {
        this.adapter = DashboardFragmentPagerAdapter(fragmentManager, screens)
    }

    fun getAdapter(): DashboardFragmentPagerAdapter = adapter

    fun onNext() {
        val userOrderId = UUID.randomUUID().toString()
        orderDetailRoomDatabase.orderDetailDataModelDao().updateUserOrderId(userOrderId)
        val orderDetailList = orderDetailRoomDatabase.orderDetailDataModelDao().getAllOrderDetails()
        orderDetailRoomDatabase.orderDetailDataModelDao().deleteAllOrderDetail()
        transientDataProvider.save(OrderDetailUseCase(orderDetailList))
        eventBus.send(StartActivityEvent.build(this).activityName(OrdersStateActivity::class.java))
    }
}