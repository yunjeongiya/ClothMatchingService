package com.lucyseven.clothmatchingservice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.loader.app.LoaderManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lucyseven.clothmatchingservice.databinding.FragmentCommuBinding
import com.lucyseven.clothmatchingservice.weather.api.WeatherData
import kotlinx.coroutines.*
import kotlin.math.round

class CommuFragment : Fragment() {

    var binding: FragmentCommuBinding? = null
    var todayFeedbackList = arrayListOf<WeatherFeedback>()
    var isAnswerToday = false
    lateinit var adapter: MyFeedbackAdapter
    lateinit var db: FirebaseFirestore
    lateinit var layoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentCommuBinding.inflate(layoutInflater, container, false)
        //firebase setting
        getfbdb()
        initLayout()
        return binding!!.root
    }

    private fun initLayout() {

        val model = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        model.weatherDataLive.observe(viewLifecycleOwner) {
            //binding 처리
            binding!!.apply {
                todayRecyclerView.layoutManager = layoutManager
                todayRecyclerView.adapter = adapter
                loctext.text = "오늘 ${it.city}의 기온"
                maxTemp.text = "최고 ${it.temperature.maxTemp}도"
                minTemp.text = "최저 ${it.temperature.minTemp}도"
                cardView2.setOnClickListener {
                    //더보기
                }
                cardView3.setOnClickListener {
                    //더보기
                }
                showdialbtn.setOnClickListener {
                    showDialog()
                }
            }

        }
    }

    private fun getfbdb() {
        db = Firebase.firestore
        db.collection("WeatherFeedback").get()
            .addOnSuccessListener { result ->
                for (document in result!!) {
//                    Log.i("eastsea", document["id"].toString())
//                    Log.i("eastsea", document["date"].toString())
//                    Log.i("eastsea", document["loc"].toString())
//                    Log.i("eastsea", document["temp"].toString())
//                    Log.i("eastsea", document["cloth"].toString())
//                    Log.i("eastsea", document["feedback"].toString())
                    val id = document["id"]?.toString() ?: "1"
                    val date = document["date"]?.toString() ?: "20222022"
                    val loc = document["loc"]?.toString() ?: "서울특별시 강남구"
                    val temp = document["temp"]?.toString() ?: "99"
                    val cloth = document["cloth"]?.toString() ?: "cloth1"
                    val feedback = document["feedback"]?.toString() ?: "test"
                    val item = WeatherFeedback(
                        id.toInt(), date, loc, temp.toInt(), cloth, feedback
                    )
                    todayFeedbackList.add(item)
                    adapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("eastsea", "Error getting documents.", exception)
            }
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val query = db.collection("WeatherFeedback").limit(10) //최근 50개만
        val option =
            FirestoreRecyclerOptions.Builder<WeatherFeedback>()
                .setQuery(query, WeatherFeedback::class.java).build()
//        adapter = MyFeedbackAdapter(option)
        adapter = MyFeedbackAdapter(todayFeedbackList)
        adapter.itemClickListener = object : MyFeedbackAdapter.OnItemClickListener {
            override fun OnItemClick(position: Int) {
                binding.apply {
//                    pIdEdit.setText(adapter.getItem(position).pId.toString())
//                    pNameEdit.setText(adapter.getItem(position).pName)
//                    pQuantityEdit.setText(adapter.getItem(position).pQuantity.toString())
                }
            }
        }
//        adapter.startListening()
        binding!!.apply{
            todayRecyclerView.layoutManager = layoutManager
            todayRecyclerView.adapter = adapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun showDialog() {
        val fragmentManager = childFragmentManager
        val newFragment = FullDiaglogFragment()
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, newFragment)
            ?.addToBackStack(null)
            ?.commitAllowingStateLoss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        adapter.stopListening()
        binding = null //view destroy될때, fragment의 binding도 null로 초기화하여 메모리 누수 없도록 하자!
    }
}