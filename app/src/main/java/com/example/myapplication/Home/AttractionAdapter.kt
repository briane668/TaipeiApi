package com.example.myapplication.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.AttractionData
import com.example.myapplication.R
import com.example.myapplication.databinding.AttractionItemBinding


class AttractionAdapter(private val context: Context, listener : OnAttractionItemClickListener) : RecyclerView.Adapter<AttractionAdapter.AttractionViewHolder>() {
    var mListener = listener;
    var attractions: List<AttractionData> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AttractionItemBinding.inflate(inflater, parent, false)
        return AttractionViewHolder(binding,context,mListener)
    }

    override fun getItemCount(): Int = attractions.size

    override fun onBindViewHolder(holder: AttractionViewHolder, position: Int) {
        holder.bind(attractions[position])
    }
    class AttractionViewHolder(private val binding: AttractionItemBinding, private val context: Context,private val listener : OnAttractionItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(attraction: AttractionData) {
            binding.attraction = attraction
            Glide.with(context)
                .load(attraction.images.firstOrNull()?.src)
                .transition(DrawableTransitionOptions.withCrossFade()) // 设置渐变效果（可选）
                .apply( RequestOptions().placeholder(R.drawable.placeholder))// 设置渐变效果（可选）
                .into(binding.attractionPhoto)

            binding.root.setOnClickListener {
                listener.onAttractionItemClick(attraction)
            }

            val categoryAdapter = CategoryAdapter(context)
            categoryAdapter.categories = attraction.category
            binding.categoryRecyclerView.adapter = categoryAdapter

            binding.executePendingBindings()
        }
    }

    interface OnAttractionItemClickListener {
        fun onAttractionItemClick(attraction: AttractionData)
    }
}
