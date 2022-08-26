package com.worldofwaffle.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WAFFLE_FILLINGS_DETAILS_TABLE")
data class WaffleFillingsDetail(@PrimaryKey(autoGenerate = true) var id: Int = 0, var fillingsId: String,
                                var fillingsName: String, var fillingCount: Int, var currentDate: String)