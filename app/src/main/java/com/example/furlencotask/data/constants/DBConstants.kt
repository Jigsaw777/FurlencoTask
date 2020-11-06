package com.example.furlencotask.data.constants

/**
 * Created by Sourik on 5/11/20.
 */

object DBConstants {
    const val APP_DB = "app.db"
    const val NEWS_TABLE = "NewsTable"

    const val ID = "id"
    const val AUTHOR = "author_name"
    const val TITLE = "title"
    const val DESCRIPTION = "description_of_news"
    const val NEWS_URL = "news_url"
    const val IMAGE_URL = "image_url"
    const val PUBLISH_DATE = "publish_date"
    const val CONTENT = "content"
    const val TYPE = "request_type"
    const val IS_FAVOURITE = "is_favourite"

    const val GET_STORED_NEWS =
        "SELECT * FROM $NEWS_TABLE WHERE $TYPE=:type ORDER BY $PUBLISH_DATE DESC"

    const val GET_FAVOURITE_NEWS = "Select * FROM $NEWS_TABLE"

    const val DELETE_NEWS = "DELETE FROM $NEWS_TABLE"
}