package com.example.wannahelp.di

import com.example.data.di.DataModule
import com.example.wannahelp.MainApp
import com.example.wannahelp.presentation.MainActivity
import com.example.wannahelp.presentation.newsScreen.NewsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface AppComponent {
    fun inject(app: MainApp)
    fun inject(activity: MainActivity)
    fun inject(viewModel: NewsViewModel)
}
