package com.lucyseven.clothmatchingservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.lucyseven.clothmatchingservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()
    lateinit var myAdapter:MyAdapter
    var itemList = arrayListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initLayout()
    }


    private fun initData() {
        db.collection("User")   // 작업할 컬렉션
            .get()      // 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우
                itemList.clear()
                for (document in result!!) {  // 가져온 문서들은 result에 들어감
                    Log.i("asdfasdf",document["email"].toString())
                    val item = User(document["email"].toString(), document["pw"].toString())
                    itemList.add(item)
                }
//                myAdapter.notifyDataSetChanged()  // 리사이클러 뷰 갱신
            }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        myAdapter = MyAdapter(itemList)
        Log.i("asdfasdf",itemList.toString())
        myAdapter.itemClickListener = object : MyAdapter.OnItemClickListener{
            override fun onItemClick(data: User) {
                //adapter.notifyDataSetChanged()  // 변경 정보 등록 가능
            }
        }
        binding.recyclerView.adapter = myAdapter
    }

    private fun initLayout() {
        binding.apply {
            button.setOnClickListener {
                val data = hashMapOf(
                    "email" to emailInput.text.toString(),
                    "pw" to pwInput.text.toString()
                )
                db.collection("User").add(data)
                    .addOnSuccessListener {
                        // 성공할 경우
                        Toast.makeText(this@MainActivity, "데이터가 추가되었습니다", Toast.LENGTH_SHORT).show()
                    }
            }
            show.setOnClickListener {
                initRecyclerView()
            }
        }
    }
}