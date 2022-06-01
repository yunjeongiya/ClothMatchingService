package com.lucyseven.clothmatchingservice

data class WeatherFeedback(
    val id: Int = 1,
    var date: String,
    var loc: String,
    var temp: Float,
    var cloth: String,
    var feedback: String
) {
}