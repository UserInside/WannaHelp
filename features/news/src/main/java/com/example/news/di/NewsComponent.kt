package com.example.news.di

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.application
import com.example.common.Feature
import com.example.data.di.DataModule
import com.example.domain.di.DomainModule
import com.example.domain.interactors.NewsInteractor
import com.example.news.NewsNavigator
import com.example.news.NewsScreenFragment
import com.example.news.NewsViewModel
import dagger.Component
import javax.inject.Singleton
import kotlin.properties.Delegates.notNull

@Feature
@Singleton
@Component(dependencies = [NewsDeps::class], modules = [DomainModule::class, DataModule::class])
interface NewsComponent {
    fun inject(vm: NewsViewModel)
    fun inject(fragment: NewsScreenFragment)

    @Component.Builder
    interface Builder {
        fun dataModule(module: DataModule): Builder
        fun domainModule(module: DomainModule): Builder
        fun deps(newsDeps: NewsDeps): Builder
        fun build(): NewsComponent
    }
}

interface NewsDeps {
    val newsNavigator: NewsNavigator
}

interface NewsDepsProvider {
    val deps: NewsDeps

    companion object : NewsDepsProvider by NewsDepsStore
}

object NewsDepsStore : NewsDepsProvider {
    override var deps: NewsDeps by notNull()
}

internal class NewsComponentViewModel(application: Application): AndroidViewModel(application) {
    val newsComponent = DaggerNewsComponent.builder()
        .dataModule(DataModule(application.applicationContext))
        .domainModule(DomainModule())
        .deps(NewsDepsStore.deps)
        .build()
}