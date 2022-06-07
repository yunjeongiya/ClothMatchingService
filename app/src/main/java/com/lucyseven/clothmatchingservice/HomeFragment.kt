package com.lucyseven.clothmatchingservice

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lucyseven.clothmatchingservice.cloth.impl.ClothDataImpl
import com.lucyseven.clothmatchingservice.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var forecastAdapter: HomeForecastAdapter
    private lateinit var forecastLayoutManager: RecyclerView.LayoutManager
    private lateinit var clothListAdapter: HomeClothAdapter
    private lateinit var clothListLayoutManager: RecyclerView.LayoutManager

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

//      상인
//      --------------- 요기 안에서 구현하시면 편할겁니다 -------------------------------------------------------
        val model = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        model.weatherDataLive.observe(viewLifecycleOwner) {
            // 상인 it 이 mainActivity 로부터 받은 weatherData 이므로
            // 이 박스 안에서 모든 데이털 처리를 하는게 편합니다. 여기서 weather data 관련된 내용을 처리하면 돼요!
            val weatherData = it

            Glide
                .with(requireContext())
                .load(weatherData.temperature.currentWeatherIconUrl)
                .into(binding!!.todayWeatherUrl)

            binding!!.currentLocation.text = weatherData.city

            binding!!.currentWeather.text = weatherData.temperature.currentWeather

            binding!!.currentTemp.text =
                "${weatherData.temperature.currentTemp} ºC"

            binding!!.todayMinTemp.text =
                "최저 : ${weatherData.temperature.minTemp} ºC"

            binding!!.todayMaxTemp.text =
                "최고 : ${weatherData.temperature.maxTemp} ºC"

            forecastLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            forecastAdapter = HomeForecastAdapter(weatherData.todayForecast)

            binding!!.apply {
                forecastRecyclerView.layoutManager = forecastLayoutManager
                forecastRecyclerView.adapter = forecastAdapter
            }

            val clothList = ClothDataImpl().recommend(
                weatherData.temperature.minTemp,
                weatherData.temperature.maxTemp
            )

            clothListLayoutManager = GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false)
            clothListAdapter = HomeClothAdapter(clothList)

            binding!!.apply {
                clothListRecyclerView.layoutManager = clothListLayoutManager
                clothListRecyclerView.adapter = clothListAdapter
            }



        }

//      --------------------------------------------------------------------------------------------------

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}