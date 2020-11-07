package com.example.furlencotask.domain.usecases

import com.example.furlencotask.domain.Repository
import com.example.furlencotask.domain.entities.RequestType
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Sourik on 7/11/20.
 */

class GetRowCountUseCase @Inject constructor(private val repository: Repository){
    fun getRowCount(type: RequestType):Single<Int>{
        return repository.getRowCount(type)
    }
}