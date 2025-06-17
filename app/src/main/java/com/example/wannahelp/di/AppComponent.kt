package com.example.wannahelp.di

import com.example.authcompose.di.AuthComposeDeps
import com.example.data.di.ApplicationComponent
import com.example.data.di.DataModule
import com.example.news.NewsNavigator
import com.example.news.di.NewsDeps
import com.example.newscompose.di.NewsComposeDeps
import com.example.profile.di.ProfileDeps
import com.example.wannahelp.MainApp
import com.example.wannahelp.navigation.AuthComposeNavigatorImpl
import com.example.wannahelp.navigation.NewsComposeNavigatorImpl
import com.example.wannahelp.navigation.NewsNavigatorImpl
import com.example.wannahelp.navigation.ProfileNavigatorImpl
import com.example.wannahelp.presentation.MainActivity
import dagger.Component
import javax.inject.Scope
import javax.inject.Singleton

@Singleton
@AppScope
@Component(modules = [DataModule::class, AppModule::class])
interface AppComponent :
    ApplicationComponent,
    AuthComposeDeps,
    NewsComposeDeps,
    NewsDeps,
    ProfileDeps {
    override val authComposeNavigator: AuthComposeNavigatorImpl
    override val newsComposeNavigator: NewsComposeNavigatorImpl
    override val newsNavigator: NewsNavigatorImpl
    override val profileNavigator: ProfileNavigatorImpl

    fun inject(app: MainApp)
    fun inject(activity: MainActivity)
}

@Scope
annotation class AppScope
