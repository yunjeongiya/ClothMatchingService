package com.lucyseven.clothmatchingservice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lucyseven.clothmatchingservice.databinding.FragmentCommuBinding
import com.lucyseven.clothmatchingservice.weather.api.WeatherData
import kotlinx.coroutines.*
import kotlin.math.round

class CommuFragment : Fragment() {

    var binding: FragmentCommuBinding?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentCommuBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        model.weatherDataLive.observe(viewLifecycleOwner) {
            // 상인 it 이 mainActivity 로부터 받은 weatherData 이므로
            // 이 박스 안에서 모든 데이털 처리를 하는게 편합니다. 여기서 weather data 관련된 내용을 처리하면 돼요!






        }


        binding!!.apply {
            cardView2.setOnClickListener {
                //더보기
            }
            cardView3.setOnClickListener {
                //더보기
            }
            showdialbtn.setOnClickListener {
                MaterialAlertDialogBuilder(requireContext())    //fragment에서 context 가져오는 방법이라고함...
                    .setTitle("title")
                    .setMessage("this is message")
                    .setNeutralButton("neutral") { dialog, which ->
                        // Respond to neutral button press
                    }
                    .setNegativeButton("negative") { dialog, which ->
                        // Respond to negative button press
                    }
                    .setPositiveButton("positive") { dialog, which ->
                        // Respond to positive button press

                        //db 에 전송 완료되면 내용 바꿔주기 위해
                    }
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null //view destroy될때, fragment의 binding도 null로 초기화하여 메모리 누수 없도록 하자!
    }
}