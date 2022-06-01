package com.lucyseven.clothmatchingservice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.loader.app.LoaderManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lucyseven.clothmatchingservice.databinding.FragmentCommuBinding

class CommuFragment : Fragment() {

    var binding: FragmentCommuBinding? = null
    var isAnswerToday = false
    lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCommuBinding.inflate(layoutInflater, container, false)
        //firebase setting
        getfbdb()
        initLayout()
        return binding!!.root
    }

    private fun initLayout() {

    }

    private fun getfbdb() {
        db = Firebase.firestore
        db.collection("WeatherFeedback").get()
            .addOnSuccessListener { result ->
                for (document in result!!) {
                    Log.i("eastsea", document["id"].toString())
                    Log.i("eastsea", document["date"].toString())
                    Log.i("eastsea", document["loc"].toString())
                    Log.i("eastsea", document["temp"].toString())
                    Log.i("eastsea", document["cloth"].toString())
                    Log.i("eastsea", document["feedback"].toString())

//                    val item = WeatherFeedback(
//                        document["id"].toString().toInt(),
//                        document["date"].toString(),
//                        document["loc"].toString(),
//                        document["temp"].toString().toFloat(),
//                        document["cloth"].toString(),
//                        document["feedback"].toString()
//                    )

                }
            }
            .addOnFailureListener { exception ->
                Log.w("eastsea", "Error getting documents.", exception)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.apply {
            cardView2.setOnClickListener {
                //더보기
            }
            cardView3.setOnClickListener {
                //더보기
            }
            showdialbtn.setOnClickListener {
//                MaterialAlertDialogBuilder(requireContext())    //fragment에서 context 가져오는 방법이라고함...
//                    .setTitle("title")
//                    .setMessage("this is message")
//                    .setNeutralButton("neutral") { dialog, which ->
//                        // Respond to neutral button press
//                    }
//                    .setNegativeButton("negative") { dialog, which ->
//                        // Respond to negative button press
//                    }
//                    .setPositiveButton("positive") { dialog, which ->
//                        // Respond to positive button press
//
//                        //db 에 전송 완료되면 내용 바꿔주기 위해
//                        isAnswerToday = true
//                    }
//                    .show()
                showDialog()
            }
        }
    }

    private fun showDialog() {
        val fragmentManager = childFragmentManager
        val newFragment = FullDiaglogFragment()
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, newFragment)
            ?.commitAllowingStateLoss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null //view destroy될때, fragment의 binding도 null로 초기화하여 메모리 누수 없도록 하자!
    }
}