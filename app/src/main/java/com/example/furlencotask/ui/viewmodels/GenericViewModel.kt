package com.example.furlencotask.ui.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.furlencotask.domain.entities.RequestType
import com.example.furlencotask.domain.entities.dbEntities.NewsModel
import com.example.furlencotask.domain.entities.networkEntities.NewsEntity
import com.example.furlencotask.domain.requests.FetchNewsRequest
import com.example.furlencotask.domain.usecases.GetNewsUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Sourik on 5/11/20.
 */

class GenericViewModel @ViewModelInject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val resultsLD = MutableLiveData<PagingData<NewsModel.DBNewsEntity>>()

    val newsResultsLD: LiveData<PagingData<NewsModel.DBNewsEntity>>
        get() = resultsLD

    fun getNews(position:Int){
        val request = FetchNewsRequest(category = RequestType.values()[position], pageNumber = 1)
        getNewsUseCase.getNews(request)
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("VM","got data : $it")
                resultsLD.postValue(it)
            },{
                Log.d("VM","got error")
                it.printStackTrace()
            }).let {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}