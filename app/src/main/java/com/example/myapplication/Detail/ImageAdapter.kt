package com.example.myapplication.Detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.AttractionData
import com.example.myapplication.Data.Image
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemDetailImageBinding

class ImageAdapter(private val context: Context) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    var images: List<Image> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDetailImageBinding.inflate(inflater, parent, false)
        return ImageViewHolder(binding,context)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }
    class ImageViewHolder(private val binding: ItemDetailImageBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Image) {
            Glide.with(context)
                .load(image.src)
                .transition(DrawableTransitionOptions.withCrossFade()) // 设置渐变效果（可选）
                .apply( RequestOptions().placeholder(R.drawable.placeholder))// 设置渐变效果（可选）
                .into(binding.imageView16)

            binding.executePendingBindings()
        }
    }

}
