package com.example.myapplication.Home


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Category
import com.example.myapplication.R
import com.example.myapplication.databinding.CategoryItemBinding

class CategoryAdapter(private val context: Context) : RecyclerView.Adapter<CategoryAdapter.AttractionViewHolder>() {

    var categories: List<Category> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(inflater, parent, false)
        return AttractionViewHolder(binding,context)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: AttractionViewHolder, position: Int) {
        holder.bind(categories[position])
    }
    class AttractionViewHolder(private val binding: CategoryItemBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {

            binding.category.text = category.name


            binding.executePendingBindings()
        }
    }

}
