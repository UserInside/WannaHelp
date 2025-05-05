package com.example.wannahelp.domain.interactors

import com.example.wannahelp.domain.entities.CategoryItem
import com.example.wannahelp.domain.repository.CategoriesRepository

class CategoriesInteractor(
    private val repository: CategoriesRepository
) {
    suspend fun getCategories(): List<CategoryItem> {
        return repository.getCategories()
    }
}