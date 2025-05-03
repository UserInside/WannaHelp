package com.example.wannahelp.di

import com.example.wannahelp.presentation.MainActivity
import com.example.wannahelp.presentation.profileScreen.ProfileScreenFragment
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: ProfileScreenFragment)
}