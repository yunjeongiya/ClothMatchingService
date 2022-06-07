package com.lucyseven.clothmatchingservice

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lucyseven.clothmatchingservice.databinding.HomeForecastRowBinding
import com.lucyseven.clothmatchingservice.weather.api.TodayForecast

class HomeForecastAdapter(private val itemList: ArrayList<TodayForecast>) :
    RecyclerView.Adapter<HomeForecastAdapter.ViewHolder>() {

    private var context: Context? = null

    inner class ViewHolder(val binding: HomeForecastRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            HomeForecastRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            Glide
                .with(context!!)
                .load(itemList[position].weatherIconUrl)
                .into(forecastWeather)

            forecastTemp.text = itemList[position].temp.toString()

            val time = itemList[position].time.toString()
            val splitTime = time.split('T')
            forecastTime.text = splitTime[0] + '\n' + splitTime[1]
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}