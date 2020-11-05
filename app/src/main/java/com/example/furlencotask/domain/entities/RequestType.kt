package com.example.furlencotask.domain.entities

/**
 * Created by Sourik on 5/11/20.
 */
enum class RequestType(val requestString: String) {
    TECH("technology"),
    BUSINESS("business"),
    GENERAL("general"),
    SCIENCE("science"),
    HEALTH("health"),
    SPORTS("sports"),
    ENTERTAINMENT("entertainment")
}