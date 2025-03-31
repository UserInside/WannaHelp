package com.example.wannahelp.newsScreen.newsFilterScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wannahelp.R
import com.example.wannahelp.common.Category
import com.google.android.material.switchmaterial.SwitchMaterial

class NewsFilterRecyclerViewAdapter(
    private val onSwitchChanged: (category: Category, isChecked: Boolean) -> Unit,
) :
    ListAdapter<FilterCategoryCard, NewsFilterRecyclerViewAdapter.ViewHolder>(NewsFilterDiffCallback()) {
    class ViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_category_name)
        val switch: SwitchMaterial = itemView.findViewById(R.id.switch_choose_category)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_card_filter_help_categories, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.title.text = item.title
        holder.switch.isChecked = item.isChecked
        holder.switch.setOnCheckedChangeListener { _, isChecked ->
            onSwitchChanged(item.category, isChecked)
        }
    }
}
