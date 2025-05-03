package com.example.wannahelp

import android.app.Application
import androidx.room.Room
import com.example.wannahelp.data.db.AppDatabase
import com.example.wannahelp.di.AppComponent
import com.example.wannahelp.di.AppModule
import com.example.wannahelp.di.DaggerAppComponent

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().appModule(AppModule(applicationContext)).build()

        database = Room.databaseBuilder(
            context = applicationContext,
            klass = AppDatabase::class.java,
            name = "wanna_help_database",
        ).build()
    }

    companion object {
        lateinit var database: AppDatabase
        lateinit var appComponent: AppComponent
    }
}