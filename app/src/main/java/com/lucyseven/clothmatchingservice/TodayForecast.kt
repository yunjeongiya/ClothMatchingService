package com.lucyseven.clothmatchingservice

import java.time.LocalDateTime

data class TodayForecast(
    var time: LocalDateTime, // 3시간 단위의 예보 시간
    var weather: String, // 날씨 : 구름인지 맑은지 -> string 말고 enum에 담기, 몇가지로 할지 회의 때 정하기
    var temp: Int // 온도
)
