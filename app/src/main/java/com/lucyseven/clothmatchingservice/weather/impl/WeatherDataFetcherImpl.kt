package com.lucyseven.clothmatchingservice.weather.impl

import com.lucyseven.clothmatchingservice.TodayForecast
import com.lucyseven.clothmatchingservice.weather.api.*
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherDataFetcherImpl : WeatherDataFetcher {

    private val dateFormat= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    override fun fetch(loc: Location): WeatherData {
        val temperatureDoc = temperatureDoc(loc)
        return WeatherData(
            temperature = parseTempInfo(temperatureDoc),
            city = parseCity(temperatureDoc),
            todayForecast = parseTodayForecasts(forecastDoc(loc))
        )
    }

    private fun forecastDoc(loc: Location) =
        JSONObject(Jsoup.connect(forecastUrl(loc)).ignoreContentType(true).get().text())

    private fun temperatureDoc(loc: Location) =
        JSONObject(Jsoup.connect(temperatureUrl(loc)).ignoreContentType(true).get().text())

    private fun temperatureUrl(loc: Location)=
        "https://api.openweathermap.org/data/2.5/weather?lat=${loc.latitude}&lon=${loc.longitude}&appid=b2110b957ed8967488040544ad665408"

    private fun forecastUrl(loc: Location)=
        "https://api.openweathermap.org/data/2.5/forecast?lat=${loc.latitude}&lon=${loc.longitude}&appid=b2110b957ed8967488040544ad665408"

    private fun parseCity(temperatureDoc: JSONObject) =
        temperatureDoc.getString("name")

    private fun parseTempInfo(temperatureDoc: JSONObject) =
        temperatureDoc.getJSONObject("main").run {
            TodayTemperature(
                getDouble("temp") - 273.15f,
                getDouble("temp_max") - 273.15f,
                getDouble("temp_min") - 273.15f,
            )
        }

    private fun parseTodayForecasts(forecastDoc: JSONObject): List<TodayForecast> {
        val forecasts = forecastDoc.getJSONArray("list")
        return (0 until 8).map { parseTodayForecast(forecasts.getJSONObject(it)) }
    }

    private fun parseTodayForecast(content: JSONObject): TodayForecast =
        with(content) {
            val time = LocalDateTime.parse(getString("dt_txt"), dateFormat)
            val temp = getJSONObject("main").getDouble("temp")
            val weather = getJSONArray("weather").getJSONObject(0).getString("main")
            return TodayForecast(time, weather, temp.toInt())
        }

}