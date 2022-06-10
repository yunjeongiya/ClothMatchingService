package com.lucyseven.clothmatchingservice.community

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lucyseven.clothmatchingservice.weather.impl.DataViewModel
import com.lucyseven.clothmatchingservice.R
import com.lucyseven.clothmatchingservice.databinding.FragmentCommuBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.max

class CommuFragment : Fragment() {

    var binding: FragmentCommuBinding? = null
    var todayFeedbackList = arrayListOf<WeatherFeedback>()
    var todayLocalFeedbackList = arrayListOf<WeatherFeedback>()
    var similarDayFeedbackList = arrayListOf<WeatherFeedback>()
    var isSimilarDay = false
    var curTemp: Int = 0
    var minTemp_today: Int = 0
    var maxTemp_today: Int = 0
    var userLoc: String = ""
    val today = LocalDateTime.now()
    val dateFormat = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
    val dateFormat2 = DateTimeFormatter.ofPattern("yyyyMMdd")
    var showOnlyLocal: Boolean = true
    lateinit var adapter: MyFeedbackAdapter
    lateinit var db: FirebaseFirestore
    lateinit var layoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val pref =
            requireContext().getSharedPreferences("commuSetting", Activity.MODE_PRIVATE)

        showOnlyLocal = pref.getBoolean("localsetting", true) // 지역만 보여줄지 여부

        binding = FragmentCommuBinding.inflate(layoutInflater, container, false)
        //firebase setting
        initLayout()
        return binding!!.root
    }

    private fun initLayout() {

        val model = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        model.weatherDataLive.observe(viewLifecycleOwner) {
            //weather data init
            curTemp = it.temperature.currentTemp
            maxTemp_today = it.temperature.maxTemp
            minTemp_today = it.temperature.minTemp
            userLoc = it.city
            //binding 처리
            if (todayFeedbackList.size + similarDayFeedbackList.size == 0)
                getfbdb()

            binding!!.apply {
                dateText.text = "${dateFormat.format(today)}"

                switchbtn.setOnCheckedChangeListener { compoundButton, isChecked ->
                    when (isChecked) {
                        //switch button event
                        true -> {
                            isTodayText.text = "비슷했던 날"
                            isSimilarDay = true
                            adapter = MyFeedbackAdapter(similarDayFeedbackList, isSimilarDay)
                            comRecyclerview.adapter = adapter

                        }
                        false -> {
                            isTodayText.text = "오늘"
                            isSimilarDay = false
                            if (showOnlyLocal) {
                                adapter = MyFeedbackAdapter(todayLocalFeedbackList, isSimilarDay)
                            } else {
                                adapter = MyFeedbackAdapter(todayFeedbackList, isSimilarDay)

                            }
                            comRecyclerview.adapter = adapter
                        }
                    }
                }
                isTodayText.setOnClickListener {
                    if (switchbtn.isChecked) {
                        isTodayText.text = "오늘"
                        isSimilarDay = false
                        adapter = MyFeedbackAdapter(todayFeedbackList, isSimilarDay)
                        comRecyclerview.adapter = adapter
                        switchbtn.isChecked = false
                    } else {
                        isTodayText.text = "비슷했던 날"
                        isSimilarDay = true
                        adapter = MyFeedbackAdapter(similarDayFeedbackList, isSimilarDay)
                        comRecyclerview.adapter = adapter
                        switchbtn.isChecked = true
                    }
                }

                fab.setOnClickListener {
                    showDialog()
                }
            }

        }
    }

    private fun getfbdb() {
        db = Firebase.firestore
        val collection = db.collection("WeatherFeedback")
        //data 일단 다 넣음
        Log.i("eastsea", "curtemp : $curTemp, today : ${dateFormat2.format(today)}")
        collection.get().addOnSuccessListener { result ->
            for (document in result!!) {
                val date = document["date"]?.toString() ?: "20222022"
                val time = document["time"]?.toString() ?: "12:12"
                val loc = document["loc"]?.toString() ?: "서울특별시 강남구"
                val currentTemp = document["curTemp"]?.toString() ?: "99"
                val maxTemp = document["maxTemp"]?.toString() ?: "1"
                val minTemp = document["minTemp"]?.toString() ?: "1"
                val weatherIcon = document["weatherIcon"]?.toString() ?: ""
                val cloth = document["cloth"] as ArrayList<String>
                val feedbackScore = document["feedbackScore"]?.toString() ?: "1"
                val feedback = document["feedback"]?.toString() ?: "test"
                val item = WeatherFeedback(
                    date,
                    time,
                    loc,
                    currentTemp.toInt(),
                    maxTemp.toInt(),
                    minTemp.toInt(),
                    cloth,
                    feedbackScore.toInt(),
                    feedback,
                    weatherIcon
                )
                Log.i(
                    "eastsea",
                    "today : ${dateFormat2.format(today)}, date from fb : ${date}, isEqual : ${
                        date == dateFormat2.format(today)
                    }"
                )
                if (date == dateFormat2.format(today)) {
                    //today
                    if (loc == userLoc) {
                        //같은 지역인 것만 추가
                        todayLocalFeedbackList.add(item)
                    }
                    todayFeedbackList.add(item)
                    todayLocalFeedbackList.sortBy { it -> -it.time.replace(":", "").toInt() }
                    todayFeedbackList.sortBy { it -> -it.time.replace(":", "").toInt() }
                } else if (maxTemp.toInt() <= maxTemp_today + 3 && maxTemp.toInt() >= maxTemp_today - 3 && minTemp.toInt() <= minTemp_today + 3 && minTemp.toInt() >= minTemp_today - 3) {
                    //similar day (not today)
                    similarDayFeedbackList.add(item)
                    similarDayFeedbackList.sortBy { it -> -it.date.toInt() }
                }
                adapter.notifyDataSetChanged()
            }
            Log.i("eastsea", "similiar list size : ${similarDayFeedbackList.size}")
            Log.i("eastsea", "today list size : ${todayFeedbackList.size}")
            Log.i("eastsea", "today local list size : ${todayLocalFeedbackList.size}")
        }
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        if (showOnlyLocal) {
            adapter = MyFeedbackAdapter(todayLocalFeedbackList, isSimilarDay)
        } else {
            adapter = MyFeedbackAdapter(todayFeedbackList, isSimilarDay)
        }
        binding!!.apply {
            comRecyclerview.layoutManager = layoutManager
            comRecyclerview.adapter = adapter
        }
    }


    private fun putResultToList(result: QuerySnapshot?, isSimilar: Boolean) {

        for (document in result!!) {
            val date = document["date"]?.toString() ?: "20222022"
            val time = document["time"]?.toString() ?: "12:12"
            val loc = document["loc"]?.toString() ?: "서울특별시 강남구"
            val curTemp = document["curTemp"]?.toString() ?: "99"
            val maxTemp = document["maxTemp"]?.toString() ?: "1"
            val minTemp = document["minTemp"]?.toString() ?: "1"
            val weatherIcon = document["weatherIcon"]?.toString() ?: ""
            val cloth = document["cloth"] as ArrayList<String>
            val feedbackScore = document["feedbackScore"]?.toString() ?: "1"
            val feedback = document["feedback"]?.toString() ?: "test"
            val item = WeatherFeedback(
                date,
                time,
                loc,
                curTemp.toInt(),
                maxTemp.toInt(),
                minTemp.toInt(),
                cloth,
                feedbackScore.toInt(),
                feedback,
                weatherIcon
            )
            if (isSimilar) {
                similarDayFeedbackList.add(item)
            } else {
                todayFeedbackList.add(item)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun showDialog() {
        val fragmentManager = childFragmentManager
        val newFragment = FullDiaglogFragment()
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, newFragment)
//            ?.addToBackStack(null)
            ?.commitAllowingStateLoss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        adapter.stopListening()
        binding = null //view destroy될때, fragment의 binding도 null로 초기화하여 메모리 누수 없도록 하자!
    }
}