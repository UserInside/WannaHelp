package com.example.news.newsRecycler

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.entities.NewsDomainModel

class NewsItemDiffCallback : DiffUtil.ItemCallback<NewsDomainModel>() {
    override fun areItemsTheSame(
        oldItem: NewsDomainModel,
        newItem: NewsDomainModel,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: NewsDomainModel,
        newItem: NewsDomainModel,
    ): Boolean = oldItem == newItem
}
