package com.example.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class FriendCardDomainModel(
    val id: Int,
    val image: String,
    val name: String,
)
