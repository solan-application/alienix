package com.worldofwaffle

import androidx.databinding.ObservableInt
import com.worldofwaffle.database.CashInBoxEntity
import com.worldofwaffle.database.HomeDatabase
import javax.inject.Inject

class CashInBoxItemViewModel(private val homeDatabase: HomeDatabase): BaseHomeItemViewModel() {

    val cashIn = ObservableInt(0)
    val cashOut = ObservableInt(0)
    init {
        if (homeDatabase.cashInBoxDao().isExist()){
            val cashBox = homeDatabase.cashInBoxDao().getCashBox()
            cashIn.set(cashBox.cashIn)
            cashOut.set(cashBox.cashOut)
        }
    }

    fun onSave() {
        if (homeDatabase.cashInBoxDao().isExist()) {
            homeDatabase.cashInBoxDao().updateCashBox(cashIn = cashIn.get(), cashOut = cashOut.get())
        }else {
            homeDatabase.cashInBoxDao().addCashInBoxDetail(CashInBoxEntity(cashIn = cashIn.get(), cashOut = cashOut.get()))
        }
        val cashBox = homeDatabase.cashInBoxDao().getCashBox()
        cashIn.set(cashBox.cashIn)
        cashOut.set(cashBox.cashOut)
    }

    class Factory @Inject constructor(private val homeDatabase: HomeDatabase) {

        fun newInstance(): CashInBoxItemViewModel {
            return CashInBoxItemViewModel(homeDatabase)
        }
    }
}