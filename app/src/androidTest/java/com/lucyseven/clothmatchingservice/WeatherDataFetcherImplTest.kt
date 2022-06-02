package com.lucyseven.clothmatchingservice

import com.lucyseven.clothmatchingservice.weather.impl.WeatherDataFetcherImpl
import com.lucyseven.clothmatchingservice.weather.api.Location
import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime

class WeatherDataFetcherImplTest {

    private val loc = Location(longitude = 127.0, latitude = 37.5)
    private val fetcher = WeatherDataFetcherImpl()

    @Test
    fun withSeoulLocation() {
        val data = fetcher.fetch(loc)
        println(data)
        Assert.assertEquals("Banpobondong", data.city)
        Assert.assertEquals(8, data.todayForecast.size)
        data.todayForecast.forEach {
            Assert.assertTrue(LocalDateTime.now() <= it.time)
            Assert.assertTrue(LocalDateTime.now().plusDays(1) > it.time)
        }
    }
}