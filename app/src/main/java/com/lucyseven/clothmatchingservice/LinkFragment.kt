package com.lucyseven.clothmatchingservice

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.lucyseven.clothmatchingservice.databinding.FragmentLinkBinding

class LinkFragment : Fragment() {

    private var binding: FragmentLinkBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinkBinding.inflate(layoutInflater, container, false)
        val pref =
            requireContext().getSharedPreferences("shoppingMall", Activity.MODE_PRIVATE)

        val musinsa = pref.getBoolean("musinsa", true) // 무신사가 setting에서 체크 되어 있는지 없는지
        val brandy = pref.getBoolean("brandy", true)
        val styleShare = pref.getBoolean("styleShare", true)
        val hiver = pref.getBoolean("hiver", true)
        val twentyNineCM = pref.getBoolean("twentyNineCM", true)
        val naver = pref.getBoolean("naver", true)

//      상인
//      --------------- 요기 안에서 구현하시면 편할겁니다 -------------------------------------------------------
        val model = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        model.weatherDataLive.observe(viewLifecycleOwner) {
            // 상인 it 이 mainActivity 로부터 받은 weatherData 이므로
            // 이 박스 안에서 모든 데이털 처리를 하는게 편합니다. 여기서 weather data 관련된 내용을 처리하면 돼요!
            val weatherData = it

        }

        model.clothDataLive.observe(viewLifecycleOwner) {
            // 상인 it이 현재 온도에 맞는 옷 리스트들이 들어있는 Cloth 데이터 리스트
            val clothList = it
        }
//      --------------------------------------------------------------------------------------------------

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_link, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}