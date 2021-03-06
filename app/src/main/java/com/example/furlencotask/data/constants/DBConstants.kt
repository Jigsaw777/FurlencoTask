package com.example.furlencotask.data.constants

/**
 * Created by Sourik on 5/11/20.
 */

object DBConstants {
    const val APP_DB = "app.db"
    const val NEWS_TABLE = "NewsTable"

    const val AUTHOR = "author_name"
    const val TITLE = "title"
    const val DESCRIPTION = "description_of_news"
    const val NEWS_URL = "news_url"
    const val IMAGE_URL = "image_url"
    const val PUBLISH_DATE = "publish_date"
    const val CONTENT = "content"
    const val TYPE = "request_type"
    const val IS_FAVOURITE = "is_favourite"

    // gets stored news from DB
    const val GET_STORED_NEWS =
        "SELECT * FROM $NEWS_TABLE WHERE $TYPE=:type ORDER BY $PUBLISH_DATE DESC LIMIT :limit OFFSET :offset "

    // gets only favourite news from db
    const val GET_FAVOURITE_NEWS =
        "Select * FROM $NEWS_TABLE WHERE $TYPE=:type AND $IS_FAVOURITE=1 ORDER BY $PUBLISH_DATE DESC"

    // toggles the favourite value in DB
    const val UPDATE_FAVOURITE =
        "UPDATE $NEWS_TABLE SET $IS_FAVOURITE=:isFavourite WHERE $NEWS_URL = :newsUrl"

    // gets number of items in table for each request type
    const val GET_ROW_COUNT = "SELECT COUNT($NEWS_URL) FROM $NEWS_TABLE WHERE $TYPE = :type"

    // clears the table
    const val DELETE_NEWS = "DELETE FROM $NEWS_TABLE"
}