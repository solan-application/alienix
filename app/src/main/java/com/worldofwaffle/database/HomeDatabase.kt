package com.worldofwaffle.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WaffleMixEntity::class, CashInBoxEntity::class], version = 1, exportSchema = false)
abstract class HomeDatabase: RoomDatabase() {
    abstract fun waffleMixDao(): WaffleMixDao
    abstract fun cashInBoxDao(): CashInBoxDao
}