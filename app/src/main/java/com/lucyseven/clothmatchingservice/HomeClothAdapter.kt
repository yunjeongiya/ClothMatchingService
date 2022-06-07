package com.lucyseven.clothmatchingservice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucyseven.clothmatchingservice.cloth.api.Cloth
import com.lucyseven.clothmatchingservice.databinding.HomeClothRowBinding

class HomeClothAdapter(private val itemList: List<Cloth>) :
    RecyclerView.Adapter<HomeClothAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: HomeClothRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = HomeClothRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            clothImageView.setImageResource(itemList[position].iconId)
            clothTextView.text = itemList[position].name
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


}