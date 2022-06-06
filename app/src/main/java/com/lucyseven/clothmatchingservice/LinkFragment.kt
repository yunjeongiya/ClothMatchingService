package com.lucyseven.clothmatchingservice

<<<<<<< Updated upstream
=======
import android.app.Activity
>>>>>>> Stashed changes
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< Updated upstream
=======
import androidx.lifecycle.ViewModelProvider
>>>>>>> Stashed changes
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucyseven.clothmatchingservice.databinding.FragmentLinkBinding

class LinkFragment : Fragment() {
    var binding:FragmentLinkBinding?=null
    lateinit var adapter: ClothAdapter
    lateinit var linkActivity: MainActivity

<<<<<<< Updated upstream
    val clothing:ArrayList<String> = ArrayList()
=======
    private var binding: FragmentLinkBinding? = null
    lateinit var adapter: ClothAdapter
    lateinit var linkActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        linkActivity = context as MainActivity
    }
>>>>>>> Stashed changes

    override fun onAttach(context: Context) {
        super.onAttach(context)
        linkActivity = context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinkBinding.inflate(layoutInflater, container, false)
<<<<<<< Updated upstream
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClothingData()
        binding!!.apply {
            recyclerView.layoutManager = LinearLayoutManager(linkActivity, LinearLayoutManager.VERTICAL, false)
            adapter = ClothAdapter(clothing)
            recyclerView.adapter = adapter
        }
    }

    private fun initClothingData()
    {
        clothing.add("가디건")
        clothing.add("셔츠")
        clothing.add("긴바지")
=======
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
            binding!!.apply {
                recyclerView.layoutManager = LinearLayoutManager(linkActivity, LinearLayoutManager.VERTICAL, false)
                TWhighest.text = "이번주 ${it.city}의 평균 최고 기온은"
                TWlowest.text = "이번주 ${it.city}의 평균 최저 기온은"
                HighestTemp.text = "${it.weekTemperature.maxTemp}"
                LowestTemp.text = "${it.weekTemperature.minTemp}"
            }
        }

        model.clothDataLive.observe(viewLifecycleOwner) {
            // 상인 it이 현재 온도에 맞는 옷 리스트들이 들어있는 Cloth 데이터 리스트
            val clothList = it
            binding!!.apply {
                adapter = ClothAdapter(it)
                recyclerView.adapter = adapter
            }
        }
//--------------------------------------------------------------------------------------------------

        return binding!!.root
>>>>>>> Stashed changes
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}