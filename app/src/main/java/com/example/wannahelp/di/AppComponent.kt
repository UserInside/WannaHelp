package com.example.wannahelp.di

import com.example.authcompose.di.AuthComposeDeps
import com.example.data.di.DataModule
import com.example.newscompose.di.NewsComposeDeps
import com.example.profile.di.ProfileDeps
import com.example.wannahelp.MainApp
import com.example.wannahelp.navigation.AuthComposeNavigatorImpl
import com.example.wannahelp.navigation.NewsComposeNavigatorImpl
import com.example.wannahelp.navigation.ProfileNavigatorImpl
import com.example.wannahelp.presentation.MainActivity
import dagger.Component
import javax.inject.Scope
import javax.inject.Singleton

@Singleton
@AppScope
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent :
    AuthComposeDeps,
    NewsComposeDeps,
    ProfileDeps {
    override val authComposeNavigator: AuthComposeNavigatorImpl
    override val newsComposeNavigator: NewsComposeNavigatorImpl
    override val profileNavigator: ProfileNavigatorImpl

    fun inject(app: MainApp)

    fun inject(activity: MainActivity)
}

@Scope
annotation class AppScope
