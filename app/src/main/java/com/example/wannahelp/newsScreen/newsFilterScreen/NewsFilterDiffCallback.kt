package com.example.wannahelp.newsScreen.newsFilterScreen

import androidx.recyclerview.widget.DiffUtil

class NewsFilterDiffCallback : DiffUtil.ItemCallback<FilterCategoryCard>() {
    override fun areItemsTheSame(
        oldItem: FilterCategoryCard,
        newItem: FilterCategoryCard,
    ): Boolean = oldItem.title == newItem.title

    override fun areContentsTheSame(
        oldItem: FilterCategoryCard,
        newItem: FilterCategoryCard,
    ): Boolean = oldItem == newItem
}
