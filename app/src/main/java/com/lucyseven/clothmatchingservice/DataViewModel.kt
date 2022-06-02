package com.lucyseven.clothmatchingservice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucyseven.clothmatchingservice.weather.api.WeatherData

class DataViewModel : ViewModel() {
    val weatherDataLive = MutableLiveData<WeatherData>()

    fun setWeatherData(weatherData: WeatherData) {
        weatherDataLive.value = weatherData
    }
}