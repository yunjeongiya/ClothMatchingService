package com.lucyseven.clothmatchingservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.lucyseven.clothmatchingservice.databinding.ActivityMainBinding
import com.lucyseven.clothmatchingservice.weather.api.Location
import com.lucyseven.clothmatchingservice.weather.api.WeatherData
import com.lucyseven.clothmatchingservice.weather.api.WeatherDataFetcher
import com.lucyseven.clothmatchingservice.weather.impl.WeatherDataFetcherImpl
import kotlinx.coroutines.*


class MainActivity: AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()
    lateinit var myAdapter: MyAdapter
    var itemList = arrayListOf<User>()

    // 상인 : 날씨에 대한 weatherData 정보를 가지고있다
    private lateinit var weatherData: WeatherData

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()    //remove title bar
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commitAllowingStateLoss()

        initWeatherData()
        initLayout()
    }

    private fun initData() {
        db.collection("User")   // 작업할 컬렉션
            .get()      // 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우
                itemList.clear()
                for (document in result!!) {  // 가져온 문서들은 result에 들어감
                    Log.i("asdfasdf", document["email"].toString())
                    val item = User(document["email"].toString(), document["pw"].toString())
                    itemList.add(item)
                }
//                myAdapter.notifyDataSetChanged()  // 리사이클러 뷰 갱신
            }
    }

    private fun initLayout() {
        binding.apply {
            bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.page_1 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commitAllowingStateLoss()
                        true
                    }
                    R.id.page_2 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.container, CommuFragment()).commitAllowingStateLoss()
                        true
                    }
                    R.id.page_3 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.container, LinkFragment()).commitAllowingStateLoss()
                        true
                    }
                    R.id.page_4 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.container, SettingFragment()).commitAllowingStateLoss()
                        true
                    }
                    else -> {
                        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commitAllowingStateLoss()
                        false
                    }
                }
            }
        }

    }

    // 상인 weather api에서 뽑아온 데이터 초기화 함수 시작시 한번 시행되고 이후 변경 되지 않는다.
    // 윤정 while 을 이용한 블러킹에서 runBlocking 을 이용해 블러킹
    private fun initWeatherData() {
        runBlocking {
            val job = CoroutineScope(Dispatchers.IO).async {
                WeatherDataFetcherImpl().fetch(Location(longitude = 127.0, latitude = 37.5))
            }
            weatherData = job.await()
        }
    }
}