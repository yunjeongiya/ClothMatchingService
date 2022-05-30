package com.lucyseven.clothmatchingservice.weather.api

import com.lucyseven.clothmatchingservice.TodayForecast

data class WeatherData(
    val temperature: TodayTemperature,
    val city: String,
    val todayForecast: List<TodayForecast>,
)

data class TodayTemperature(val current: Double, val min: Double, val max: Double)