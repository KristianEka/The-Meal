package com.ekachandra.themeal.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ekachandra.themeal.data.source.remote.response.MealsItem
import com.ekachandra.themeal.databinding.ItemMenuBinding

class MealAdapter : ListAdapter<MealsItem, MealAdapter.ListViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((MealsItem) -> Unit)? = null

    inner class ListViewHolder(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: MealsItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(meal.strMealThumb)
                    .into(ivThumb)

                tvName.text = meal.strMeal
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
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealAdapter.ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MealsItem>() {
            override fun areItemsTheSame(
                oldItem: MealsItem,
                newItem: MealsItem
            ): Boolean {
                return oldItem.idMeal == newItem.idMeal
            }

            override fun areContentsTheSame(
                oldItem: MealsItem,
                newItem: MealsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}