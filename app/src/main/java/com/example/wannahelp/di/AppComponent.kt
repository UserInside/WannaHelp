package com.example.wannahelp.di

import com.example.authorization.di.AuthDeps
import com.example.data.di.DataModule
import com.example.domain.interactors.NewsInteractor
import com.example.news.di.NewsDeps
import com.example.profile.ProfileNavigator
import com.example.profile.di.ProfileDeps
import com.example.wannahelp.MainApp
import com.example.wannahelp.navigation.AuthorizationNavigatorImpl
import com.example.wannahelp.navigation.NewsNavigatorImpl
import com.example.wannahelp.navigation.ProfileNavigatorImpl
import com.example.wannahelp.presentation.MainActivity
import dagger.Component
import javax.inject.Scope
import javax.inject.Singleton

@Singleton
@AppScope
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent : AuthDeps, NewsDeps, ProfileDeps {
    override val authNavigator: AuthorizationNavigatorImpl
    override val newsNavigator: NewsNavigatorImpl
    override val profileNavigator: ProfileNavigatorImpl

    fun inject(app: MainApp)
    fun inject(activity: MainActivity)

}

@Scope
annotation class AppScope
