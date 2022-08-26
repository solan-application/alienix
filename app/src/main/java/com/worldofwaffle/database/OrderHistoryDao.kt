package com.worldofwaffle.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderHistoryDao {

    @Insert
    fun addOrderHistoryDetail(orderHistoryDetail: OrderHistoryEntity)

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE deliveredStatus =:status")
    fun getDeliveredOrderHistory(status: Int): List<OrderHistoryEntity>

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE userId=:userId")
    fun getSingleOrderHistory(userId: String): OrderHistoryEntity

    @Query("UPDATE ORDER_HISTORY_TABLE SET deliveredStatus=:deliveredStatus WHERE userId = :userId")
    fun updateDeliveredOrderStatus(deliveredStatus: Int, userId: String)

    @Query("UPDATE ORDER_HISTORY_TABLE SET paidStatus=:paidStatus WHERE userId = :userId")
    fun updatePaidStatus(paidStatus: Int, userId: String)

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE dateOfOrder= :date")
    fun getOrderHistory(date: String): List<OrderHistoryEntity>

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE dateOfOrder= :date")
    fun isOrderExist(date: String): Boolean

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE paidStatus=1 AND deliveredStatus = 1")
    fun getCashOrders(): List<OrderHistoryEntity>

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE paidStatus=2 AND deliveredStatus = 1")
    fun getUpiOrders(): List<OrderHistoryEntity>

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE paidStatus=3 AND deliveredStatus = 1")
    fun getZomatoOrders(): List<OrderHistoryEntity>

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE paidStatus=4 AND deliveredStatus = 1")
    fun getZaaroOrders(): List<OrderHistoryEntity>

}