package com.example.furlencotask.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.furlencotask.data.constants.AppConstants
import com.example.furlencotask.domain.entities.RequestType
import com.example.furlencotask.domain.entities.dbEntities.DBNewsEntity
import com.example.furlencotask.domain.usecases.GetFavouritesUseCase
import com.example.furlencotask.domain.usecases.UpdateFavouriteValueUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Sourik on 7/11/20.
 */

class GenericFavsViewModel @ViewModelInject constructor(
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val updateFavouriteValueUseCase: UpdateFavouriteValueUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val resultsLD = MutableLiveData<List<DBNewsEntity>>()
    private val successLD = MutableLiveData<String>()
    private val errorLD = MutableLiveData<String>()
    private val changeFavLD = MutableLiveData<Pair<String, Boolean>>()

    val newsResultsLD: LiveData<List<DBNewsEntity>>
        get() = resultsLD

    val successMsg: LiveData<String>
        get() = successLD

    val errorMsg: LiveData<String>
        get() = errorLD

    val changeFavouriteLD: LiveData<Pair<String, Boolean>>
        get() = changeFavLD

    fun getFavs(position: Int) {
        getFavouritesUseCase.getFavourites(RequestType.values()[position])
            .subscribeOn(Schedulers.io()).subscribe({
                resultsLD.postValue(it)
            }, {
                errorLD.postValue(it.message)
                it.printStackTrace()
            }).let { compositeDisposable.add(it) }
    }

    fun toggleFavouriteValue(entity: DBNewsEntity) {
        val isFavourite = !entity.isFavourite
        updateFavouriteValueUseCase.updateValue(entity.newsUrl, isFavourite)
            .subscribeOn(Schedulers.io()).subscribe({
                successLD.postValue(if (isFavourite) AppConstants.ADDED_FAVOURITES else AppConstants.REMOVED_FAVOURITES)
                changeFavLD.postValue(entity.newsUrl to isFavourite)
            }, { it.printStackTrace() })
            .let { compositeDisposable.add(it) }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}