package com.newsapi.model.request

data class NewsRequest(
    var apiKey: String,
    var q: String,
    var sortBy: String,
    var page: Int,
    var pageSize: Int,
    var from: String,
    var to: String
)
