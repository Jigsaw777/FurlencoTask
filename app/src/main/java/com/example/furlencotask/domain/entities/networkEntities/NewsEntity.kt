package com.example.furlencotask.domain.entities.networkEntities

import com.google.gson.annotations.SerializedName

/**
 * Created by Sourik on 5/11/20.
 */

data class NewsEntity(
    @SerializedName("source") val sourceEntity: SourceEntity,
    @SerializedName("author") val author: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("url") val newsUrl: String,
    @SerializedName("urlToImage") val imageUrl: String?,
    @SerializedName("publishedAt") val publishDate: String?,
    @SerializedName("content") val content: String?,
)

data class SourceEntity(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?
)