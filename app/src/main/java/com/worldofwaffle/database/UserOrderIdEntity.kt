package com.worldofwaffle.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.worldofwaffle.menu.AddOnItem

@Entity(tableName = "USER_ORDER_ID_TABLE")
data class UserOrderIdEntity(@PrimaryKey(autoGenerate = true) val id: Int = 1, val userOrderId: String)