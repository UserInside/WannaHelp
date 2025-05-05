package com.example.wannahelp.data.db.categories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface CategoriesDao {
    @Insert(onConflict = REPLACE)
    suspend fun addCategory(categoriesEntity: CategoriesEntity)

    @Query("SELECT * FROM categories")
    suspend fun getCategories(): List<CategoriesEntity>
}
