package com.example.furlencotask.data.services.localDBRequests

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.furlencotask.data.constants.DBConstants
import com.example.furlencotask.domain.entities.dbEntities.NewsModel


/**
 * Created by Sourik on 5/11/20.
 */

@Dao
interface NewsEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news:List<NewsModel.DBNewsEntity>)

    @Query(DBConstants.GET_STORED_NEWS)
    fun getNews(): PagingSource <Int, NewsModel.DBNewsEntity>

    @Query(DBConstants.DELETE_NEWS)
    fun clearTable()

    //requestType: String, page: Int = 1
//    @Query(DBConstants.GET_FAVOURITE_NEWS)
//    fun getFavouriteNews(): Single<List<NewsModel.DBNewsEntity>>
}