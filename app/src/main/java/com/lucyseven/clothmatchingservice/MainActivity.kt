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

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()
    lateinit var myAdapter: MyAdapter
    var itemList = arrayListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()    //remove title bar
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.linearLayout, HomeFragment()).commitAllowingStateLoss()
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
                        supportFragmentManager.beginTransaction().replace(R.id.linearLayout, HomeFragment()).commitAllowingStateLoss()
                        true
                    }
                    R.id.page_2 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.linearLayout, CommuFragment()).commitAllowingStateLoss()
                        true
                    }
                    R.id.page_3 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.linearLayout, LinkFragment()).commitAllowingStateLoss()
                        true
                    }
                    R.id.page_4 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.linearLayout, SettingFragment()).commitAllowingStateLoss()
                        true
                    }
                    else -> {
                        supportFragmentManager.beginTransaction().replace(R.id.linearLayout, HomeFragment()).commitAllowingStateLoss()
                        false
                    }
                }
            }
        }

    }
}