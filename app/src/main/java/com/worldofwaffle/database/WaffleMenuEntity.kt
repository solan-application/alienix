package com.worldofwaffle.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WAFFLE_MENU_TABLE")
data class WaffleMenuEntity(@PrimaryKey var id: Int, var waffleId: String, var waffleName: String, var wafflePrice: Int)