package com.example.furlencotask.domain.usecases

import com.example.furlencotask.domain.Repository
import com.example.furlencotask.domain.entities.dbEntities.DBNewsEntity
import javax.inject.Inject

/**
 * Created by Sourik on 6/11/20.
 */

class InsertNewsUseCase @Inject constructor(private val repository: Repository){
    fun insertNews(list: List<DBNewsEntity>){
        return repository.insertNews(list)
    }
}