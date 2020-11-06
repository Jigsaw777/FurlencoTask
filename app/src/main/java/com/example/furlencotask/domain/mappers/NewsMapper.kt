package com.example.furlencotask.domain.mappers

import android.util.Log
import com.example.furlencotask.domain.entities.RequestType
import com.example.furlencotask.domain.entities.dbEntities.NewsModel
import com.example.furlencotask.domain.entities.networkEntities.ResponseEntity
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Sourik on 6/11/20.
 */

class NewsMapper @Inject constructor() {
    fun transform(input: ResponseEntity, page: Int, locale: Locale, type: RequestType): NewsModel {
        return with(input) {
            NewsModel(
                total = totalResults,
                page = page,
                newsEntities = articles.map {
                    NewsModel.DBNewsEntity(
                        0,
                        author = it.author,
                        title = it.title,
                        description = it.description,
                        newsUrl = it.newsUrl,
                        imageUrl = it.imageUrl,
                        publishDateInMillis = it.publishDate.let { date ->
                            if (date.isNullOrEmpty())
                                0L
                            else
                                SimpleDateFormat(
                                    "yyyy-MM-dd'T'HH:mm:ss'Z'",
                                    locale
                                ).parse(date).time
                        },
                        content = it.content,
                        type = type.requestString,
                        isFavourite = false
                    )
                }
            )
        }
    }
}