package com.worldofwaffle.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.worldofwaffle.OrderHistoryHeaderItemViewModel
import com.worldofwaffle.OrderedHistoryHeader

class OrderHistoryHeaderConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromOrderHistoryHeader(profile: OrderedHistoryHeader): String? = gson.toJson(profile)

    @TypeConverter
    fun toOrderHistoryHeader(string: String): OrderedHistoryHeader? = gson.fromJson(string, OrderedHistoryHeader::class.java)

}