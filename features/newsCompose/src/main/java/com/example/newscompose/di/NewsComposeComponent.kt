package com.example.newscompose.di

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.common.Feature
import com.example.data.di.DataModule
import com.example.domain.di.DomainModule
import com.example.newscompose.NewsComposeNavigator
import com.example.newscompose.NewsComposeFragment
import com.example.newscompose.NewsViewModel
import dagger.Component
import javax.inject.Singleton
import kotlin.properties.Delegates.notNull

@Feature
@Singleton
@Component(dependencies = [NewsComposeDeps::class], modules = [DomainModule::class, DataModule::class])
interface NewsComposeComponent {
    fun inject(vm: NewsViewModel)
    fun inject(fragment: NewsComposeFragment)
    fun inject(activity: Activity)

    @Component.Builder
    interface Builder {
        fun dataModule(module: DataModule): Builder
        fun domainModule(module: DomainModule): Builder
        fun deps(newsDeps: NewsComposeDeps): Builder
        fun build(): NewsComposeComponent
    }
}

interface NewsComposeDeps {
    val newsComposeNavigator: NewsComposeNavigator
}

interface NewsComposeDepsProvider {
    val deps: NewsComposeDeps

    companion object : NewsComposeDepsProvider by NewsComposeDepsStore
}

object NewsComposeDepsStore : NewsComposeDepsProvider {
    override var deps: NewsComposeDeps by notNull()
}

class NewsComposeComponentViewModel(application: Application): AndroidViewModel(application) {
    val newsComposeComponent = DaggerNewsComposeComponent.builder()
        .dataModule(DataModule(application.applicationContext))
        .domainModule(DomainModule())
        .deps(NewsComposeDepsStore.deps)
        .build()
}