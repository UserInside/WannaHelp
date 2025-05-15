package com.example.domain.interactors

import com.example.domain.entities.CategoryDomainModel
import com.example.domain.repository.CategoriesRepository

class CategoriesInteractor(
    private val repository: CategoriesRepository
) {
    suspend fun getCategories(): List<CategoryDomainModel> {
        return repository.getCategories()
    }
}