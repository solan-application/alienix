package com.worldofwaffle.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CASH_IN_BOX_TABLE")
data class CashInBoxEntity(@PrimaryKey(autoGenerate = true) var id: Int = 0, val cashIn: Int, val cashOut: Int)