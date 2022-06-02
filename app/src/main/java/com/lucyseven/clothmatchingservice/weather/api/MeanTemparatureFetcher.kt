package com.lucyseven.clothmatchingservice.weather.api

import java.time.Month

interface TemperatureFetcher {
    fun fetchTemperature(yearAndMonth: YearAndMonth): MonthTemperature
}

data class MonthTemperature(val average: Int, val min: Int, val max: Int)

data class YearAndMonth(val year: Int, val month: Int) {
    init {
        if (year !in 2000..2030)
            error("Year not supported")
        if (month !in 1..12)
            error("Invalid month")
    }
}