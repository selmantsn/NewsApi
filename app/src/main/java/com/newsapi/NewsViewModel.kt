package com.newsapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.newsapi.api.Articles
import com.newsapi.api.ServiceInterface
import com.newsapi.db.ArticlesDao
import com.newsapi.paging.NewsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val serviceInterface: ServiceInterface, private val articlesDao: ArticlesDao
) : ViewModel() {

    fun getNews(from: String, to: String): Flow<PagingData<Articles>> = Pager(config = PagingConfig(
        pageSize = PAGE_SIZE, enablePlaceholders = false
    ), pagingSourceFactory = {
        NewsPagingSource(serviceInterface, articlesDao, from, to)
    }).flow

    fun updateDateAndFetchData(from: String, to: String): Flow<PagingData<Articles>> {
        viewModelScope.launch {
            articlesDao.clearArticles()
        }
        return getNews(from, to)
    }


}