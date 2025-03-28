package com.example.wannahelp.searchScreen

import androidx.recyclerview.widget.DiffUtil

class SearchResultItemDiffCallback: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean =
        oldItem == newItem
}

// strannie sravnenizya