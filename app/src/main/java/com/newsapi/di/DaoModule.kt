package com.newsapi.di

import android.content.Context
import androidx.room.Room
import com.newsapi.db.ArticlesDao
import com.newsapi.db.ArticlesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideArticlesDatabase(@ApplicationContext context: Context): ArticlesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ArticlesDatabase::class.java,
            "news_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideArticlesDao(articlesDatabase: ArticlesDatabase): ArticlesDao {
        return articlesDatabase.articlesDao()
    }

}