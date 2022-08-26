package com.worldofwaffle

import android.util.Log
import androidx.databinding.ObservableField
import com.worldofwaffle.database.HomeDatabase
import com.worldofwaffle.database.WaffleMixEntity
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WaffleMixItemViewModel @Inject constructor(private val homeDatabase: HomeDatabase) : BaseHomeItemViewModel() {

    val usedKg = ObservableField("")

    init {
       if(isExist()){
           val waffleMixEntity = homeDatabase.waffleMixDao().getWaffleMixKg(getCurrentDate())
           usedKg.set("UsedKg: ${waffleMixEntity.mixInKg}")
       }else {
           usedKg.set("UsedKg: 0")
       }
    }

    fun onClickKgs(gram: Int) {
        val kg = gram / 1000.0;
        val todayDate: String = getCurrentDate()
        Log.e("WaffleMix In Kg" , ""+kg.toInt())

        val mixInKg = if (isExist()) {homeDatabase.waffleMixDao().getWaffleMixKg(todayDate).mixInKg} else 0.0
        val updatedKg = kg.plus( mixInKg)
        Log.e("WaffleMix updated In Kg" , ""+updatedKg)
        if (isExist())
        homeDatabase.waffleMixDao().updateMixInKg(updatedKg, todayDate)
        else
            homeDatabase.waffleMixDao().addWaffleMixDetail(WaffleMixEntity(0, getCurrentDate(), updatedKg))
        usedKg.set("UsedKg: $updatedKg")
    }

    private fun isExist() = homeDatabase.waffleMixDao().isExists(getCurrentDate())

    private fun getCurrentDate() = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

    class Factory @Inject constructor(private val homeDatabase: HomeDatabase) {

        fun newInstance(): WaffleMixItemViewModel {
            return WaffleMixItemViewModel(homeDatabase)
        }
    }

}