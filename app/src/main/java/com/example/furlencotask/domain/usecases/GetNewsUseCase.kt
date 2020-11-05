package com.example.furlencotask.domain.usecases

import com.example.furlencotask.domain.Repository
import com.example.furlencotask.domain.entities.NewsEntity
import com.example.furlencotask.domain.entities.ResponseEntity
import com.example.furlencotask.domain.requests.FetchNewsRequest
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Sourik on 5/11/20.
 */

class GetNewsUseCase @Inject constructor(private val repository: Repository) {
    fun getNews(newsRequest: FetchNewsRequest): Single<ResponseEntity> {
        return repository.getNews(newsRequest)
    }
}