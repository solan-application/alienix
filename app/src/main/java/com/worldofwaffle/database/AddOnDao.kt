package com.worldofwaffle.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AddOnDao {

    @Query("SELECT * FROM ADD_ON_TABLE")
    fun getAllAddOns(): List<AddOnEntity>
}