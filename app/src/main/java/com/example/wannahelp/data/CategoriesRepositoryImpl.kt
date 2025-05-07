package com.example.wannahelp.data

import android.content.Context
import android.util.Log
import com.example.wannahelp.common.extensions.parseToList
import com.example.wannahelp.data.db.categories.CategoriesDao
import com.example.wannahelp.data.db.mapCategoryDbEntityToCategoryItem
import com.example.wannahelp.domain.entities.CategoryItem
import com.example.wannahelp.domain.repository.CategoriesRepository
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val context: Context,
    private val categoriesDao: CategoriesDao
) : CategoriesRepository {
    override suspend fun getCategories(): List<CategoryItem> = try {
        val response = categoriesDao.getCategories()
        Log.i("DEMO", "categories from db") // для демонстрации
        response.map { mapCategoryDbEntityToCategoryItem(it) }
    } catch (e: Exception) {
        Log.i("DEMO", "categories from file + ${e.message} + ${e.cause}") // для демонстрации
        loadCategoriesListFromFile(context)
    }

    private fun loadCategoriesListFromFile(context: Context): List<CategoryItem> {
        return Json.parseToList<CategoryItem>(context, CATEGORIES_FILE_NAME)
    }

    companion object {
        private const val CATEGORIES_FILE_NAME = "categories.json"
    }
}