package com.newsapi.repository

import com.newsapi.api.ServiceInterface
import com.newsapi.model.request.NewsRequest
import com.newsapi.model.response.NewsResponse
import retrofit2.Response

class ServiceRepositoryImpl(private val serviceInterface: ServiceInterface) : ServiceRepository {
    override suspend fun getNews(query: NewsRequest): Response<NewsResponse> {
        return serviceInterface.getNews(
            query.apiKey, query.q, query.sortBy, query.page, query.pageSize, query.from, query.to
        )
    }

}