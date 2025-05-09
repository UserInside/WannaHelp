package com.example.domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Category(name: String) {
    @SerialName("kids")
    KIDS(name = "kids"),

    @SerialName("adults")
    ADULTS(name = "adults"),

    @SerialName("aged")
    AGED(name = "aged"),

    @SerialName("animals")
    ANIMALS(name = "animals"),

    @SerialName("events")
    EVENTS(name = "events"),
}