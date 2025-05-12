package com.example.domain.interactors

import com.example.domain.entities.NewsItem
import com.example.wannahelp.domain.repository.NewsRepository

class NewsInteractor(
    private val repository: NewsRepository
) {
    suspend fun getNewsByCategories(categories: Set<String>): List<NewsItem> {
        return repository.getNewsByCategories(categories)
    }

    suspend fun markNewsItemAsRead(newsItemId: Int) {
        repository.markNewsItemAsRead(newsItemId)
    }
}