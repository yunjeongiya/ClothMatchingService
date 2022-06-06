package com.lucyseven.clothmatchingservice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucyseven.clothmatchingservice.cloth.api.Cloth
import com.lucyseven.clothmatchingservice.cloth.impl.ClothDataImpl
import com.lucyseven.clothmatchingservice.weather.api.WeatherData

class DataViewModel : ViewModel() {
    val weatherDataLive = MutableLiveData<WeatherData>()
    val clothDataLive = MutableLiveData<List<Cloth>>()

    fun setWeatherData(weatherData: WeatherData) {
        weatherDataLive.value = weatherData
    }

    fun setClothData(clothData: List<Cloth>) {
        clothDataLive.value = clothData
    }
}