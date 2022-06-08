package com.lucyseven.clothmatchingservice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.loader.app.LoaderManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lucyseven.clothmatchingservice.databinding.FragmentCommuBinding
import com.lucyseven.clothmatchingservice.weather.api.WeatherData
import kotlinx.coroutines.*
import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.round

class CommuFragment : Fragment() {

    var binding: FragmentCommuBinding? = null
    var todayFeedbackList = arrayListOf<WeatherFeedback>()
    var similarDayFeedbackList = arrayListOf<WeatherFeedback>()
    var isSimilarDay = false
    var curTemp: Int = 0
    val today = LocalDateTime.now()
    val dateFormat = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
    val dateFormat2 = DateTimeFormatter.ofPattern("yyyyMMdd")
    lateinit var adapter: MyFeedbackAdapter
    lateinit var db: FirebaseFirestore
    lateinit var layoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

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
                            adapter = MyFeedbackAdapter(todayFeedbackList, isSimilarDay)
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
                    if (date == dateFormat2.format(today)) {
                        todayFeedbackList.add(item)
                    }
                    if (currentTemp.toInt() <= curTemp + 3 && currentTemp.toInt() >= curTemp - 3) {
                        similarDayFeedbackList.add(item)
                    }
                    adapter.notifyDataSetChanged()
                }
                Log.i("eastsea", "similiar list size : ${similarDayFeedbackList.size}")
                Log.i("eastsea", "today list size : ${todayFeedbackList.size}")

            }
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = MyFeedbackAdapter(todayFeedbackList, isSimilarDay)
//        adapter.itemClickListener = object : MyFeedbackAdapter.OnItemClickListener {
//            override fun OnItemClick(position: Int) {
//                binding.apply {
//                    pIdEdit.setText(adapter.getItem(position).pId.toString())
//                    pNameEdit.setText(adapter.getItem(position).pName)
//                    pQuantityEdit.setText(adapter.getItem(position).pQuantity.toString())
//                }
//            }
//        }
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