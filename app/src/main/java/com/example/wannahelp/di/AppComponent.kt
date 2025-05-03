package com.example.wannahelp.di

import com.example.wannahelp.presentation.MainActivity
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun getRetrofit(): Retrofit
    fun inject(activity: MainActivity)
}