package com.worldofwaffle.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CashInBoxDao {

    @Insert
    fun addCashInBoxDetail(cashInBoxEntity: CashInBoxEntity)

    @Query("SELECT * FROM CASH_IN_BOX_TABLE")
    fun isExist(): Boolean

    @Query("SELECT * FROM CASH_IN_BOX_TABLE")
    fun getCashBox(): CashInBoxEntity

    @Query("UPDATE CASH_IN_BOX_TABLE SET cashIn=:cashIn, cashOut= :cashOut")
    fun updateCashBox(cashIn: Int, cashOut: Int)
}