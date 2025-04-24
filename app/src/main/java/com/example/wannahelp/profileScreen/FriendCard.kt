package com.example.wannahelp.profileScreen

import kotlinx.serialization.Serializable

@Serializable
data class FriendCard(
    val id: Int,
    val image: String,
    val name: String,
)
