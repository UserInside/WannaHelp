package com.example.wannahelp.domain.repository

import com.example.wannahelp.domain.entities.NewsItem

interface NewsRepository {
    suspend fun getNewsByCategories(categories: Set<String>): List<NewsItem>
    suspend fun markNewsItemAsRead(newsItemId: Int)

}