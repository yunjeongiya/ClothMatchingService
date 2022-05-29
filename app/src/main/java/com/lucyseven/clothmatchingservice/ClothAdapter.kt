package com.lucyseven.clothmatchingservice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucyseven.clothmatchingservice.databinding.LinkrowBinding

class ClothAdapter(val clothing:ArrayList<String>) : RecyclerView.Adapter<ClothAdapter.ViewHolder>()
{
    inner class ViewHolder(val binding:LinkrowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothAdapter.ViewHolder {
        val binding = LinkrowBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClothAdapter.ViewHolder, position: Int) {
        //holder.binding.clothImage : 이미지까지 변경할 생각이라면 위에서 ArrayList<Cloth???>식으로, image를 당겨올 수 있는 data class 등이 필요
        holder.binding.clothImage
        holder.binding.clothText.text = clothing[position]
    }

    override fun getItemCount(): Int {
        return clothing.size
    }

}