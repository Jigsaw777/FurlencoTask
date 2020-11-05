package com.example.furlencotask.data.services.localDBRequests

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.furlencotask.data.constants.DBConstants
import com.example.furlencotask.domain.entities.dbEntities.NewsModel

/**
 * Created by Sourik on 6/11/20.
 */

@Dao
interface NewsKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<NewsModel.NewsRemoteKeys>)

    @Query("SELECT * FROM ${DBConstants.NEWS_REMOTE_KEY_TABLE} WHERE newsId = :newsId")
    fun remoteKeysById(newsId: Long): NewsModel.NewsRemoteKeys?

    @Query("DELETE FROM ${DBConstants.NEWS_REMOTE_KEY_TABLE}")
    fun clearRemoteKeys()

}