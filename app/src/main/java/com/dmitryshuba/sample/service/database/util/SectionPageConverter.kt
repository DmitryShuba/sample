package com.dmitryshuba.sample.service.database.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.dmitryshuba.sample.service.database.model.SectionEntity

class SectionPageConverter {

    private var gson = Gson()

    @TypeConverter
    fun sectionListStringToEntityList(data: String?): List<SectionEntity>? {
        if (data.isNullOrEmpty()) {
            return null
        }
        val listType = object : TypeToken<List<SectionEntity>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun entityListToString(list: List<SectionEntity>?): String? = gson.toJson(list)
}