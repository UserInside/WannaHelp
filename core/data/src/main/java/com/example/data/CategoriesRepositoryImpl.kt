package com.example.data

import android.content.Context
import android.util.Log
import com.example.common.utils.extensions.parseToList
import com.example.data.db.categories.CategoriesDao
import com.example.data.db.mapCategoryDbEntityToCategoryItem
import com.example.domain.entities.CategoryDomainModel
import com.example.domain.repository.CategoriesRepository
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val context: Context,
    private val categoriesDao: CategoriesDao
) : CategoriesRepository {
    override suspend fun getCategories(): List<CategoryDomainModel> = try {
        val response = categoriesDao.getCategories()
        Log.i("DEMO", "categories from db") // для демонстрации
        response.map { mapCategoryDbEntityToCategoryItem(it) }
    } catch (e: Exception) {
        Log.i("DEMO", "categories from file + ${e.message} + ${e.cause}") // для демонстрации
        loadCategoriesListFromFile(context)
    }

    private fun loadCategoriesListFromFile(context: Context): List<CategoryDomainModel> {
        return Json.parseToList<CategoryDomainModel>(context, CATEGORIES_FILE_NAME)
    }

    companion object {
        private const val CATEGORIES_FILE_NAME = "categories.json"
    }
}