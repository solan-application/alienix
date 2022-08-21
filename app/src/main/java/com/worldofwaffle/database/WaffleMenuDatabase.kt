package com.worldofwaffle.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WaffleMenuEntity::class], version = 1, exportSchema = false)
abstract class WaffleMenuDatabase: RoomDatabase() {
    abstract fun waffleMenuDao(): WaffleMenuDao
}