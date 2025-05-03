package com.example.wannahelp.presentation.newsScreen.newsRecycler

import androidx.recyclerview.widget.DiffUtil
import com.example.wannahelp.domain.entities.NewsItem

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
