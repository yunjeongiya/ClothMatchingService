package com.lucyseven.clothmatchingservice

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.Date
import com.lucyseven.clothmatchingservice.databinding.FragmentFullDiaglogBinding
import java.util.*

// https://developer.android.com/guide/topics/ui/dialogs.html#FullscreenDialog

class FullDiaglogFragment : DialogFragment() {
    private var _binding: FragmentFullDiaglogBinding? = null
    private val binding get() = _binding!!
    val db = Firebase.firestore
    var checkedList = arrayListOf<Boolean>(false, false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

//    override fun onStart() {
//        super.onStart()
//        val decorView = dialog?.window?.decorView
//        decorView?.animate()?.translationY(-100f)
//            ?.setStartDelay(300)
//            ?.setDuration(300)
//            ?.start()
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFullDiaglogBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(activity as MainActivity)[DataViewModel::class.java]
        viewModel.weatherDataLive.observe(viewLifecycleOwner) {
            val city = it.city
            binding.apply {
                dlgclosebtn.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, CommuFragment())
                        .commitAllowingStateLoss()
                }
                imageButton.setOnClickListener {
                    checkedList[0] = !checkedList[0]
                }
                imageButton2.setOnClickListener {
                    checkedList[1] = !checkedList[1]
                }
                imageButton3.setOnClickListener {
                    checkedList[2] = !checkedList[2]
                }
                submitbtn.setOnClickListener {
                    var clothesStr = ""
                    for (i in 1..checkedList.size) {
                        if (checkedList[i - 1]) {
                            clothesStr += "옷 ${i},"
                        }
                    }
                    val feedback = WeatherFeedback(
                        1,
                        "20220601",
                        city,
                        30,
                        clothesStr,
                        feedbacktext.text.toString()
                    )
                    db.collection("WeatherFeedback")
                        .add(feedback)
                        .addOnSuccessListener { documentRef ->
                            Log.d("eastsea", "ID : ${documentRef}")
//                            onDestroyView()
                        //화면 전환 해야함
                            showCommuFrag()
                        }
                        .addOnFailureListener { e -> Log.e("eastsea", "Error : ${e}") }
                }
            }
        }

    }

    private fun showCommuFrag() {
        val fragmentManager = childFragmentManager
        val newFragment = CommuFragment()
        //backstack remove
        activity?.supportFragmentManager?.popBackStack()
        //replace to commu fragment
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, newFragment)
            ?.commitAllowingStateLoss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}