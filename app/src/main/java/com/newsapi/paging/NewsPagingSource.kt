package com.newsapi.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.newsapi.BuildConfig
import com.newsapi.PAGE_SIZE
import com.newsapi.Q
import com.newsapi.SORT_BY
import com.newsapi.api.Articles
import com.newsapi.api.ServiceInterface
import com.newsapi.db.ArticlesDao
import retrofit2.HttpException
import java.io.IOException

class NewsPagingSource(
    private val serviceInterface: ServiceInterface,
    private val articlesDao: ArticlesDao,
    private val from: String,
    private val to: String
) : PagingSource<Int, Articles>() {

    companion object {
        private const val PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Articles>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Articles> {
        return try {
            val pageIndex = params.key ?: PAGE_INDEX
            val response = serviceInterface.getNews(
                apiKey = BuildConfig.API_KEY,
                q = Q,
                sortBy = SORT_BY,
                page = pageIndex,
                pageSize = PAGE_SIZE,
                from = from,
                to = to
            )

            response.body()?.articles?.let {
                articlesDao.insertAll(it)
            }

            if (response.isSuccessful) {
                LoadResult.Page(
                    data = response.body()?.articles ?: emptyList(),
                    prevKey = if (pageIndex == PAGE_INDEX) null else pageIndex.minus(1),
                    nextKey = if (response.body()?.articles?.isEmpty() == true) null else pageIndex.plus(
                        1
                    )
                )
            } else {
                val errorBody = response.errorBody()?.string()

                LoadResult.Error(Exception(errorBody))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    }

}