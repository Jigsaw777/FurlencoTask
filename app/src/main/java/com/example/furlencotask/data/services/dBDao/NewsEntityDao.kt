package com.example.furlencotask.data.services.dBDao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.furlencotask.data.constants.DBConstants
import com.example.furlencotask.domain.entities.dbEntities.DBNewsEntity
import io.reactivex.Completable
import io.reactivex.Single


/**
 * Created by Sourik on 5/11/20.
 */

@Dao
interface NewsEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<DBNewsEntity>)

    @Query(DBConstants.GET_STORED_NEWS)
    fun getNews(type: String): Single<List<DBNewsEntity>>

    @Query(DBConstants.GET_FAVOURITE_NEWS)
    fun getFavouriteNews(type: String): Single<List<DBNewsEntity>>

    @Query(DBConstants.UPDATE_FAVOURITE)
    fun updateFavourite(newsUrl: String, isFavourite: Boolean): Completable

    @Query(DBConstants.DELETE_NEWS)
    fun clearTable(): Completable
}