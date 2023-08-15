package com.newsapi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.newsapi.db.ArticlesDao
import com.newsapi.model.response.Articles
import com.newsapi.paging.NewsPagingSource
import com.newsapi.usecase.NewsUseCase
import com.newsapi.utils.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val articlesDao: ArticlesDao, private val newsUseCase: NewsUseCase
) : ViewModel() {

    fun getNews(from: String, to: String): Flow<PagingData<Articles>> = Pager(config = PagingConfig(
        pageSize = PAGE_SIZE, enablePlaceholders = false
    ), pagingSourceFactory = {
        NewsPagingSource(articlesDao, newsUseCase, from, to)
    }).flow

    fun updateDateAndFetchData(from: String, to: String): Flow<PagingData<Articles>> {
        viewModelScope.launch {
            articlesDao.clearArticles()
        }
        return getNews(from, to)
    }


}