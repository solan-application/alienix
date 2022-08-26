package com.worldofwaffle.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WaffleFillingsEntity::class, WaffleFillingsDetail::class], version = 1, exportSchema = false)
abstract class WaffleFillingsDatabase: RoomDatabase() {
    abstract fun waffleFillingsDao(): WaffleFillingsDao
}