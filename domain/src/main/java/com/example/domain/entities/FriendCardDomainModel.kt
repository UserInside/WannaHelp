package com.example.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class FriendCardItem(
    val id: Int,
    val image: String,
    val name: String,
)
