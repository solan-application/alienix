package com.worldofwaffle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.worldofwaffle.database.OrderDetailRoomDatabase
import com.worldofwaffle.eventbus.UnboundViewEventBus
import com.worldofwaffle.menu.MenuFragment
import javax.inject.Inject

class OrderEditViewModel @Inject constructor(private val orderDetailRoomDatabase: OrderDetailRoomDatabase,
                                             private val transientDataProvider: TransientDataProvider,
                                             private val eventBus: UnboundViewEventBus,
private val userOrderIdUtil: UserOrderIdUtil) : BaseLifecycleViewModel() {

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
        val existingUserOrderId = userOrderIdUtil.getUserOrderId()
        val orderDetailList = orderDetailRoomDatabase.orderDetailDataModelDao().getAllOrderDetails(existingUserOrderId)
        transientDataProvider.save(OrderEditStatusUseCase(true, orderDetailList))
        eventBus.send(FinishActivityEvent.build(this).finishActivityEvent())
    }

    fun onClickCancelOrder() {
        transientDataProvider.save(OrderEditStatusUseCase(true, listOf()))
        eventBus.send(FinishActivityEvent.build(this).finishActivityEvent())
    }

    fun onPageChange(position: Int) {
        if (position == 1){
            val orderDetailFragment = screens[1] as OrderDetailFragment
            orderDetailFragment.onPageShow()
        }
    }

}