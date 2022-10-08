package com.worldofwaffle.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserOrderIdDao {

    @Insert
    fun addUserOrderId(userOrderIdEntity: UserOrderIdEntity)

    @Query("SELECT * FROM USER_ORDER_ID_TABLE")
    fun getUserOrderId(): UserOrderIdEntity

    @Query("UPDATE USER_ORDER_ID_TABLE SET userOrderId=:userOrderId")
    fun updateUserOrderId(userOrderId: String)

    @Query("SELECT EXISTS(SELECT * FROM USER_ORDER_ID_TABLE)")
    fun isUserOrderIdExist() : Boolean
}