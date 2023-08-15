package com.newsapi.di

import com.newsapi.api.ServiceInterface
import com.newsapi.repository.ServiceRepository
import com.newsapi.repository.ServiceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun serviceRepository(serviceInterface: ServiceInterface): ServiceRepository {
        return ServiceRepositoryImpl(serviceInterface)
    }

}