package com.example.wannahelp.wannaHelpScreen

import android.app.Application
import androidx.room.Room
import com.example.wannahelp.db.AppDatabase

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            context = applicationContext,
            klass = AppDatabase::class.java,
            name = "wanna_help_database",
        ).build()
    }

//    private fun fetchDataToDB() {
//        lifecycleScope.launch {
//            val eventsResponse = RetrofitClient.apiService.getEvents()
//            Log.i("DEMO", "eventsResponse received $eventsResponse") // для демонстрации
//
//            eventsResponse.forEach {
//                MainApp.database.getEventsDao().addEvent(mapEventApiResponseItemToDbEntity(it))
//            }
//        }
//        lifecycleScope.launch {
//            val categoriesResponse = RetrofitClient.apiService.getCategories()
//            Log.i("DEMO", "categoriesResponse received $categoriesResponse") // для демонстрации
//
//            categoriesResponse.forEach {
//                MainApp.database.getCategoriesDao()
//                    .addCategory(mapCategoryApiResponseItemToDbEntity(it))
//            }
//        }
//    }

    companion object {
        lateinit var database: AppDatabase
    }
}

