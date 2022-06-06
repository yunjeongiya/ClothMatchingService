package com.lucyseven.clothmatchingservice.weather.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class WeatherData(
    val temperature: CurrentWeather, // 오늘온도
    val city: String = "noCity", // 현재 도시
    val todayForecast: ArrayList<TodayForecast>, // 오늘 3시간 간격 예보 - 앞으로 8개 (24시간)
    val weekTemperature: WeekTemperature // 일주일 최고, 최저기온
) : Parcelable

@Parcelize
data class CurrentWeather(
    val currentTemp: Int, // 현재온도
    val minTemp: Int, // 오늘 온도중 최저기온
    val maxTemp: Int, // 오늘 온도중 최고기온
    val currentWeather: String, // 오늘 날씨 -- 맑음, 흐림, 등등 한글로 저장
    val currentWeatherIconUrl: String // 오늘 날씨에 대한 아이콘 url
) : Parcelable

@Parcelize
data class TodayForecast(
    var time: LocalDateTime, // 3시간 단위의 예보 시간 0600 0900 1200 1500 1800 2100 0000 0300 식으로 변경
    // 현재시간 과 가장 가까운 시간부터 이후 24시간 까지의 시간
    // ex. 현재시간이 21:15 이면 리스트에 21:00, 24:00 03:00 06:00 ... 21:00 이렇게 24시간의 정보가 담겨있다.
    var temp: Int, // 온도
    var weather: String, // 3시간 시간단위의 날씨 -- 맑음, 흐림 등등 한글로 저장되어 있다.
    var weatherIconUrl: String // 해당 날씨에 대한 아이콘 url
) : Parcelable

@Parcelize
data class WeekTemperature(
    val minTemp: Int, // 오늘 이후 일주일동안의 최저기온
    val maxTemp: Int, // 오늘 이후 일주일동안의 최고기온
) : Parcelable
