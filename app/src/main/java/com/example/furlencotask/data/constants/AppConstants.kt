package com.example.furlencotask.data.constants

/**
 * Created by Sourik on 5/11/20.
 */

object AppConstants {
    const val BASE_URL = "https://newsapi.org/"
    const val GET_NEWS_ENDPOINT = "v2/top-headlines/"

    const val VM_ERROR_MSG = "Something went wrong. Please try again."
    const val NO_NETWORK_ERROR = "No internet connection. Please try again later."
    const val REQUEST_TYPE_POSITION = "position"
    const val NEWS_URL = "news_url"

    const val ADDED_FAVOURITES = "Added your favourite"
    const val REMOVED_FAVOURITES = "Removed your favourite"

    const val ALERT_TITLE = "ALERT!!!"
    const val ALERT_MESSAGE =
        "This will clear all current data in the app and refresh the app with new updated data. Are you sure you want to proceed with this ?"
    const val POSITIVE = "Yes, I'm sure"
    const val NEGATIVE = "I'll do this later"
}