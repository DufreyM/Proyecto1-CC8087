package com.example.waterreminder.dao

import androidx.room.TypeConverter
import com.example.waterreminder.api.Condition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConditionConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromCondition(condition: Condition): String {
        return gson.toJson(condition)
    }

    @TypeConverter
    fun toCondition(conditionString: String): Condition {
        val type = object : TypeToken<Condition>() {}.type
        return gson.fromJson(conditionString, type)
    }
}
