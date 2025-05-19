package com.example.news.newsRecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.common.models.NewsUiModel
import com.example.news.newsRecycler.NewsRecyclerViewAdapter.ViewHolder

class NewsRecyclerViewAdapter(
    private val onItemClicked: (NewsUiModel) -> Unit,
) :
    ListAdapter<NewsUiModel, ViewHolder>(NewsItemDiffCallback()) {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.news_image)
        val title: TextView = itemView.findViewById(R.id.news_card_title)
        val description: TextView = itemView.findViewById(R.id.tv_news_card_description)
        val remains: TextView = itemView.findViewById(R.id.tv_news_card_remains)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_card_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = getItem(position)

        Glide.with(holder.itemView)
            .load(item.imageRes)
//            .placeholder(R.drawable.placeholder_24)
            .into(holder.image)
//        holder.image.setBackgroundResource("/home/igor/WannaHelpApplication/WannaHelp/app/src/main/res/drawable/news_card_img1.png")
        holder.title.text = item.name
        holder.description.text = item.description
        holder.remains.text = item.date

        holder.itemView.setOnClickListener {
            onItemClicked(item)
        }
    }
}
