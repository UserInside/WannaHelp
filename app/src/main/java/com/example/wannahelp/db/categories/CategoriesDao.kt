package com.example.wannahelp.db.categories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface CategoriesDao {

    @Insert(onConflict = REPLACE)
    fun addCategory(categoriesEntity: CategoriesEntity)

    @Query("SELECT * FROM categories")
    fun getCategories(): List<CategoriesEntity>
}