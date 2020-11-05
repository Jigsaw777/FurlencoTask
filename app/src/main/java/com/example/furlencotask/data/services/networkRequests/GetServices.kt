package com.example.furlencotask.data.services.networkRequests

import com.example.furlencotask.data.constants.AppConstants
import com.example.furlencotask.domain.entities.networkEntities.ResponseEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by Sourik on 5/11/20.
 */

interface GetServices {
    @GET(AppConstants.GET_NEWS_ENDPOINT)
    fun getNews(@QueryMap data: Map<String, String>):Single<ResponseEntity>
}