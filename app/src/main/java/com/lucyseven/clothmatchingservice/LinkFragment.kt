package com.lucyseven.clothmatchingservice

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucyseven.clothmatchingservice.databinding.FragmentLinkBinding

class LinkFragment : Fragment() {
    var binding: FragmentLinkBinding?=null
    lateinit var adapter: ClothAdapter
    lateinit var linkActivity: MainActivity
    val shopList: ArrayList<ShopInfo> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        linkActivity = context as MainActivity
        initshopList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinkBinding.inflate(layoutInflater, container, false)
        val pref =
            requireContext().getSharedPreferences("shoppingMall", Activity.MODE_PRIVATE)

        shopList[0].pref = pref.getBoolean("musinsa", true) // 무신사가 setting에서 체크 되어 있는지 없는지
        shopList[1].pref = pref.getBoolean("brandy", true)
        shopList[2].pref = pref.getBoolean("styleShare", true)
        shopList[3].pref = pref.getBoolean("hiver", true)
        shopList[4].pref = pref.getBoolean("twentyNineCM", true)
        shopList[5].pref = pref.getBoolean("naver", true)

//      상인
//      --------------- 요기 안에서 구현하시면 편할겁니다 -------------------------------------------------------
        val model = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        model.weatherDataLive.observe(viewLifecycleOwner) {
            // 상인 it 이 mainActivity 로부터 받은 weatherData 이므로
            // 이 박스 안에서 모든 데이털 처리를 하는게 편합니다. 여기서 weather data 관련된 내용을 처리하면 돼요!
            binding!!.apply {
                recyclerView.layoutManager = LinearLayoutManager(linkActivity, LinearLayoutManager.VERTICAL, false)
                cityTextView.text = "${it.city}"
                HighestTemp.text = "${it.weekTemperature.maxTemp}"
                LowestTemp.text = "${it.weekTemperature.minTemp}"
            }

        }

        model.clothDataLive.observe(viewLifecycleOwner) {
            // 상인 it이 현재 온도에 맞는 옷 리스트들이 들어있는 Cloth 데이터 리스트
            binding!!.apply {
                adapter = ClothAdapter(it, shopList)
                recyclerView.adapter = adapter
            }
        }
//      --------------------------------------------------------------------------------------------------

        // Inflate the layout for this fragment
        binding = FragmentLinkBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }


    fun initshopList()
    {
        shopList.add(ShopInfo(R.drawable.musinsa,"무신사", "https://www.musinsa.com/search/musinsa/integration?type=&q=", true))
        shopList.add(ShopInfo(R.drawable.brandy,"브랜디","https://www.brandi.co.kr/search?q=", true))
        shopList.add(ShopInfo(R.drawable.styleshare,"스타일 쉐어", "https://www.styleshare.kr/search/result?keyword=", true))
        shopList.add(ShopInfo(R.drawable.hiver,"하이버","https://www.hiver.co.kr/search?q=", true))
        shopList.add(ShopInfo(R.drawable.twentyninecm,"29CM", "https://search.29cm.co.kr/?keyword=", true))
        shopList.add(ShopInfo(R.drawable.naver,"네이버 쇼핑","https://search.shopping.naver.com/search/all?query=", true))
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}