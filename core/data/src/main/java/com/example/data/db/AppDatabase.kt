package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.db.categories.CategoriesDao
import com.example.data.db.categories.CategoriesEntity
import com.example.data.db.events.EventsDao
import com.example.data.db.events.EventsEntity

@Database(entities = [CategoriesEntity::class, EventsEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getEventsDao(): EventsDao
    abstract fun getCategoriesDao(): CategoriesDao
}
