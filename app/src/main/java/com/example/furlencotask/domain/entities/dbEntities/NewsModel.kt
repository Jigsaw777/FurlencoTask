package com.example.furlencotask.domain.entities.dbEntities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.furlencotask.data.constants.DBConstants
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * Created by Sourik on 6/11/20.
 */

@Parcelize
data class NewsModel(
    val total: Int = 0,
    val page: Int = 0,
    val newsEntities: List<DBNewsEntity>
): Parcelable{
    @IgnoredOnParcel
    val endOfPage = total == page


    @Parcelize
    @Entity(tableName = DBConstants.NEWS_TABLE)
    data class DBNewsEntity(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = DBConstants.ID)
        val id: Long = 0,

        @ColumnInfo(name = DBConstants.AUTHOR)
        val author: String?,

        @ColumnInfo(name = DBConstants.TITLE)
        val title: String?,

        @ColumnInfo(name = DBConstants.DESCRIPTION)
        val description: String?,

        @ColumnInfo(name = DBConstants.NEWS_URL)
        val newsUrl: String?,

        @ColumnInfo(name = DBConstants.IMAGE_URL)
        val imageUrl: String?,

        @ColumnInfo(name = DBConstants.PUBLISH_DATE)
        val publishDateInMillis: Long?,

        @ColumnInfo(name = DBConstants.CONTENT)
        val content: String?,

        @ColumnInfo(name = DBConstants.TYPE)
        val type: String,

        @ColumnInfo(name = DBConstants.IS_FAVOURITE)
        val isFavourite: Boolean
    ):Parcelable

    @Parcelize
    @Entity(tableName = DBConstants.NEWS_REMOTE_KEY_TABLE)
    data class NewsRemoteKeys(
        @PrimaryKey val newsId: Long,
        val prevKey: Int?,
        val nextKey: Int?
    ) : Parcelable
}