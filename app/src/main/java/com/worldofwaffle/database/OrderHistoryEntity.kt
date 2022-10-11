package com.worldofwaffle.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.worldofwaffle.*
import java.util.*

@Entity(tableName = "ORDER_HISTORY_TABLE")
@TypeConverters(OrderHistoryDetailConverter::class, OrderHistoryHeaderConverter::class, OrderHistoryEditConverter::class)
data class OrderHistoryEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val userId: String,
    val dateOfOrder: String,
    val timeOfOrder: String,
    val orderHistoryEdit: OrderHistoryEdit,
    val orderHistoryHeader: OrderedHistoryHeader,
    val orderHistoryDetail: List<OrderedHistoryDetail>,
    val deliveredStatus: Int = 0,
    val paidStatus: Int = 0
)
