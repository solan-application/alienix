package com.worldofwaffle.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface WaffleMenuDao {

    @Query("SELECT * FROM WAFFLE_MENU_TABLE")
    fun getWaffleMenu():List<WaffleMenuEntity>

    @Query("SELECT * FROM WAFFLE_MENU_TABLE WHERE waffleID =:id")
    fun getWaffleDetail(id: String): WaffleMenuEntity
}