package com.worldofwaffle.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderHistoryDao {

    @Insert
    fun addOrderHistoryDetail(orderHistoryDetail: OrderHistoryEntity)

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE deliveredStatus = 0")
    fun getUnDeliveredOrderHistory(): List<OrderHistoryEntity>

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE userId=:userId")
    fun getPaidStatus(userId: String): OrderHistoryEntity

    @Query("UPDATE ORDER_HISTORY_TABLE SET deliveredStatus=:deliveredStatus WHERE userId = :userId")
    fun updateDeliveredOrderStatus(deliveredStatus: Int, userId: String)

    @Query("UPDATE ORDER_HISTORY_TABLE SET paidStatus=:paidStatus WHERE userId = :userId")
    fun updatePaidStatus(paidStatus: Int, userId: String)
}