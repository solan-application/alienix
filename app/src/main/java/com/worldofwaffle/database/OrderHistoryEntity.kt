package com.worldofwaffle.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.worldofwaffle.OrderHistoryDetailItemViewModel
import com.worldofwaffle.OrderHistoryHeaderItemViewModel
import com.worldofwaffle.OrderedHistoryDetail
import com.worldofwaffle.OrderedHistoryHeader

@Entity(tableName = "ORDER_HISTORY_TABLE")
@TypeConverters(OrderHistoryDetailConverter::class, OrderHistoryHeaderConverter::class)
data class OrderHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val userId: String,
    val orderHistoryHeader: OrderedHistoryHeader,
    val orderHistoryDetail: List<OrderedHistoryDetail>,
    val deliveredStatus: Int = 0,
    val paidStatus: Int = 0
)
