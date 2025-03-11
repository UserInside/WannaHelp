package com.example.wannahelp.newsScreen.newsFilterScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wannahelp.R
import com.google.android.material.switchmaterial.SwitchMaterial

class NewsFilterRecyclerViewAdapter(private val list: List<FilterCategoryCard>) :
    RecyclerView.Adapter<NewsFilterRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_category_name)
        val switcher: SwitchMaterial = itemView.findViewById(R.id.switch_choose_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_card_filter_help_categories, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position].title
        holder.switcher.isChecked = list[position].isSelected
    }


}