package com.example.wannahelp.di

import com.example.wannahelp.MainApp
import com.example.wannahelp.presentation.MainActivity
import com.example.wannahelp.presentation.newsScreen.NewsViewModel
import com.example.wannahelp.presentation.profileScreen.ProfileScreenFragment
import com.example.wannahelp.presentation.wannaHelpScreen.WannaHelpViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: MainApp)
    fun inject(activity: MainActivity)
    fun inject(fragment: ProfileScreenFragment)
    fun inject(viewModel: WannaHelpViewModel)
    fun inject(viewModel: NewsViewModel)
}
