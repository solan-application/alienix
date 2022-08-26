package com.worldofwaffle.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WAFFLE_FILLINGS_TABLE")
data class WaffleFillingsEntity(@PrimaryKey var id: Int, var fillingId: String, var fillingsName: String)