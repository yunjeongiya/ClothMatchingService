package com.lucyseven.clothmatchingservice

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucyseven.clothmatchingservice.databinding.FragmentLinkBinding

class LinkFragment : Fragment() {
    var binding:FragmentLinkBinding?=null
    lateinit var adapter: ClothAdapter
    lateinit var linkActivity: MainActivity

    val clothing:ArrayList<String> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        linkActivity = context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinkBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClothingData()
        binding!!.apply {
            recyclerView.layoutManager = LinearLayoutManager(linkActivity, LinearLayoutManager.VERTICAL, false)
            adapter = ClothAdapter(clothing)
            recyclerView.adapter = adapter
        }
    }

    private fun initClothingData()
    {
        clothing.add("가디건")
        clothing.add("셔츠")
        clothing.add("긴바지")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}