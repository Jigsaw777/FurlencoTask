package com.example.furlencotask.domain.usecases

import androidx.paging.PagingData
import com.example.furlencotask.domain.Repository
import com.example.furlencotask.domain.entities.dbEntities.NewsModel
import com.example.furlencotask.domain.requests.FetchNewsRequest
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Sourik on 5/11/20.
 */

class GetNewsUseCase @Inject constructor(private val repository: Repository) {
    fun getNews(newsRequest: FetchNewsRequest): Flowable<PagingData<NewsModel.DBNewsEntity>> {
        return repository.getNews(newsRequest)
    }
}