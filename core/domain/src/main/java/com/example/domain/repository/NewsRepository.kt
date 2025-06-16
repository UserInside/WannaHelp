package com.example.domain.repository

import com.example.domain.entities.NewsDomainModel

interface NewsRepository {
    suspend fun getNewsByCategories(categories: Set<String>): List<NewsDomainModel>
    suspend fun getEventById(id: Int): NewsDomainModel
    suspend fun markNewsItemAsRead(newsItemId: Int)

}