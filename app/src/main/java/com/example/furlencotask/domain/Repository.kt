package com.example.furlencotask.domain

import com.example.furlencotask.domain.entities.ResponseEntity
import com.example.furlencotask.domain.requests.FetchNewsRequest
import io.reactivex.rxjava3.core.Single

/**
 * Created by Sourik on 5/11/20.
 */

interface Repository {
    fun getNews(newsRequest: FetchNewsRequest):Single<ResponseEntity>
}