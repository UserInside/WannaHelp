package com.example.data

import com.example.data.db.AppDatabase
import com.example.data.db.mapEventDbEntityToNewsItem
import com.example.domain.entities.NewsItem
import com.example.wannahelp.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(val db: AppDatabase) : NewsRepository {
    override suspend fun getNewsByCategories(categories: Set<String>): List<NewsItem> {
        return db.getEventsDao().getEvents(categories)
            .map { mapEventDbEntityToNewsItem(it) }
    }

    override suspend fun markNewsItemAsRead(newsItemId: Int) {
        db.getEventsDao().markEventAsRead(newsItemId)
    }
}

