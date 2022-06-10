package com.lucyseven.clothmatchingservice.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lucyseven.clothmatchingservice.databinding.RowBinding

//firestore ui 사용하기 위한 adapter
//class MyFeedbackAdapter(options: FirestoreRecyclerOptions<WeatherFeedback>) :
//    FirestoreRecyclerAdapter<WeatherFeedback, MyFeedbackAdapter.ViewHolder>(options) {
class MyFeedbackAdapter(val itemList: ArrayList<WeatherFeedback>, val isSimilarDay: Boolean) :
    RecyclerView.Adapter<MyFeedbackAdapter.ViewHolder>() {
//    interface OnItemClickListener {
//        fun OnItemClick(position: Int)
//    }

//    var itemClickListener: AdapterView.OnItemClickListener? = null

    inner class ViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
//            binding.root.setOnClickListener {
//                itemClickListener!!.OnItemClick(bindingAdapterPosition) //adapterPosition is depreciated. adapter 여러개일 때는 달라질 수 있음
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    //    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: WeatherFeedback) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!isSimilarDay) {
            holder.binding.apply {
                //오늘
//                weather icon
//                Glide.with(weatherIcon).load("${it.temperature.currentWeatherIconUrl}").override(100, 100,)
//                    .into(weatherIcon);
                Glide.with(weatherIconImg).load("${itemList[position].weatherIcon}")
                    .override(100, 100).into(weatherIconImg)
                date.text = "${itemList[position].time}, ${itemList[position].curTemp}℃ (${itemList[position].loc})"
                clothes.text = clothListToStr(itemList[position].cloth)
                feedback.text = "${makeFeedback(itemList[position].feedbackScore)}"
                feedback2.text = "\"${itemList[position].feedback}\""
            }
        } else {
            holder.binding.apply {
                //비슷했던 날
                Glide.with(weatherIconImg).load("${itemList[position].weatherIcon}")
                    .override(100, 100).into(weatherIconImg)
                val dateMod = "${
                    itemList[position].date.substring(
                        0,
                        4
                    )
                }년 ${
                    itemList[position].date.substring(
                        4,
                        6
                    )
                }월 ${itemList[position].date.substring(6, 8)}일"
                date.text =
                    "$dateMod, ${itemList[position].minTemp}℃ ~ ${itemList[position].maxTemp}℃ (${itemList[position].loc})"
                date.visibility = View.VISIBLE
                clothes.text = clothListToStr(itemList[position].cloth)
                feedback.text = "${makeFeedback(itemList[position].feedbackScore)}"
                feedback2.text = "\"${itemList[position].feedback}\""

            }
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    fun clothListToStr(clothList:ArrayList<String>): String{
        var returnstr = ""
        if (clothList.size > 0) {
            for (cloth in clothList) {
                returnstr += "$cloth, "
            }
            return returnstr.substring(0, returnstr.length - 2)
        }else{
            return ""
        }
    }
    fun makeFeedback(score:Int):String{
        when(score){
            1 -> return "추웠어요"
            2 -> return "조금 추웠어요"
            3 -> return "적당했어요"
            4 -> return "조금 더웠어요"
            5 -> return "더웠어요"
            else -> return ""
        }
    }

}