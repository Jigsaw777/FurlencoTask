package com.example.furlencotask.domain.entities.networkEntities

import com.example.furlencotask.domain.entities.networkEntities.NewsEntity
import com.google.gson.annotations.SerializedName

/**
 * Created by Sourik on 5/11/20.
 */

data class ResponseEntity(
    @SerializedName("status") val status:String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<NewsEntity>
)