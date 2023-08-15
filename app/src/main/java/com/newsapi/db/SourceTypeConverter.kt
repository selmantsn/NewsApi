package com.newsapi.db

import androidx.room.TypeConverter
import com.newsapi.model.response.Source

class SourceTypeConverter {

    @TypeConverter
    fun sourceToString(source: Source?): String? {
        return source?.name
    }

    @TypeConverter
    fun stringToSource(name: String?): Source? {
        return name?.let { Source(it, it) }
    }

}