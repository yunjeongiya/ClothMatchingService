package com.lucyseven.clothmatchingservice

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.lucyseven.clothmatchingservice.databinding.RowBinding

//firestore ui 사용하기 위한 adapter
//class MyFeedbackAdapter(options: FirestoreRecyclerOptions<WeatherFeedback>) :
//    FirestoreRecyclerAdapter<WeatherFeedback, MyFeedbackAdapter.ViewHolder>(options) {
    class MyFeedbackAdapter(val itemList: ArrayList<WeatherFeedback>) :
    RecyclerView.Adapter<MyFeedbackAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun OnItemClick(position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener!!.OnItemClick(bindingAdapterPosition) //adapterPosition is depreciated. adapter 여러개일 때는 달라질 수 있음
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

//    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: WeatherFeedback) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
//            productid.text = model.pId.toString()
//            productname.text = model.pName.toString()
//            productquantity.text = model.pQuantity.toString()
//            Log.i("eastsea", "cloth adapter: ${model.cloth}")
//            clothes.text = model.cloth.toString()
//            feedback.text = model.feedback.toString()
            clothes.text = itemList[position].cloth.toString()
            feedback.text = itemList[position].feedback.toString()
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}