package com.lucyseven.clothmatchingservice

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lucyseven.clothmatchingservice.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private var binding: FragmentSettingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)

        binding!!.apply {
            val pref =
                requireContext().getSharedPreferences("shoppingMall", Activity.MODE_PRIVATE)
            val prefEdit = pref.edit()

            musinsa.isChecked = pref.getBoolean("musinsa", true)
            brandy.isChecked = pref.getBoolean("brandy", true)
            styleShare.isChecked = pref.getBoolean("styleShare", true)
            hiver.isChecked = pref.getBoolean("hiver", true)
            twentyNineCM.isChecked = pref.getBoolean("twentyNineCM", true)
            naver.isChecked = pref.getBoolean("naver", true)

            musinsa.setOnCheckedChangeListener { _, isChecked ->
                prefEdit.putBoolean("musinsa", isChecked).apply()
            }

            brandy.setOnCheckedChangeListener { _, isChecked ->
                prefEdit.putBoolean("brandy", isChecked).apply()
            }

            styleShare.setOnCheckedChangeListener { _, isChecked ->
                prefEdit.putBoolean("styleShare", isChecked).apply()
            }

            hiver.setOnCheckedChangeListener { _, isChecked ->
                prefEdit.putBoolean("hiver", isChecked).apply()
            }

            twentyNineCM.setOnCheckedChangeListener { _, isChecked ->
                prefEdit.putBoolean("twentyNineCM", isChecked).apply()
            }

            naver.setOnCheckedChangeListener { _, isChecked ->
                prefEdit.putBoolean("naver", isChecked).apply()
            }
            //for community setting
            val pref2 = requireContext().getSharedPreferences("commuSetting", Activity.MODE_PRIVATE)
            val prefEdit2 = pref2.edit()
            localsetting.isChecked = pref2.getBoolean("localsetting", true)
            localsetting.setOnCheckedChangeListener {_, isChecked ->
                prefEdit2.putBoolean("localsetting", isChecked).apply()
            }

        }


        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}