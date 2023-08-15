package com.newsapi.api

import com.newsapi.model.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceInterface {

    @GET("/v2/everything")
    suspend fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("q") q: String,
        @Query("sortBy") sortBy: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("from") from: String,
        @Query("to") to: String
    ): Response<NewsResponse>

}