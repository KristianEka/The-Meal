package com.ekachandra.themeal.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ekachandra.themeal.data.source.remote.response.CategoriesItem
import com.ekachandra.themeal.databinding.ItemMenuBinding

class CategoryAdapter : ListAdapter<CategoriesItem, CategoryAdapter.ListViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((CategoriesItem) -> Unit)? = null

    inner class ListViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoriesItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(category.strCategoryThumb)
                    .into(ivThumb)

                tvName.text = category.strCategory
            }
        }

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = getItem(position)
                    onItemClick?.invoke(clickedItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemMenuBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CategoriesItem>() {
            override fun areItemsTheSame(
                oldItem: CategoriesItem,
                newItem: CategoriesItem
            ): Boolean {
                return oldItem.idCategory == newItem.idCategory
            }

            override fun areContentsTheSame(
                oldItem: CategoriesItem,
                newItem: CategoriesItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}