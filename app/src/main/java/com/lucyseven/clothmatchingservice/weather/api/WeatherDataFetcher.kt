package com.lucyseven.clothmatchingservice.weather.api

interface WeatherDataFetcher {
    fun fetch(loc: Location): WeatherData
}