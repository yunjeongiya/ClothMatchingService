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

    override fun onStart() {
        super.onStart()
        val decorView = dialog?.window?.decorView
        decorView?.animate()?.translationY(-100f)
            ?.setStartDelay(300)
            ?.setDuration(300)
            ?.start()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFullDiaglogBinding.inflate(layoutInflater)
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
                    "서울시 광진구 구의동",
                    30.1f,
                    clothesStr,
                    feedbacktext.text.toString()
                )
                db.collection("WeatherFeedback")
                    .add(feedback)
                    .addOnSuccessListener { documentRef -> Log.d("eastsea", "ID : ${documentRef}") }
                    .addOnFailureListener { e -> Log.e("eastsea", "Error : ${e}") }
            }
        }
        return binding.root
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