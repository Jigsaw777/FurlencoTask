package com.example.furlencotask.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.furlencotask.data.services.networkRequests.GetServices
import com.example.furlencotask.domain.AppDatabase
import com.example.furlencotask.domain.Repository
import com.example.furlencotask.domain.entities.dbEntities.NewsModel
import com.example.furlencotask.domain.mappers.NewsMapper
import com.example.furlencotask.domain.mappers.RemoteNewsMediator
import com.example.furlencotask.domain.requests.FetchNewsRequest
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Sourik on 5/11/20.
 */

class RepoImpl
@Inject constructor(
    private val database: AppDatabase,
    private val getRequestsService: GetServices,
    private val mapper: NewsMapper
) : Repository {

    @ExperimentalPagingApi
    override fun getNews(newsRequest: FetchNewsRequest): Flowable<PagingData<NewsModel.DBNewsEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                prefetchDistance = 10,
                initialLoadSize = 10
            ),
            remoteMediator = RemoteNewsMediator(getRequestsService, database, mapper, newsRequest),
            pagingSourceFactory = { database.newsEntityDao().getNews() }
        ).flowable
    }


}