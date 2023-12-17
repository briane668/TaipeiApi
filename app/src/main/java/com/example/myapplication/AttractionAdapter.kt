package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.AttractionItemBinding


class AttractionAdapter(private val context: Context) : RecyclerView.Adapter<AttractionAdapter.AttractionViewHolder>() {

    var attractions: List<AttractionData> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AttractionItemBinding.inflate(inflater, parent, false)
        return AttractionViewHolder(binding,context)
    }

    override fun getItemCount(): Int = attractions.size

    override fun onBindViewHolder(holder: AttractionViewHolder, position: Int) {
        holder.bind(attractions[position])
    }



    class AttractionViewHolder(private val binding: AttractionItemBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(attraction: AttractionData) {

            binding.attraction = attraction


            // 設定點擊監聽器
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
//
//                    // 在這裡處理點擊事件，例如跳轉到另一個畫面
//                    val intent = Intent(context, YourTargetActivity::class.java)
//                    // 可以傳遞一些資料到目標 Activity
//                    intent.putExtra("key", yourData)
//                    context.startActivity(intent)
                }
            }
            binding.executePendingBindings()

        }

    }
}
