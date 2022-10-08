package com.worldofwaffle.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDetailDataModelDao {

    @Insert
    fun addOrderedWaffleDetail(orderDetailEntity: OrderDetailEntity)

    @Query("SELECT EXISTS(SELECT * FROM ORDER_DETAIL_TABLE WHERE userOrderId =:userOrderId AND waffleId = :waffleId)")
    fun isOrderExist(userOrderId: String, waffleId : String) : Boolean

    @Query("UPDATE ORDER_DETAIL_TABLE SET waffleCount=:count WHERE userOrderId =:userOrderId AND waffleId = :waffleId AND orderId = :orderId ")
    fun addItemCount(count: Int, userOrderId: String, waffleId: String, orderId: String)

    @Query("SELECT * FROM ORDER_DETAIL_TABLE WHERE userOrderId =:userOrderId AND waffleId =:waffleId")
    fun getOrderDetail(userOrderId: String, waffleId: String): List<OrderDetailEntity>

    @Query("SELECT * FROM ORDER_DETAIL_TABLE WHERE userOrderId =:userOrderId")
    fun getAllOrderDetails(userOrderId: String): List<OrderDetailEntity>

    @Query("DELETE FROM ORDER_DETAIL_TABLE WHERE userOrderId =:userOrderId AND waffleId = :waffleId AND orderId = :orderId ")
    fun deleteOrderDetail(userOrderId: String, waffleId: String, orderId: String)

    @Query("DELETE FROM ORDER_DETAIL_TABLE WHERE userOrderId = :userOrderId")
    fun deleteAllOrderDetail(userOrderId: String)

    /*@Query("UPDATE ORDER_DETAIL_TABLE SET userOrderId=:userOrderId")
    fun updateUserOrderId(userOrderId: String)
*/
    @Query("UPDATE ORDER_DETAIL_TABLE SET hasTakeAway=:hasTakeAway WHERE userOrderId =:userOrderId AND waffleId = :waffleId AND orderId = :orderId ")
    fun updateTakeAwayStatue(hasTakeAway: Int, userOrderId: String, waffleId: String, orderId: String)
}