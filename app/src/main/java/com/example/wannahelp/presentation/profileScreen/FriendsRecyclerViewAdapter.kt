package com.example.wannahelp.presentation.profileScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.wannahelp.R
import com.example.wannahelp.domain.entities.FriendCardItem

class FriendsRecyclerViewAdapter(private val friendCardsListItem: List<FriendCardItem>) :
    RecyclerView.Adapter<FriendsRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.img_friend_avatar)
        val name: TextView = itemView.findViewById(R.id.tv_friend_name)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_card_friends, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = friendCardsListItem[position]
        Glide.with(holder.itemView)
            .load(item.image)
            .placeholder(R.drawable.placeholder_24)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.image)
        holder.name.text = item.name
    }

    override fun getItemCount(): Int {
        return friendCardsListItem.size
    }
}
