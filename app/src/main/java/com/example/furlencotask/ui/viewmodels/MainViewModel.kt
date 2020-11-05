package com.example.furlencotask.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.furlencotask.domain.entities.NewsEntity
import com.example.furlencotask.domain.requests.FetchNewsRequest
import com.example.furlencotask.domain.usecases.GetNewsUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Created by Sourik on 5/11/20.
 */

class MainViewModel @ViewModelInject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val newsResultLD = MutableLiveData<List<NewsEntity>>()

    val resultLD: LiveData<List<NewsEntity>>
        get() = newsResultLD

    fun getSearchResults() {
        val request = FetchNewsRequest(category = "Technology", pageNumber = 1)
        getNewsUseCase.getNews(request)
            .subscribeOn(Schedulers.io())
            .subscribe({
                newsResultLD.postValue(it.articles)
            }, {
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