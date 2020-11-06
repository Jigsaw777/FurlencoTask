package com.example.furlencotask.domain

import com.example.furlencotask.domain.entities.RequestType
import com.example.furlencotask.domain.entities.dbEntities.DBNewsEntity
import com.example.furlencotask.domain.entities.networkEntities.ResponseEntity
import com.example.furlencotask.domain.requests.FetchNewsRequest
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by Sourik on 5/11/20.
 */

interface Repository {
    fun getNewsFromLocal(newsRequest: FetchNewsRequest): Single<ResponseEntity>
    fun insertNews(list: List<DBNewsEntity>)
    fun getNewsFromLocal(type: RequestType): Single<List<DBNewsEntity>>
    fun getFavouriteNews(type: RequestType):Single<List<DBNewsEntity>>
    fun updateFavouriteValue(newsUrl:String, isFavourite:Boolean): Completable
}