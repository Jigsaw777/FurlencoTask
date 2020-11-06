package com.example.furlencotask.domain.usecases

import com.example.furlencotask.domain.Repository
import com.example.furlencotask.domain.entities.RequestType
import com.example.furlencotask.domain.entities.dbEntities.DBNewsEntity
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Sourik on 7/11/20.
 */

class GetFavouritesUseCase @Inject constructor(private val repository: Repository){
    fun getFavourites(type: RequestType):Single<List<DBNewsEntity>>{
        return repository.getFavouriteNews(type)
    }
}