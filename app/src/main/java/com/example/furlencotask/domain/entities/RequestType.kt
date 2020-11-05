package com.example.furlencotask.domain.entities

/**
 * Created by Sourik on 5/11/20.
 */
enum class RequestType(val requestString: String) {
    BUSINESS("business"),
    GENERAL("general"),
    SCIENCE("science"),
    TECH("technology"),
    HEALTH("health"),
    SPORTS("sports"),
    ENTERTAINMENT("entertainment")
}