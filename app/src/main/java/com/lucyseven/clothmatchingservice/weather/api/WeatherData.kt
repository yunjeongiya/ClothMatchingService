package com.lucyseven.clothmatchingservice.weather.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class WeatherData(
    val temperature: CurrentWeather,
    val city: String = "noCity",
    val todayForecast: List<TodayForecast>,
) : Parcelable

@Parcelize
data class CurrentWeather(
    val currentTemp: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val currentWeather: String,
    val currentWeatherIconUrl: String
) : Parcelable

@Parcelize
data class TodayForecast(
    var time: LocalDateTime, // 3시간 단위의 예보 시간
    var temp: Int, // 온도
    var weather: String, // 날씨 : 구름인지 맑은지 -> string 말고 enum에 담기, 몇가지로 할지 회의 때 정하기
    var weatherIconUrl: String
) : Parcelable