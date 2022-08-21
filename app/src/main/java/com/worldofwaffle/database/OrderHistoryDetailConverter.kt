package com.worldofwaffle.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.worldofwaffle.OrderedHistoryDetail

class OrderHistoryDetailConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromBaseOrderHistory(orderHistoryDetail: List<OrderedHistoryDetail>): String {
        val type = object : TypeToken<List<OrderedHistoryDetail>>() {}.type
        return gson.toJson(orderHistoryDetail, type)
    }

    @TypeConverter
    fun toBaseOrderHistory(json: String): List<OrderedHistoryDetail> {
        val type = object : TypeToken<List<OrderedHistoryDetail>>() {}.type
        return gson.fromJson(json, type)
    }
}