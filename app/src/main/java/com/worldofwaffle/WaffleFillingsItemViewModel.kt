package com.worldofwaffle

import androidx.databinding.ObservableField
import com.worldofwaffle.database.WaffleFillingsDatabase
import com.worldofwaffle.database.WaffleFillingsDetail
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WaffleFillingsItemViewModel(private val waffleFillingsDatabase: WaffleFillingsDatabase,
                                  private val waffleFillingId: String,
                                  private val waffleFillingName: String): BaseLifecycleViewModel() {

    val fillingsName = ObservableField(waffleFillingName)
    val fillingsCount = ObservableField("")

    init {
        if (isExist()) {
           val waffleFillingCount = waffleFillingsDatabase.waffleFillingsDao()
                .getFillingsDetail(waffleFillingId, getCurrentDate()).fillingCount
            fillingsCount.set("TAKEN: $waffleFillingCount")
        }
    }

    fun onClick() {
        var waffleFillingCount = 1
        if (isExist()) {
            waffleFillingCount = waffleFillingsDatabase.waffleFillingsDao()
                .getFillingsDetail(waffleFillingId, getCurrentDate()).fillingCount
            waffleFillingCount += 1
            waffleFillingsDatabase.waffleFillingsDao().updateFillingsCount(waffleFillingCount,
                getCurrentDate(), waffleFillingId)
        }else {
            val waffleFillingsDetail = WaffleFillingsDetail(fillingsId = waffleFillingId, fillingsName = waffleFillingName, fillingCount = waffleFillingCount, currentDate = getCurrentDate())
            waffleFillingsDatabase.waffleFillingsDao().addWaffleFillingsDetail(waffleFillingsDetail)
        }
        fillingsCount.set("TAKEN: $waffleFillingCount")
    }

    private fun isExist() = waffleFillingsDatabase.waffleFillingsDao().isExists(getCurrentDate(), waffleFillingId)

    private fun getCurrentDate() = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())


    class Factory @Inject constructor(private val waffleFillingsDatabase: WaffleFillingsDatabase) {
        fun newInstance(
            waffleFillingId: String,
            waffleFillingName: String): WaffleFillingsItemViewModel {
            return WaffleFillingsItemViewModel(waffleFillingsDatabase, waffleFillingId, waffleFillingName)
        }
    }
}