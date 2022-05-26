package com.lucyseven.clothmatchingservice

data class WeatherData(
    var curTemp: Int, // 현재온도
    var maxTemp: Int, // 오늘 최고온도
    var minTemp: Int, // 오늘 최저온도
    var city: String, // 현재 위치 (구 단위)
    var todayForecast: ArrayList<TodayForecast>, // 앞으로 5일간의 온도를 3시간 간격으로 나타낸것
)
