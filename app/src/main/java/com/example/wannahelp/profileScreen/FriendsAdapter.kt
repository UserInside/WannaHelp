package com.example.wannahelp.profileScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wannahelp.R

class FriendsAdapter(private val friendCardsList: List<FriendCard>) : RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.img_friend_avatar)
        val name: TextView = itemView.findViewById(R.id.tv_friend_name)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_friends_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = friendCardsList[position]
        holder.avatar.setImageResource(item.avatar)
        holder.name.text = item.name
    }

    override fun getItemCount(): Int {
        return friendCardsList.size
    }
}
