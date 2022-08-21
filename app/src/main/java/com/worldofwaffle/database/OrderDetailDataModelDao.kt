package com.worldofwaffle.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDetailDataModelDao {

    @Insert
    fun addOrderedWaffleDetail(orderDetailEntity: OrderDetailEntity)

    @Query("SELECT EXISTS(SELECT * FROM ORDER_DETAIL_TABLE WHERE waffleId = :id)")
    fun isOrderExist(id : String) : Boolean

    @Query("UPDATE ORDER_DETAIL_TABLE SET waffleCount=:count WHERE waffleId = :waffleId AND orderId = :orderId ")
    fun addItemCount(count: Int, waffleId: String, orderId: String)

    @Query("SELECT * FROM ORDER_DETAIL_TABLE WHERE waffleId =:id")
    fun getOrderDetail(id: String): List<OrderDetailEntity>

    @Query("SELECT * FROM ORDER_DETAIL_TABLE")
    fun getAllOrderDetails(): List<OrderDetailEntity>

    @Query("DELETE FROM ORDER_DETAIL_TABLE WHERE waffleId = :waffleId AND orderId = :orderId ")
    fun deleteOrderDetail(waffleId: String, orderId: String)

    @Query("DELETE FROM ORDER_DETAIL_TABLE")
    fun deleteAllOrderDetail()

    @Query("UPDATE ORDER_DETAIL_TABLE SET userOrderId=:userOrderId")
    fun updateUserOrderId(userOrderId: String)

    @Query("UPDATE ORDER_DETAIL_TABLE SET hasTakeAway=:hasTakeAway WHERE waffleId = :waffleId AND orderId = :orderId ")
    fun updateTakeAwayStatue(hasTakeAway: Int, waffleId: String, orderId: String)
}