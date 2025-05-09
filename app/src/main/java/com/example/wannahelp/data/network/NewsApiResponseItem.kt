package com.example.wannahelp.data.network

import com.example.domain.entities.Category
import com.example.wannahelp.domain.entities.NewsItem
import kotlinx.serialization.Serializable

@Serializable
data class NewsApiResponseItem(
    val id: String,
    val name: String,
    val startDate: Long,
    val endDate: Long,
    val description: String,
    val status: Int,
    val photos: List<String>,
    val category: String,
    val createdAt: Long,
    val phone: String,
    val address: String,
    val organization: String,
) : java.io.Serializable {
    companion object {
        fun mapResponseItemToNewsItem(responseItem: NewsApiResponseItem): NewsItem {
            return NewsItem(
                id = responseItem.id.toInt(),
                imageRes = responseItem.photos[0],
                name = responseItem.name,
                description = responseItem.description,
                date = responseItem.startDate.toString(),
                category = Category.valueOf(responseItem.category.uppercase()),
            )
        }
    }
}
