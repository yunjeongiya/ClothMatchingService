package com.lucyseven.clothmatchingservice

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucyseven.clothmatchingservice.cloth.api.Cloth
import com.lucyseven.clothmatchingservice.databinding.LinkrowBinding

class ClothAdapter(val clothing:List<Cloth>, val shopBase: ArrayList<ShopInfo>) : RecyclerView.Adapter<ClothAdapter.ViewHolder>()
{
    lateinit var adapter: ClothShopAdapter
    lateinit var context: Context

    inner class ViewHolder(val binding:LinkrowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothAdapter.ViewHolder {
        val binding = LinkrowBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClothAdapter.ViewHolder, position: Int) {
        val shopList: ArrayList<String> = ArrayList()
        val linkList: ArrayList<String> = ArrayList()
        val imageList: ArrayList<Int> = ArrayList()

        for(i in 0 until shopBase.count())
        {
            if(shopBase[i].pref)
            {
                holder.binding.clothImage.setImageResource(clothing[position].iconId)
                holder.binding.clothText.text = clothing[position].name
                holder.binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                imageList.add(shopBase[i].iconId)
                shopList.add(shopBase[i].name + "에서\n" + clothing[position].name + " 확인해보기 >")
                linkList.add(shopBase[i].linkBase + clothing[position].name)
            }
        }
        adapter = ClothShopAdapter(imageList, shopList, linkList)
        holder.binding.recyclerView.adapter = adapter
    }


    override fun getItemCount(): Int {
        return clothing.size
    }
}