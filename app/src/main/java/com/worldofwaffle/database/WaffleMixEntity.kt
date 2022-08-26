package com.worldofwaffle.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WAFFLE_MIX_TABLE")
data class WaffleMixEntity(@PrimaryKey var id: Int = 0, val currentDate: String, val mixInKg: Double)