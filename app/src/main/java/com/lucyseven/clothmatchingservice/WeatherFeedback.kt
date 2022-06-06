package com.lucyseven.clothmatchingservice

data class WeatherFeedback(
    val id: Int,
    var date: String,
    var loc: String,
    var temp: Int,
    var cloth: String,
    var feedback: String
)