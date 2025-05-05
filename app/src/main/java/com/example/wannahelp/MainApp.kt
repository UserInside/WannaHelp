package com.example.wannahelp

import android.app.Application
import com.example.wannahelp.data.db.AppDatabase
import com.example.wannahelp.di.AppComponent
import com.example.wannahelp.di.AppModule
import com.example.wannahelp.di.DaggerAppComponent
import javax.inject.Inject

class MainApp : Application() {
    @Inject
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        appComponent.inject(this)
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
