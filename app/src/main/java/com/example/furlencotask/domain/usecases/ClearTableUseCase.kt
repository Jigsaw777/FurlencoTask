package com.example.furlencotask.domain.usecases

import com.example.furlencotask.domain.Repository
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Created by Sourik on 7/11/20.
 */

class ClearTableUseCase @Inject constructor(private val repository: Repository) {
    fun clearTables(): Completable {
        return repository.clearTable()
    }
}