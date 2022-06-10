package com.lucyseven.clothmatchingservice.shoplink

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucyseven.clothmatchingservice.databinding.LinkrowrowBinding

class ClothShopAdapter(val imagelist:ArrayList<Int>, val clothing:ArrayList<String>, val link:ArrayList<String>) : RecyclerView.Adapter<ClothShopAdapter.ViewHolder>()
{
    lateinit var context: Context
    inner class ViewHolder(val binding: LinkrowrowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LinkrowrowBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.mallImage.setImageResource(imagelist[position])
        holder.binding.toWebsite.text = clothing[position]
        holder.binding.toWebsite.setOnClickListener{
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link[position])))
        }
    }

    override fun getItemCount(): Int {
        return clothing.size
    }
}