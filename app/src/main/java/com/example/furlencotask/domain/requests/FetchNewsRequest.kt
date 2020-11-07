package com.example.furlencotask.domain.requests

import com.example.furlencotask.domain.entities.RequestType

/**
 * Created by Sourik on 5/11/20.
 */

class FetchNewsRequest(
    private val category: RequestType = RequestType.TECH,
    private val pageNumber: Int = 1
) {
    fun getParams(): Map<String, String> {
        val params = HashMap<String, String>()
        params["category"] = category.requestString
        params["page"] = pageNumber.toString()
        params["apiKey"] = "3de65ab68dc64ed5a30d3f90a4a7f597"
        params["pageSize"] = "10"
        params["country"] = "in"
        return params;
    }
}

