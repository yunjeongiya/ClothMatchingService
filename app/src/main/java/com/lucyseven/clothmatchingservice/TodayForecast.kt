package com.lucyseven.clothmatchingservice

data class TodayForecast(
    var time: String, // 3시간 단위의 예보 시간
    var weather: String, // 날시 : 구름인지 맑은지
    var temp: Int // 온도
)
