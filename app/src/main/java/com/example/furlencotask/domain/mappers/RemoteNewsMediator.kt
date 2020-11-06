package com.example.furlencotask.domain.mappers

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.example.furlencotask.data.services.networkRequests.GetServices
import com.example.furlencotask.domain.AppDatabase
import com.example.furlencotask.domain.entities.RequestType
import com.example.furlencotask.domain.entities.dbEntities.NewsModel
import com.example.furlencotask.domain.requests.FetchNewsRequest
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.InvalidObjectException
import java.util.*
import javax.inject.Inject

/**
 * Created by Sourik on 6/11/20.
 */

@ExperimentalPagingApi
class RemoteNewsMediator @Inject constructor(
    private val service: GetServices,
    private val database: AppDatabase,
    private val mapper: NewsMapper,
    private val request: FetchNewsRequest
) : RxRemoteMediator<Int, NewsModel.DBNewsEntity>() {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, NewsModel.DBNewsEntity>
    ): Single<MediatorResult> {
        Log.d("Mediator", loadType.name)
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                        remoteKeys?.nextKey?.minus(1) ?: 1
                    }

                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")
                        remoteKeys.prevKey ?: -1
                    }

                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?: throw InvalidObjectException("Result is empty")
                        remoteKeys.nextKey ?: -1
                    }
                }
            }
            .flatMap { page ->
                Log.d("Mediator","Page : $page")
                if (page == -1) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    service.getNews(request.getParams())
                        .map {
                            mapper.transform(
                                it,
                                page,
                                Locale.getDefault(),
                                RequestType.values()
                                    .first { element -> element.requestString == request.getParams()["category"] }
                            )
                        }
                        .map { insertToDB(page, loadType, it) }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it.endOfPage) }
                        .onErrorReturn {
                            it.printStackTrace()
                            MediatorResult.Error(it) }
                }
            }
            .onErrorReturn {
                it.printStackTrace()
                MediatorResult.Error(it) }

    }

    @Suppress("DEPRECATION")
    private fun insertToDB(page: Int, loadType: LoadType, data: NewsModel): NewsModel {
        database.beginTransaction()

        try {
            if (loadType == LoadType.REFRESH) {
                database.newsKeyDao().clearRemoteKeys()
                database.newsEntityDao().clearTable()
            }
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (data.endOfPage) null else page + 1
            val keys = data.newsEntities.map {
                NewsModel.NewsRemoteKeys(newsId = it.id, prevKey = prevKey, nextKey = nextKey)
            }
            database.newsKeyDao().insertAll(keys)
            database.newsEntityDao().insertAll(data.newsEntities)
            database.setTransactionSuccessful()
        } finally {
            database.endTransaction()
        }
        return data
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, NewsModel.DBNewsEntity>): NewsModel.NewsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            database.newsKeyDao().remoteKeysById(repo.id)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, NewsModel.DBNewsEntity>): NewsModel.NewsRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { news ->
            database.newsKeyDao().remoteKeysById(news.id)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, NewsModel.DBNewsEntity>): NewsModel.NewsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {
                database.newsKeyDao().remoteKeysById(it)
            }
        }
    }

}