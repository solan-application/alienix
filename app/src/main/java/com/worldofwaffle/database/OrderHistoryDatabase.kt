package com.worldofwaffle.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [OrderHistoryEntity::class], version = 1, exportSchema = false)
abstract class OrderHistoryDatabase: RoomDatabase() {
    abstract fun orderStateDao(): OrderHistoryDao
}