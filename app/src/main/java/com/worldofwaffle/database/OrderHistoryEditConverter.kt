package com.worldofwaffle.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.worldofwaffle.OrderHistoryEdit
import com.worldofwaffle.OrderHistoryHeaderItemViewModel
import com.worldofwaffle.OrderedHistoryHeader

class OrderHistoryEditConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromOrderHistoryEdit(orderHistoryEdit: OrderHistoryEdit): String? = gson.toJson(orderHistoryEdit)

    @TypeConverter
    fun toOrderHistoryEdit(string: String): OrderHistoryEdit? = gson.fromJson(string, OrderHistoryEdit::class.java)

}