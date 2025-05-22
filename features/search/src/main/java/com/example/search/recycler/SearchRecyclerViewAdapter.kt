package com.example.search.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.common.models.NewsUiModel
import com.example.news.newsRecycler.SearchItemDiffCallback
import com.example.search.R
import com.example.search.recycler.SearchRecyclerViewAdapter.ViewHolder

class SearchRecyclerViewAdapter(
//    private val onItemClicked: (Int) -> Unit
) :
    ListAdapter<NewsUiModel, ViewHolder>(SearchItemDiffCallback()) {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_nko_title)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_card_search_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.title.text = item.name
    }
}
