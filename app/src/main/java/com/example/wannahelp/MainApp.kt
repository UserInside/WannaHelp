package com.example.wannahelp

import android.app.Application
import com.example.authorization.di.AuthDepsStore
import com.example.data.db.AppDatabase
import com.example.data.di.DataModule
import com.example.news.di.NewsDepsStore
import com.example.profile.di.ProfileDepsStore
import com.example.wannahelp.di.AppComponent
import com.example.wannahelp.di.DaggerAppComponent
import javax.inject.Inject

class MainApp : Application() {
    @Inject
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        appComponent =
            DaggerAppComponent.builder()
                .dataModule(DataModule(this))
                .build()
        appComponent.inject(this)
        AuthDepsStore.deps = appComponent
        NewsDepsStore.deps = appComponent
        ProfileDepsStore.deps = appComponent
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
