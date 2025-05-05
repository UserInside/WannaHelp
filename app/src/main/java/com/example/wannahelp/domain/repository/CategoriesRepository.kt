package com.example.wannahelp.domain.repository

import com.example.wannahelp.domain.entities.CategoryItem

interface CategoriesRepository {
    suspend fun getCategories(): List<CategoryItem>
}