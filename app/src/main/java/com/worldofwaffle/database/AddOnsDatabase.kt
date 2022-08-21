package com.worldofwaffle.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AddOnEntity::class], version = 1, exportSchema = false)
abstract class AddOnsDatabase: RoomDatabase() {
    abstract fun addOnDao(): AddOnDao
}