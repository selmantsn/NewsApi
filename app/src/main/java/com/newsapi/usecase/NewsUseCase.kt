package com.newsapi.usecase

import com.newsapi.model.request.NewsRequest
import com.newsapi.model.response.NewsResponse
import com.newsapi.repository.ServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class NewsUseCase @Inject constructor(
    private val repository: ServiceRepository
) {

    suspend fun getNews(requestQuery: NewsRequest): Response<NewsResponse> {
        return withContext(Dispatchers.IO) {
            repository.getNews(requestQuery)
        }
    }

}