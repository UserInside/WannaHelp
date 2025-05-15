package com.example.profile.di

import androidx.lifecycle.ViewModelProvider
import com.example.data.di.DataModule
import com.example.profile.ProfileScreenFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ProfileModule::class, DataModule::class])
interface ProfileComponent {
    fun inject(fragment: ProfileScreenFragment)

//    // Если нужно инжектить в другие фрагменты/активности
//    fun viewModelFactory(): ViewModelProvider.Factory
}