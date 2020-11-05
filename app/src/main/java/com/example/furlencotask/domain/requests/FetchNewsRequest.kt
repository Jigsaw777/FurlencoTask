package com.example.furlencotask.domain.requests

/**
 * Created by Sourik on 5/11/20.
 */

class FetchNewsRequest(private val category: String, private val pageNumber: Int = 1) {
    fun getParams(): Map<String, String> {
        val params = HashMap<String, String>()
        params["category"] = category
        params["page"] = pageNumber.toString()
        params["apiKey"] = "58537123cd884d6fa7fdef77906d0886"
        params["pageSize"] = "10"
        params["country"] = "in"
        return params;
    }
}

