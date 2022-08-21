package com.worldofwaffle.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.worldofwaffle.menu.AddOnItem

class AddOnItemConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromAddOnItem(addOnItems: List<AddOnItem>): String {
        val type = object : TypeToken<List<AddOnItem>>() {}.type
        return gson.toJson(addOnItems, type)
    }

    @TypeConverter
    fun toAddOnItem(json: String): List<AddOnItem> {
        val type = object : TypeToken<List<AddOnItem>>() {}.type
        return gson.fromJson(json, type)
    }
}