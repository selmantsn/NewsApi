package com.newsapi.repository

import com.newsapi.model.request.NewsRequest
import com.newsapi.model.response.NewsResponse
import retrofit2.Response

interface ServiceRepository {
    suspend fun getNews(query: NewsRequest): Response<NewsResponse>

}