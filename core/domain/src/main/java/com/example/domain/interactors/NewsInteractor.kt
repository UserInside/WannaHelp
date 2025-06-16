package com.example.domain.interactors

import com.example.domain.entities.NewsDomainModel
import com.example.domain.repository.NewsRepository

class NewsInteractor(
    private val repository: NewsRepository
) {
    suspend fun getNewsByCategories(categories: Set<String>): List<NewsDomainModel> {
        return repository.getNewsByCategories(categories)
    }

    suspend fun getEventById(id: Int): NewsDomainModel {
        return repository.getEventById(id)
    }

    suspend fun markNewsItemAsRead(newsItemId: Int) {
        repository.markNewsItemAsRead(newsItemId)
    }
}