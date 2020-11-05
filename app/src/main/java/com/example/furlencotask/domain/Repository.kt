package com.example.furlencotask.domain

import androidx.paging.PagingData
import com.example.furlencotask.domain.entities.dbEntities.NewsModel
import com.example.furlencotask.domain.requests.FetchNewsRequest
import io.reactivex.Flowable

/**
 * Created by Sourik on 5/11/20.
 */

interface Repository {
    fun getNews(newsRequest: FetchNewsRequest): Flowable<PagingData<NewsModel.DBNewsEntity>>
}