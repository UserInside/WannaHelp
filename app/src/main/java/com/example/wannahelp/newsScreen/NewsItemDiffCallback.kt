package com.example.wannahelp.newsScreen

import androidx.recyclerview.widget.DiffUtil

class NewsItemDiffCallback : DiffUtil.ItemCallback<NewsItem>() {
    override fun areItemsTheSame(
        oldItem: NewsItem,
        newItem: NewsItem,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: NewsItem,
        newItem: NewsItem,
    ): Boolean = oldItem == newItem
}
