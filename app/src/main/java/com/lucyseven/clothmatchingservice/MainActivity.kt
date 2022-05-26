package com.lucyseven.clothmatchingservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.firestore.FirebaseFirestore
import com.lucyseven.clothmatchingservice.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()
    lateinit var myAdapter: MyAdapter
    var itemList = arrayListOf<User>()

    // 상인 : 날씨에 대한 weatherData 정보를 가지고있다
    private var weatherData: WeatherData = WeatherData(0, 0, 0, "noCity", ArrayList())
    // 상인 : 현재 gps구현하다 어려운 문제가 있어서 deault로 좌표를 서울 한남동인가로 찍어놨습니다 이후 gps는 수정하겠습니다
    // 그리고 아직 달 평균기온구하는 api를 다른곳에서 찾아야할것같아서 추후에 구현후 올리겠습니다
    val baseUrl =
        "https://api.openweathermap.org/data/2.5/weather?lat=37.5&lon=127.5&appid=b2110b957ed8967488040544ad665408"
    val forcasetUrl =
        "https://api.openweathermap.org/data/2.5/forecast?lat=37.5&lon=127.5&appid=b2110b957ed8967488040544ad665408"

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
    private fun initWeatherData() {

        CoroutineScope(Dispatchers.Default).launch {
            var doc = Jsoup.connect(forcasetUrl).ignoreContentType(true).get().text()
            val jsonForecast = JSONObject(doc)
            val list = jsonForecast.getJSONArray("list")
            for (i in 0 until 40) {
                val content = list.getJSONObject(i)
                val time = content.getString("dt_txt")
                val temp = content.getJSONObject("main").getDouble("temp")
                val weather = content.getJSONArray("weather").getJSONObject(0).getString("main")
                weatherData.todayForecast.add(TodayForecast(time, weather, temp.toInt()))
            }

            doc = Jsoup.connect(baseUrl).ignoreContentType(true).get().text()
            val json = JSONObject(doc)
            val weatherArray = json.getJSONArray("weather")
            val weather = weatherArray.getJSONObject(0).getString("main")

            val main = json.getJSONObject("main")
            val curTemp = main.getDouble("temp") - 273.15f
            val maxTemp = main.getDouble("temp_max") - 273.15f
            val minTemp = main.getDouble("temp_min") - 273.15f
            val city = json.getString("name")

            weatherData.curTemp = curTemp.toInt()
            weatherData.maxTemp = maxTemp.toInt()
            weatherData.minTemp = minTemp.toInt()
            weatherData.city = city

        }

        var tmp: Int = 0
        val bfTmp = weatherData!!.curTemp
        //  동기화 될 때 까지 기다리기
        while (bfTmp == tmp) {
            tmp = weatherData!!.curTemp
        }
    }
}