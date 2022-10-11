package com.worldofwaffle.database

import androidx.room.*
import com.worldofwaffle.OrderedHistoryDetail
import com.worldofwaffle.OrderedHistoryHeader

@Dao
interface OrderHistoryDao {

    @Insert
    fun addOrderHistoryDetail(orderHistoryDetail: OrderHistoryEntity)

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE deliveredStatus =:status ORDER BY timeOfOrder DESC")
    fun getDeliveredOrderHistory(status: Int): List<OrderHistoryEntity>

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE userId=:userId")
    fun getSingleOrderHistory(userId: String): OrderHistoryEntity

    @Query("UPDATE ORDER_HISTORY_TABLE SET deliveredStatus=:deliveredStatus WHERE userId = :userId")
    fun updateDeliveredOrderStatus(deliveredStatus: Int, userId: String)

    @Query("UPDATE ORDER_HISTORY_TABLE SET paidStatus=:paidStatus WHERE userId = :userId")
    fun updatePaidStatus(paidStatus: Int, userId: String)

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE dateOfOrder= :date")
    fun getOrderHistory(date: String): List<OrderHistoryEntity>

    @Update
    fun updateEditedOrderHistory(orderHistoryDetail: OrderHistoryEntity)

    @Query("DELETE FROM ORDER_HISTORY_TABLE WHERE userId = :userOrderId")
    fun deleteOrderHistory(userOrderId: String)

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE dateOfOrder= :date")
    fun isOrderExist(date: String): Boolean

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE dateOfOrder= :date AND paidStatus=1 AND deliveredStatus = 1")
    fun getCashOrders(date: String): List<OrderHistoryEntity>

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE dateOfOrder= :date AND paidStatus=2 AND deliveredStatus = 1")
    fun getUpiOrders(date: String): List<OrderHistoryEntity>

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE dateOfOrder= :date AND paidStatus=3 AND deliveredStatus = 1")
    fun getZomatoOrders(date: String): List<OrderHistoryEntity>

    @Query("SELECT * FROM ORDER_HISTORY_TABLE WHERE dateOfOrder= :date AND paidStatus=4 AND deliveredStatus = 1")
    fun getZaaroOrders(date: String): List<OrderHistoryEntity>

}