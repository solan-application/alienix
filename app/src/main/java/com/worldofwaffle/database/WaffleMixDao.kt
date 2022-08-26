package com.worldofwaffle.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WaffleMixDao {

    @Query("SELECT * FROM WAFFLE_MIX_TABLE WHERE currentDate = :currentDate")
    fun getWaffleMixKg(currentDate: String): WaffleMixEntity

    @Query("UPDATE WAFFLE_MIX_TABLE SET mixInKg=:mixInKg WHERE currentDate = :currentDate")
    fun updateMixInKg(mixInKg: Double = 0.0, currentDate: String)

    @Query("SELECT EXISTS(SELECT * FROM WAFFLE_MIX_TABLE WHERE currentDate = :currentDate)")
    fun isExists(currentDate: String): Boolean

    @Insert
    fun addWaffleMixDetail(waffleMixEntity: WaffleMixEntity)
}