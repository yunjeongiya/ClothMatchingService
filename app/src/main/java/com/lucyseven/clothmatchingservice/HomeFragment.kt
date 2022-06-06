package com.lucyseven.clothmatchingservice

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lucyseven.clothmatchingservice.weather.api.WeatherData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import com.lucyseven.clothmatchingservice.MainActivity as MainActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}