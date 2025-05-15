package com.example.domain.repository

import com.example.domain.entities.CategoryDomainModel

interface CategoriesRepository {
    suspend fun getCategories(): List<CategoryDomainModel>
}