package com.example.furlencotask.data

import com.example.furlencotask.data.services.networkRequests.GetServices
import com.example.furlencotask.domain.Repository
import com.example.furlencotask.domain.entities.ResponseEntity
import com.example.furlencotask.domain.requests.FetchNewsRequest
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Sourik on 5/11/20.
 */

class RepoImpl @Inject constructor(private val getServices: GetServices) : Repository{

    override fun getNews(newsRequest: FetchNewsRequest): Single<ResponseEntity> {
        return getServices.getNews(newsRequest.getParams())
    }

}