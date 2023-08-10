package com.newsapi.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.newsapi.api.Articles

@Database(entities = [Articles::class], version = 1, exportSchema = false)
@TypeConverters(SourceTypeConverter::class)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao

}