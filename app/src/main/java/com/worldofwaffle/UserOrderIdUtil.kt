package com.worldofwaffle

import android.util.Log
import com.worldofwaffle.database.OrderDetailRoomDatabase
import com.worldofwaffle.database.UserOrderIdEntity
import java.util.*
import javax.inject.Inject

class UserOrderIdUtil @Inject constructor(private val orderDetailRoomDatabase: OrderDetailRoomDatabase) {

    fun createUserOrderId() {
        val userOrderId = UUID.randomUUID().toString()
        orderDetailRoomDatabase.userOrderIdDao().addUserOrderId(UserOrderIdEntity(userOrderId = userOrderId))
        Log.e("UserID", "Created "+ getUserOrderId())
    }

    fun updateUserOrderId() {
        val userOrderId = UUID.randomUUID().toString()
        orderDetailRoomDatabase.userOrderIdDao().updateUserOrderId(userOrderId)
        Log.e("UserID", "updated "+ getUserOrderId())
    }

    fun getUserOrderId(): String  {
        return orderDetailRoomDatabase.userOrderIdDao().getUserOrderId().userOrderId
    }

    fun isUserOrderIdExist(): Boolean {
        return orderDetailRoomDatabase.userOrderIdDao().isUserOrderIdExist()
    }

    fun updateEditingUserOrderId(userOrderId: String) {
        orderDetailRoomDatabase.userOrderIdDao().updateUserOrderId(userOrderId)
        Log.e("UserID", "update Edited "+ getUserOrderId())
    }

}