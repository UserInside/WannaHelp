package com.example.categories.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.categories.R
import com.example.common.R as commonR
import com.example.domain.entities.CategoryDomainModel

internal class CategoriesRecyclerViewAdapter(private val categoriesList: List<CategoryDomainModel>) :
    RecyclerView.Adapter<CategoriesRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.img_card_help_category)
        val title: TextView = itemView.findViewById(R.id.tv_title_category)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_card_help_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val currentCard = categoriesList[position]

        with(holder.image) {
            Glide
                .with(holder.itemView)
                .load(currentCard.image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(commonR.drawable.placeholder_24)
                .into(this)
        }
        holder.title.text = currentCard.name
    }

    override fun getItemCount(): Int = categoriesList.size
}
