package com.worldofwaffle.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [OrderDetailEntity::class, UserOrderIdEntity::class], version = 1, exportSchema = false)
abstract class OrderDetailRoomDatabase: RoomDatabase() {
    abstract fun orderDetailDataModelDao(): OrderDetailDataModelDao
    abstract fun userOrderIdDao(): UserOrderIdDao
}