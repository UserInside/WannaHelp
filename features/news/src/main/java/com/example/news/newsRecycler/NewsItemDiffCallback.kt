package com.example.news.newsRecycler

import androidx.recyclerview.widget.DiffUtil
import com.example.common.models.NewsUiModel

class NewsItemDiffCallback : DiffUtil.ItemCallback<NewsUiModel>() {
    override fun areItemsTheSame(
        oldItem: NewsUiModel,
        newItem: NewsUiModel,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: NewsUiModel,
        newItem: NewsUiModel,
    ): Boolean = oldItem == newItem
}
