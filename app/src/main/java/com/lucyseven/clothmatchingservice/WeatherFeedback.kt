package com.lucyseven.clothmatchingservice

data class WeatherFeedback(
    var date: String,
    var time : String,
    var loc: String,
    var curTemp: Int,
    var maxTemp : Int,
    var minTemp : Int,
    var cloth: ArrayList<String>,
    var feedbackScore : Int,
    var feedback: String,
    var weatherIcon : String
) {
}