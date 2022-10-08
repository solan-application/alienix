package com.worldofwaffle.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.worldofwaffle.menu.AddOnItem

@Entity(tableName = "ORDER_DETAIL_TABLE")
@TypeConverters(AddOnItemConverter::class)
data class OrderDetailEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userOrderId: String,
    val waffleId: String,
    val orderId: String,
    val waffleCount:Int = 1,
    val hasTakeAway: Int = 0,
    val addedOnItemDetails: List<AddOnItem> = listOf()
)