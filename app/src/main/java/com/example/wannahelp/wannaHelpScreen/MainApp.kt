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

    companion object {
        lateinit var database: AppDatabase
    }
}