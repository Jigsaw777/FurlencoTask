package com.example.furlencotask.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.furlencotask.domain.usecases.ClearTableUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Sourik on 7/11/20.
 */

class MainViewModel @ViewModelInject constructor(
    private val clearTableUseCase: ClearTableUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val onFavouriteChangedLD = MutableLiveData<Pair<String, Boolean>>()
    private val onFinishedClearLD = MutableLiveData<Boolean>()

    val onChangedFav: LiveData<Pair<String, Boolean>>
        get() = onFavouriteChangedLD

    val finishedClearingDB: LiveData<Boolean>
        get() = onFinishedClearLD

    fun onChangeFav(pair: Pair<String, Boolean>) {
        onFavouriteChangedLD.postValue(pair)
    }

    val isNetworkAvailable = MutableLiveData<Boolean>()

    fun clearAllData() {
        clearTableUseCase.clearTables().subscribeOn(Schedulers.io()).subscribe({
            onFinishedClearLD.postValue(true)
        }, {
            it.printStackTrace()
            onFinishedClearLD.postValue(false)
        })
            .let { compositeDisposable.add(it) }
    }
}