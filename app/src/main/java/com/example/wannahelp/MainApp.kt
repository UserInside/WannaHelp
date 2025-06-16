package com.example.wannahelp

import android.app.Application
import com.example.authcompose.di.AuthComposeComposeDepsStore
import com.example.data.db.AppDatabase
import com.example.data.di.DataModule
import com.example.newscompose.di.NewsComposeDepsStore
import com.example.profile.di.ProfileDepsStore
import com.example.wannahelp.di.AppComponent
import com.example.wannahelp.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class MainApp : Application() {
    @Inject
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        appComponent =
            DaggerAppComponent.builder()
                .dataModule(DataModule(this))
                .build()
        appComponent.inject(this)
        AuthComposeComposeDepsStore.deps = appComponent
        NewsComposeDepsStore.deps = appComponent
        ProfileDepsStore.deps = appComponent
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
