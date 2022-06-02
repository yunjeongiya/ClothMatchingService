package com.lucyseven.clothmatchingservice.weather.impl

import com.google.api.AnnotationsProto.http
import com.lucyseven.clothmatchingservice.weather.api.*
import org.json.JSONObject
import org.jsoup.Jsoup
import java.lang.reflect.Array.getDouble
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherDataFetcherImpl : WeatherDataFetcher {

    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    override fun fetch(loc: Location): WeatherData {
//        val temperatureDoc = temperatureDoc(loc)
        return WeatherData(
            temperature = parseTempInfo(temperatureDoc(loc)),
            city = parseCity(cityDoc(loc)),
            todayForecast = parseTodayForecasts(forecastDoc(loc))
        )
    }

    private fun forecastDoc(loc: Location) =
        JSONObject(Jsoup.connect(forecastUrl(loc)).ignoreContentType(true).get().text())

    private fun temperatureDoc(loc: Location) =
        JSONObject(Jsoup.connect(temperatureUrl(loc)).ignoreContentType(true).get().text())

    private fun cityDoc(loc: Location) =
        JSONObject(Jsoup.connect(findCityUrl(loc)).ignoreContentType(true).get().text())

    private fun temperatureUrl(loc: Location) =
        "https://api.openweathermap.org/data/2.5/weather?lat=${loc.latitude}&lon=${loc.longitude}&appid=b2110b957ed8967488040544ad665408"

    private fun forecastUrl(loc: Location) =
        "https://api.openweathermap.org/data/2.5/forecast?lat=${loc.latitude}&lon=${loc.longitude}&appid=b2110b957ed8967488040544ad665408"

    private fun findCityUrl(loc: Location) =
        "http://apis.vworld.kr/coord2jibun.do?x=${loc.longitude}&y=${loc.latitude}&output=json&epsg=epsg:4326&apiKey=A3A3152A-C67F-3485-A5C0-F6E666CC952E"

    private fun parseCity(content: JSONObject): String {
        val ret = content.getString("ADDR")
        val token = ret.split(' ')
        return "${token[0]} ${token[1]}"
    }

    private fun parseTempInfo(temperatureDoc: JSONObject): CurrentWeather =
        with(temperatureDoc) {
            var currentTemp: Int
            var todayMaxTemp: Int
            var todayMinTemp: Int
            var currentWeather: String
            var currentWeatherIconUrl: String

            temperatureDoc.getJSONObject("main").apply {
                currentTemp = (getDouble("temp") - 273.15f).toInt()
                todayMaxTemp = (getDouble("temp_max") - 273.15f).toInt()
                todayMinTemp = (getDouble("temp_min") - 273.15f).toInt()
            }

            temperatureDoc.getJSONArray("weather").getJSONObject(0).apply {
                currentWeather = when (getInt("id")) {
                    in 200..232 -> "천둥 / 번개"
                    in 300..321 -> "이슬비"
                    in 500..531 -> "비"
                    in 600..622 -> "눈"
                    in 701..781 ->
                        when (getString("main")) {
                            "Dust", "Sand" -> "황사"
                            "Ash" -> "화산재"
                            "Squall" -> "낙뢰 / 호우"
                            else -> "안개"
                        }
                    800 -> "맑음"
                    in 801..804 -> "흐림"
                    else -> "정보없음"
                }

                currentWeatherIconUrl =
                    "http://openweathermap.org/img/wn/" + getString("icon") + "@2x.png"
            }

            return CurrentWeather(
                currentTemp,
                todayMaxTemp,
                todayMinTemp,
                currentWeather,
                currentWeatherIconUrl
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

            val weatherObject = getJSONArray("weather").getJSONObject(0)
            val weather = when (weatherObject.getInt("id")) {
                in 200..232 -> "천둥 / 번개"
                in 300..321 -> "이슬비"
                in 500..531 -> "비"
                in 600..622 -> "눈"
                in 701..781 ->
                    when (weatherObject.getString("main")) {
                        "Dust", "Sand" -> "황사"
                        "Ash" -> "화산재"
                        "Squall" -> "낙뢰 / 호우"
                        else -> "안개"
                    }
                800 -> "맑음"
                in 801..804 -> "흐림"
                else -> "정보없음"
            }

            val weatherIconUrl =
                        "http://openweathermap.org/img/wn/" +
                        weatherObject.getString("icon") +
                        "@2x.png"


            return TodayForecast(time, temp.toInt(), weather, weatherIconUrl)
        }

}