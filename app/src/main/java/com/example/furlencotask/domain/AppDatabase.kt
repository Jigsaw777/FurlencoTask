package com.example.furlencotask.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.furlencotask.data.constants.DBConstants
import com.example.furlencotask.data.services.localDBRequests.NewsEntityDao
import com.example.furlencotask.data.services.localDBRequests.NewsKeyDao
import com.example.furlencotask.domain.entities.dbEntities.NewsModel

/**
 * Created by Sourik on 5/11/20.
 */

@Database(entities = [NewsModel.DBNewsEntity::class, NewsModel.NewsRemoteKeys::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsEntityDao(): NewsEntityDao
    abstract fun newsKeyDao(): NewsKeyDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, DBConstants.APP_DB
        )
            .build()
    }
}