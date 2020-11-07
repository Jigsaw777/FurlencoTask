package com.example.furlencotask.ui.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.furlencotask.data.constants.AppConstants
import com.example.furlencotask.domain.entities.RequestType
import com.example.furlencotask.domain.entities.dbEntities.DBNewsEntity
import com.example.furlencotask.domain.requests.FetchNewsRequest
import com.example.furlencotask.domain.usecases.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Sourik on 5/11/20.
 */

class GenericViewModel @ViewModelInject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val insertNewsUseCase: InsertNewsUseCase,
    private val getNewsFromLocalUseCase: GetNewsFromLocalUseCase,
    private val updateFavouriteValueUseCase: UpdateFavouriteValueUseCase,
    private val getRowCountUseCase: GetRowCountUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val resultsLD = MutableLiveData<List<DBNewsEntity>>()
    private val isListEmpty = MutableLiveData<Boolean>()
    private val successLD = MutableLiveData<String>()
    private val errorLD = MutableLiveData<String>()
    private val changeFavLD = MutableLiveData<Pair<String, Boolean>>()
    private val settingRowCountLD = MutableLiveData<Boolean>()

    var pageNumber = 1;
    var canFetch: Boolean = false

    private var itemsDownloadedAlready = 0

    val newsResultsLD: LiveData<List<DBNewsEntity>>
        get() = resultsLD

    val listEmptyLD: LiveData<Boolean>
        get() = isListEmpty

    val successMsg: LiveData<String>
        get() = successLD

    val errorMsg: LiveData<String>
        get() = errorLD

    val changeFavouriteLD: LiveData<Pair<String, Boolean>>
        get() = changeFavLD

    val rowCountSetLD: LiveData<Boolean>
        get() = settingRowCountLD

    fun getNews(position: Int) {
        if(canFetch) {
            if ((pageNumber * 10) > itemsDownloadedAlready) {
                val request =
                    FetchNewsRequest(category = RequestType.values()[position], pageNumber)
                getNewsUseCase.getNews(request)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        val dbNewsList = it.articles.map { newsEntity ->
                            DBNewsEntity(
                                author = newsEntity.author,
                                title = newsEntity.title,
                                description = newsEntity.description,
                                newsUrl = newsEntity.newsUrl,
                                imageUrl = newsEntity.imageUrl,
                                publishDateInMillis = newsEntity.publishDate.let { date ->
                                    if (date.isNullOrEmpty())
                                        0L
                                    else
                                        SimpleDateFormat(
                                            "yyyy-MM-dd'T'HH:mm:ss'Z'",
                                            Locale.getDefault()
                                        ).parse(date).time
                                },
                                content = newsEntity.content,
                                type = RequestType.values()[position].requestString,
                                isFavourite = false
                            )
                        }
                        insertNewsUseCase.insertNews(dbNewsList)
                            .subscribeOn(Schedulers.io())
                            .subscribe({
                                fetchNewsFromLocal(position)
                            }, { error ->
                                error.printStackTrace()
                            })
                    }, {
                        Log.d("VM", "got error")
                        it.printStackTrace()
                    }).let {
                        compositeDisposable.add(it)
                    }
            } else {
                fetchNewsFromLocal(position)
            }
        }
        else
            errorLD.postValue(AppConstants.PAGINATION_NETWORK_ERROR)
    }

    fun fetchNewsFromLocal(position: Int) {
        getNewsFromLocalUseCase.getNewsFromLocal(
            RequestType.values()[position],
            AppConstants.SIZE_OF_PAGE,
            (pageNumber - 1) * AppConstants.SIZE_OF_PAGE
        )
            .subscribeOn(Schedulers.io()).subscribe({
                resultsLD.postValue(it)
            }, {
                it.printStackTrace()
            }).let {
                compositeDisposable.add(it)
            }
    }

    fun isTableEmpty(position: Int) {
        getNewsFromLocalUseCase.getNewsFromLocal(RequestType.values()[position], 10, pageNumber - 1)
            .subscribeOn(Schedulers.io()).subscribe({
                isListEmpty.postValue(it.isEmpty())
            }, { it.printStackTrace() }).let { compositeDisposable.add(it) }
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

    fun setRowCount(position: Int) {
        getRowCountUseCase.getRowCount(RequestType.values()[position]).subscribeOn(Schedulers.io()).subscribe({
            itemsDownloadedAlready = it
            settingRowCountLD.postValue(true)
        }, {
            it.printStackTrace()
            settingRowCountLD.postValue(false)
        }).let { compositeDisposable.add(it) }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}