package com.worldofwaffle.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WaffleFillingsDao {

    @Query("SELECT * FROM WAFFLE_FILLINGS_TABLE")
    fun getWaffleFillings():List<WaffleFillingsEntity>

    @Query("SELECT * FROM WAFFLE_FILLINGS_DETAILS_TABLE WHERE fillingsId =:fillingsId AND currentDate = :currentDate")
    fun getFillingsDetail(fillingsId: String, currentDate: String): WaffleFillingsDetail

    @Insert
    fun addWaffleFillingsDetail(wafffleFillingsDetail: WaffleFillingsDetail)

    @Query("SELECT EXISTS(SELECT * FROM WAFFLE_FILLINGS_DETAILS_TABLE WHERE currentDate = :currentDate AND fillingsId = :fillingsId)")
    fun isExists(currentDate: String, fillingsId: String): Boolean

    @Query("UPDATE WAFFLE_FILLINGS_DETAILS_TABLE SET fillingCount=:fillingsCount WHERE currentDate = :currentDate AND fillingsId =:fillingsId")
    fun updateFillingsCount(fillingsCount: Int, currentDate: String, fillingsId: String)

    @Query("SELECT * FROM WAFFLE_FILLINGS_DETAILS_TABLE WHERE currentDate = :currentDate AND fillingCount != 0.0")
    fun getFillingsDetail(currentDate: String): List<WaffleFillingsDetail>

    @Query("SELECT EXISTS(SELECT * FROM WAFFLE_FILLINGS_DETAILS_TABLE WHERE currentDate = :currentDate)")
    fun isExists(currentDate: String): Boolean
}