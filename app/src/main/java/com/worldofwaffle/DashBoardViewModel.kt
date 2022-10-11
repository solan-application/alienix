package com.worldofwaffle

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.worldofwaffle.database.*
import com.worldofwaffle.eventbus.StartActivityEvent
import com.worldofwaffle.eventbus.UnboundViewEventBus
import com.worldofwaffle.menu.MenuFragment
import com.worldofwaffle.menu.adapter.MenuAdapter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class DashBoardViewModel @Inject constructor(
    private val orderDetailRoomDatabase: OrderDetailRoomDatabase,
    private val orderHistoryDatabase: OrderHistoryDatabase,
    private val fillingsDatabase: WaffleFillingsDatabase,
    private val homeDatabase: HomeDatabase,
    private val transientDataProvider: TransientDataProvider,
    private val eventBus: UnboundViewEventBus,
    val menuAdapter: MenuAdapter,
    val userOrderIdUtil: UserOrderIdUtil)
    : BaseLifecycleViewModel() {

    private lateinit var adapter: DashboardFragmentPagerAdapter
    private var screens = listOf<Fragment>()

    init {
        screens = setScreens()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        if (!userOrderIdUtil.isUserOrderIdExist()) {
            userOrderIdUtil.createUserOrderId()
        }else {
            userOrderIdUtil.updateUserOrderId()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
       /* if (transientDataProvider.containsUseCase(OrderHistoryBackUseCase::class.java)) {
            transientDataProvider.remove(OrderHistoryBackUseCase::class.java)
            userOrderIdUtil.updateUserOrderId()
        }*/
    }



    private fun setScreens() = listOf( MenuFragment(), OrderDetailFragment())


    fun onPageChange(position: Int) {
        if (position == 1){
            val orderDetailFragment = screens[1] as OrderDetailFragment
            orderDetailFragment.onPageShow()
        }else if (position == 0) {
            val menuFragment = screens[0] as MenuFragment
            menuFragment.onPageShow()
        }
    }

    fun setAdapter(fragmentManager: FragmentManager) {
        this.adapter = DashboardFragmentPagerAdapter(fragmentManager, screens)
    }

    fun getAdapter(): DashboardFragmentPagerAdapter = adapter

    fun onCancelOrder() {
        val existingUserOrderId = userOrderIdUtil.getUserOrderId()
        orderDetailRoomDatabase.orderDetailDataModelDao().deleteAllOrderDetail(existingUserOrderId)
        userOrderIdUtil.updateUserOrderId()
    }

    fun onClickHome() {
        eventBus.send(StartActivityEvent.build(this).activityName(HomeActivity::class.java))
    }

    fun onClickShare() {

       // val uri = Uri.parse("smsto: 9677345243")
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$919176014015&text=${getTodayReport()}")
        val i = Intent(Intent.ACTION_VIEW, uri)
        i.setPackage("com.whatsapp")

        eventBus.send(StartActivityEvent.build(this).setIntent(i).launchExternalApplication(true))
    }

    private fun getTodayReport(): String {
        var cashOrderTotal = 0
        var upiOrderTotal = 0
        var zomatoOrderTotal = 0
        var zaarozOrderTotal = 0
        var fillingsNameAndCount = "FILLINGS: \n ---------- \n"
        var mixCount = "WAFFLEMIX: \n ----------- \n"

        val cashOrders = orderHistoryDatabase.orderStateDao().getCashOrders(getCurrentDate())
        cashOrders.map { cashOrderTotal += it.orderHistoryHeader.itemTotalPrice.toInt()  }
        val upiOrders = orderHistoryDatabase.orderStateDao().getUpiOrders(getCurrentDate())
        upiOrders.map { upiOrderTotal += it.orderHistoryHeader.itemTotalPrice.toInt()  }
        val zomatoOrders = orderHistoryDatabase.orderStateDao().getZomatoOrders(getCurrentDate())
        zomatoOrders.map { zomatoOrderTotal += it.orderHistoryHeader.itemTotalPrice.toInt()  }
        val zaarozOrders = orderHistoryDatabase.orderStateDao().getZaaroOrders(getCurrentDate())
        zaarozOrders.map { zaarozOrderTotal += it.orderHistoryHeader.itemTotalPrice.toInt()  }

        if (homeDatabase.cashInBoxDao().isExist()) {
            val cashBox = homeDatabase.cashInBoxDao().getCashBox()
            var remainingCash = 0
            if (cashBox.cashIn > 0)
             remainingCash = cashBox.cashIn - cashBox.cashOut
            cashOrderTotal += remainingCash
        }

        val totalPrice = "TOTAL CASH: \n --------- \n cash: $cashOrderTotal\n  upiCash: $upiOrderTotal\n " +
                " zomatoCash: $zomatoOrderTotal\n  ZaroozCash: $zaarozOrderTotal\n"

        if(fillingsDatabase.waffleFillingsDao().isExists(getCurrentDate())) {
            val fillingsDetail =
                fillingsDatabase.waffleFillingsDao().getFillingsDetail(getCurrentDate())
            fillingsDetail.map {
                fillingsNameAndCount += it.fillingsName + ": " + it.fillingCount.toString() + "\n"
            }
        }

        if(homeDatabase.waffleMixDao().isExists(getCurrentDate())) {
               mixCount += homeDatabase.waffleMixDao().getWaffleMixKg(getCurrentDate()).mixInKg.toString()
        }

        return totalPrice.plus("\n"+fillingsNameAndCount).plus("\n" +
                mixCount)
    }

    fun onUnDeliverOrders() {
        transientDataProvider.save(OrderHistoryStatusUseCase(DASHBOARD_SCREEN))
        eventBus.send(StartActivityEvent.build(this).activityName(OrderHistoryActivity::class.java))
    }

    private fun getCurrentDate() = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())


    fun onNext() {
        val existingUserOrderId = userOrderIdUtil.getUserOrderId()
        Log.e("userID", "confirm order $existingUserOrderId")
        if (orderDetailRoomDatabase.orderDetailDataModelDao().getAllOrderDetails(existingUserOrderId).isNotEmpty()) {
            userOrderIdUtil.updateUserOrderId()
            val orderDetailList =
                orderDetailRoomDatabase.orderDetailDataModelDao().getAllOrderDetails(existingUserOrderId)
            transientDataProvider.save(OrderDetailUseCase(orderDetailList))
            eventBus.send(
                StartActivityEvent.build(this).activityName(OrderHistoryActivity::class.java)
            )
            onPageChange(1)
            onPageChange(0)
        }
    }
}