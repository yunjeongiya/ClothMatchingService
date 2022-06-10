package com.lucyseven.clothmatchingservice

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.lucyseven.clothmatchingservice.cloth.api.Cloth
import com.lucyseven.clothmatchingservice.cloth.impl.ClothDataImpl
import com.lucyseven.clothmatchingservice.community.CommuFragment
import com.lucyseven.clothmatchingservice.databinding.ActivityMainBinding
import com.lucyseven.clothmatchingservice.shoplink.LinkFragment
import com.lucyseven.clothmatchingservice.weather.api.Location
import com.lucyseven.clothmatchingservice.weather.api.WeatherData
import com.lucyseven.clothmatchingservice.weather.impl.DataViewModel
import com.lucyseven.clothmatchingservice.weather.impl.WeatherDataFetcherImpl
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    // 상인 : 날씨에 대한 weatherData 정보를 가지고있다
//    private lateinit var weatherData: WeatherData
    private lateinit var loc: Location
    private lateinit var pref: SharedPreferences

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (checkGPSProvider()) {
                getLastLocation()
            }
        }
    private val locationPermissionRequest =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_FINE_LOCATION, false
                ) || permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    false
                ) -> getLastLocation()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()//remove title bar
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SplashFragment()).commitAllowingStateLoss()
        initShoppingMallSetting()
        initLocation()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initShoppingMallSetting() {
        pref = getSharedPreferences("shoppingMall", Activity.MODE_PRIVATE)
        val edit = pref.edit()

        if (pref.getInt("init", 0) == 0) {
            edit.putBoolean("musinsa", true)
            edit.putBoolean("brandy", true)
            edit.putBoolean("styleShare", true)
            edit.putBoolean("hiver", true)
            edit.putBoolean("twentyNineCM", true)
            edit.putBoolean("naver", true)

            edit.putInt("init", 1)

            edit.apply()
        }
    }

    private fun initLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
    }

    private fun initLayout() {
        binding.apply {
            bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.page_1 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, HomeFragment()).commitAllowingStateLoss()
                        true
                    }
                    R.id.page_2 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, CommuFragment()).commitAllowingStateLoss()
                        true
                    }
                    R.id.page_3 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, LinkFragment()).commitAllowingStateLoss()
                        true
                    }
                    R.id.page_4 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, SettingFragment()).commitAllowingStateLoss()
                        true
                    }
                    else -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, HomeFragment()).commitAllowingStateLoss()
                        false
                    }
                }
            }
        }

    }

    // 상인 weather api에서 뽑아온 데이터 초기화 함수 시작시 한번 시행되고 이후 변경 되지 않는다.
    // 윤정 while 을 이용한 블러킹에서 runBlocking 을 이용해 블러킹
    private fun initWeatherData(latitude: Double, longitude: Double): WeatherData {
        var weatherData: WeatherData? = null

        runBlocking {
            val job = CoroutineScope(Dispatchers.IO).async {
                WeatherDataFetcherImpl().fetch(Location(latitude, longitude))
            }
            weatherData = job.await()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, HomeFragment()).commitAllowingStateLoss()
        return weatherData!!
    }

    private fun checkGPSProvider(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
    }

    private fun showGPSSetting() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            "앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" +
                    "위치 설정을 허용 하시겠습니까?"
        )
        builder.setPositiveButton(
            "설정"
        ) { _, _ ->
            val gpsSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            activityResultLauncher.launch(gpsSettingIntent)
        }
        builder.setNegativeButton(
            "취소"
        ) { dialogInterface, i ->
            dialogInterface.dismiss()
        }
        builder.create().show()
    }

    private fun showPermissionRequestDlg() {
        AlertDialog.Builder(this)
            .setTitle("위치 서비스 제공")
            .setMessage(
                "앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" +
                        "기기의 위치를 제공하도록 허용 하시겠습니까?"
            )
            .setPositiveButton(
                "설정"
            ) { _, _ ->
                locationPermissionRequest.launch(permissions)
            }
            .setNegativeButton(
                "취소"
            ) { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

    private fun checkFineLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkCoarseLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        when {
            checkFineLocationPermission() -> {
                if (!checkGPSProvider()) {
                    showGPSSetting()
                } else {
                    fusedLocationProviderClient.getCurrentLocation(
                        LocationRequest.PRIORITY_HIGH_ACCURACY,
                        object : CancellationToken() {
                            override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                                return CancellationTokenSource().token
                            }

                            override fun isCancellationRequested(): Boolean {
                                return false
                            }

                        }
                    ).addOnSuccessListener {
                        if (it != null) {
                            val weatherData = initWeatherData(it.latitude, it.longitude)
                            val clothList: List<Cloth> = ClothDataImpl().recommend(
                                weatherData.weekTemperature.minTemp,
                                weatherData.weekTemperature.maxTemp
//                                weatherData.temperature.minTemp,
//                                weatherData.temperature.maxTemp
                            )
                            val model = ViewModelProvider(this).get(DataViewModel::class.java)
                            model.setWeatherData(weatherData)
                            model.setClothData(clothList)
                        }
                    }
                }

//                fusedLocationProviderClient.lastLocation.addOnSuccessListener {
//                    if (it != null) {
//                        loc = LatLng(it.latitude, it.longitude)
//                    }
//                    setCurrentLocation(loc)
//                }
            }

            checkCoarseLocationPermission() -> {
                fusedLocationProviderClient.getCurrentLocation(
                    LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
                    object : CancellationToken() {
                        override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                            return CancellationTokenSource().token
                        }

                        override fun isCancellationRequested(): Boolean {
                            return false
                        }

                    }
                ).addOnSuccessListener {
                    if (it != null) {
                        val weatherData = initWeatherData(it.latitude, it.longitude)
                        val clothList: List<Cloth> = ClothDataImpl().recommend(
                            weatherData.weekTemperature.minTemp,
                            weatherData.weekTemperature.maxTemp
//                            weatherData.temperature.minTemp,
//                            weatherData.temperature.maxTemp
                        )
                        val model = ViewModelProvider(this).get(DataViewModel::class.java)
                        model.setWeatherData(weatherData)
                        model.setClothData(clothList)
                    }
                }
//                fusedLocationProviderClient.lastLocation.addOnSuccessListener {
//                    if (it != null) {
//                        loc = LatLng(it.latitude, it.longitude)
//                    }
//                    setCurrentLocation(loc)
//                }
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) -> showPermissionRequestDlg()

            else -> {
                locationPermissionRequest.launch(permissions)
            }
        }
    }
}
