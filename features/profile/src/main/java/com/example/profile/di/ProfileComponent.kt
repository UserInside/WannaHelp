package com.example.profile.di

import com.example.data.di.DataModule
import com.example.profile.ProfileScreenFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ProfileModule::class, DataModule::class])
interface ProfileComponent {
    fun inject(fragment: ProfileScreenFragment)
}