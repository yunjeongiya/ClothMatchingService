package com.lucyseven.clothmatchingservice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucyseven.clothmatchingservice.databinding.RowBinding

class MyAdapter(val items: ArrayList<User>): RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(data: User)
    }
    var itemClickListener:OnItemClickListener?=null

    inner class MyViewHolder(val binding: RowBinding): RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener{    //root 말고 바인딩된 아이디 사용할 것~
                itemClickListener?.onItemClick(items[adapterPosition])//adapterposition 또 넘겨줄 수도 있음
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            email.text = items[position].email
            pw.text = items[position].pw
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}