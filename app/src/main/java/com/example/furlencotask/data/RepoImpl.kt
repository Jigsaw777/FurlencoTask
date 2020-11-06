package com.example.furlencotask.data

import com.example.furlencotask.data.services.networkRequests.GetServices
import com.example.furlencotask.domain.AppDatabase
import com.example.furlencotask.domain.Repository
import com.example.furlencotask.domain.entities.RequestType
import com.example.furlencotask.domain.entities.dbEntities.DBNewsEntity
import com.example.furlencotask.domain.entities.networkEntities.ResponseEntity
import com.example.furlencotask.domain.requests.FetchNewsRequest
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Sourik on 5/11/20.
 */

class RepoImpl
@Inject constructor(
    private val database: AppDatabase,
    private val getRequestsService: GetServices
) : Repository {

    override fun getNewsFromLocal(newsRequest: FetchNewsRequest): Single<ResponseEntity> {
        return getRequestsService.getNews(newsRequest.getParams())
    }

    override fun getNewsFromLocal(type: RequestType): Single<List<DBNewsEntity>> {
        return database.newsEntityDao().getNews(type = type.requestString)
    }

    override fun insertNews(list: List<DBNewsEntity>) {
        database.newsEntityDao().insertAll(list)
    }
}