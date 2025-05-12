package com.example.data.db

import com.example.data.db.categories.CategoriesEntity
import com.example.domain.entities.Category
import com.example.data.db.events.EventsEntity
import com.example.data.network.NewsApiResponseItem
import com.example.domain.entities.CategoryItem
import com.example.domain.entities.NewsItem

fun mapEventApiResponseItemToDbEntity(item: NewsApiResponseItem): EventsEntity {
    return EventsEntity(
        id = item.id,
        name = item.name,
        startDate = item.startDate,
        endDate = item.endDate,
        description = item.description,
        status = item.status,
        photos = item.photos,
        category = item.category,
        createdAt = item.createdAt,
        phone = item.phone,
        address = item.address,
        organization = item.organization,
    )
}

fun mapEventDbEntityToNewsItem(entity: EventsEntity): NewsItem {
    return NewsItem(
        id = entity.id.toInt(),
        imageRes = entity.photos[0],
        name = entity.name,
        description = entity.description,
        date = entity.createdAt.toString(),
        isRead = entity.isRead,
        category = Category.valueOf(entity.category.uppercase()),
    )
}

fun mapCategoryApiResponseItemToDbEntity(item: CategoryItem): CategoriesEntity {
    return CategoriesEntity(
        id = item.id.toInt(),
        nameEn = item.name_en,
        name = item.name,
        image = item.image,
    )
}

fun mapCategoryDbEntityToCategoryItem(entity: CategoriesEntity): CategoryItem {
    return CategoryItem(
        id = entity.id.toString(),
        name_en = entity.nameEn,
        name = entity.name,
        image = entity.image,
    )
}
