package com.lucyseven.clothmatchingservice.weather.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class WeatherData (
    val temperature: TodayTemperature,
    val city: String = "noCity",
    val todayForecast: List<TodayForecast>,
) : Parcelable

@Parcelize
data class TodayTemperature(val current: Double, val min: Double, val max: Double) : Parcelable

@Parcelize
data class TodayForecast(
    var time: LocalDateTime, // 3시간 단위의 예보 시간
    var weather: String, // 날씨 : 구름인지 맑은지 -> string 말고 enum에 담기, 몇가지로 할지 회의 때 정하기
    var temp: Int // 온도
) : Parcelable