package com.example.news.di

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.common.Feature
import com.example.data.di.DataModule
import com.example.data.storage.DatastoreStorageProvider
import com.example.data.storage.StorageProvider
import com.example.domain.di.DomainModule
import com.example.news.NewsNavigator
import com.example.news.NewsScreenFragment
import com.example.news.NewsViewModel
import com.example.news.newsFilterScreen.NewsFilterFragment
import com.example.news.newsFilterScreen.NewsFilterViewModel
import com.example.news.newsFilterScreen.NewsFilterViewModelFactory
import dagger.Component
import javax.inject.Singleton
import kotlin.properties.Delegates.notNull

@Feature
@Singleton
@Component(
    dependencies = [NewsDeps::class],
    modules = [DomainModule::class, DataModule::class,]
)
interface NewsComponent {
    fun inject(vm: NewsViewModel)
    fun inject(newsFilterViewModel: NewsFilterViewModel)
    fun inject(fragment: NewsScreenFragment)
    fun inject(fragment: NewsFilterFragment)
    fun inject(factory: NewsFilterViewModelFactory)
    fun inject(activity: Activity)

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

class NewsComponentViewModel(application: Application) : AndroidViewModel(application) {
    val newsComponent = DaggerNewsComponent.builder()
        .dataModule(DataModule(application.applicationContext))
        .domainModule(DomainModule())
        .deps(NewsDepsStore.deps)
        .build()
}