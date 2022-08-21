package com.worldofwaffle.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ADD_ON_TABLE")
data class AddOnEntity(@PrimaryKey var id: Int, val addOnId: String, val addOnName: String, val addOnPrice: Int)