package com.example.wannahelp.data.db

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun toStringList(str: String): List<String> {
        return if (str.isEmpty()) emptyList() else str.split(",")
    }
}
