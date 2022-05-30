package com.lucyseven.clothmatchingservice

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucyseven.clothmatchingservice.databinding.LinkrowBinding

class ClothAdapter(val clothing:ArrayList<String>) : RecyclerView.Adapter<ClothAdapter.ViewHolder>()
{
    lateinit var adapter: ClothShopAdapter
    lateinit var context: Context

    val shopList:ArrayList<String> = ArrayList()
    val urlList:ArrayList<String> = ArrayList()

    inner class ViewHolder(val binding:LinkrowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        initShopData()
        initUrlData()
        context = recyclerView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothAdapter.ViewHolder {
        val binding = LinkrowBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClothAdapter.ViewHolder, position: Int) {
        //holder.binding.clothImage : 이미지까지 변경할 생각이라면 위에서 ArrayList<Cloth???>식으로, image를 당겨올 수 있는 data class 등이 필요
        holder.binding.clothImage.setImageResource(R.drawable.ic_baseline_shopping_cart_24)
        holder.binding.clothText.text = clothing[position]
        holder.binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = ClothShopAdapter(shopList, urlList)
        holder.binding.recyclerView.adapter = adapter
    }

    private fun initShopData()
    {
        shopList.add("무신사")
        shopList.add("지그재그")
        shopList.add("에이블리")
    }

    private fun initUrlData()
    {
        urlList.add("https://www.musinsa.com/app/")
        urlList.add("https://zigzag.kr/")
        urlList.add("https://a-bly.com/")
    }

    override fun getItemCount(): Int {
        return clothing.size
    }

}