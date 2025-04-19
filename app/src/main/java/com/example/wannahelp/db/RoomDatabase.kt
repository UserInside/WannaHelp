package com.example.wannahelp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wannahelp.db.categories.CategoriesDao
import com.example.wannahelp.db.categories.CategoriesEntity
import com.example.wannahelp.db.events.EventsDao
import com.example.wannahelp.db.events.EventsEntity

@Database(entities = [CategoriesEntity::class, EventsEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getEventsDao(): EventsDao
    abstract fun getCategoriesDao(): CategoriesDao
}