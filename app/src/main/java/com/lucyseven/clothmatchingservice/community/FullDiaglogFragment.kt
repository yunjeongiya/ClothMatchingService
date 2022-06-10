package com.lucyseven.clothmatchingservice.community

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lucyseven.clothmatchingservice.weather.impl.DataViewModel
import com.lucyseven.clothmatchingservice.MainActivity
import com.lucyseven.clothmatchingservice.R
import com.lucyseven.clothmatchingservice.databinding.FragmentFullDiaglogBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// https://developer.android.com/guide/topics/ui/dialogs.html#FullscreenDialog

class FullDiaglogFragment : androidx.fragment.app.DialogFragment() {
    private var _binding: FragmentFullDiaglogBinding? = null
    private val binding get() = _binding!!
    val today = LocalDateTime.now()
    val dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd")
    val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
    val db = Firebase.firestore
    var clothList = arrayListOf<String>()
    var clothMap = mapOf<Int, String>(
        R.drawable.tshirt to "반팔티",
        R.drawable.linen to "여름바지",
        R.drawable.summer_shirts to "여름 셔츠",
        R.drawable.shirt to "셔츠",
        R.drawable.short_onepiece to "짧은 원피스",
        R.drawable.shorts to "반바지",
        R.drawable.shorts_2 to "짧은 반바지",
        R.drawable.sleeveless to "민소매",
        R.drawable.skirt to "치마",
        R.drawable.slacks to "슬랙스",
        R.drawable.onepiece to "원피스",
        R.drawable.hoodie to "후드티",
        R.drawable.jacket to "자켓",
        R.drawable.leather_jacket to "가죽자켓",
        R.drawable.jeans to "청바지",
        R.drawable.long_sleeve to "긴팔티",
        R.drawable.mantoman to "맨투맨",
        R.drawable.sweater to "스웨터",
        R.drawable.thin_coat to "얇은코트",
        R.drawable.winter_padding to "패딩",
        R.drawable.winter_coat to "코트",
        R.drawable.cardigan to "가디건",
        R.drawable.winter_pants to "겨울바지",
        R.drawable.winter_scarf to "목도리",
        R.drawable.cotton_pants to "면바지",
        R.drawable.fleece_pants to "내복",
        R.drawable.gloves to "장갑"
    )
    var buttonList = arrayListOf<ImageButton>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    val buttonClicked = View.OnClickListener() {
        val btnid = it.id
        val str = clothMap[btnid]
        if (it.isSelected) {
            //list에서 빼줘야함
            clothList.remove(str)
            Log.i("eastsea", "${str} removed from cloth list")
        } else {
            if (str != null) {
                clothList.add(str)
                Log.i("eastsea", "${str} added from cloth list")
            }
        }
        it.isSelected = !it.isSelected
        //textview에 추가
        var textstr = ""
        if (clothList.size > 0) {
            for (cloth in clothList) {
                textstr += "$cloth, "
            }
            binding.clothListText.text = textstr.substring(0, textstr.length - 2)
        }else{
            binding.clothListText.text = ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFullDiaglogBinding.inflate(layoutInflater)
        binding.apply {
            //make image button's resource & tag
            clothMap.forEach { imgId, clothStr ->
                val newBtn = ImageButton(activity)
                newBtn.setImageResource(imgId)
                newBtn.id = imgId
                newBtn.setBackgroundResource(R.color.button_bg_color)
                newBtn.layoutParams =
                    TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 200, 1f)
                newBtn.scaleType = ImageView.ScaleType.FIT_CENTER
                newBtn.setOnClickListener(buttonClicked)
                buttonList.add(newBtn)
            }
            var newRow: TableRow = TableRow(activity)
            newRow.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 80, 1f)
            for (i in 0 until buttonList.size) {
                if ((i - 1) % 4 == 3) {
                    //make table row
                    Log.i("eastsea", i.toString())
                    tableLayout.addView(newRow)
                    newRow = TableRow(activity)
                    newRow.layoutParams =
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 80, 1f)
                }
                newRow.addView(buttonList[i])
            }
            tableLayout.addView(newRow)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(activity as MainActivity)[DataViewModel::class.java]
        viewModel.weatherDataLive.observe(viewLifecycleOwner) {
            val city = it.city
            val currentTemp = it.temperature.currentTemp
            val minTemp = it.temperature.minTemp
            val maxTemp = it.temperature.maxTemp
            val icon = it.temperature.currentWeatherIconUrl
            var score = 1
            binding.apply {
                dlgclosebtn.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, CommuFragment())
                        .commitAllowingStateLoss()
                }
                radioGroup.setOnCheckedChangeListener { radioGroup, i ->
                    when (i) {
                        R.id.btn1 -> {
                            score = 1
                        }
                        R.id.btn2 -> {
                            score = 2
                        }
                        R.id.btn3 -> {
                            score = 3
                        }
                        R.id.btn4 -> {
                            score = 4
                        }
                        R.id.btn5 -> {
                            score = 5
                        }
                    }
                }
                submitbtn.setOnClickListener {
                    val feedback = WeatherFeedback(
                        date = dateFormat.format(today),
                        time = timeFormat.format(today),
                        loc = city,
                        curTemp = currentTemp,
                        maxTemp = maxTemp,
                        minTemp = minTemp,
                        cloth = clothList,
                        feedback = feedbacktext.text.toString(),
                        feedbackScore = score,
                        weatherIcon = icon
                    )
                    db.collection("WeatherFeedback")
                        .add(feedback)
                        .addOnSuccessListener { documentRef ->
                            Log.d("eastsea", "ID : ${documentRef}")
//                            onDestroyView()
                            //화면 전환 해야함
                            showCommuFrag()
                        }
                        .addOnFailureListener { e -> Log.e("eastsea", "Error : ${e}") }
                }
            }
        }

    }

    private fun showCommuFrag() {
        val fragmentManager = childFragmentManager
        val newFragment = CommuFragment()
        //backstack remove
        activity?.supportFragmentManager?.popBackStack()
        //replace to commu fragment
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, newFragment)
            ?.commitAllowingStateLoss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}