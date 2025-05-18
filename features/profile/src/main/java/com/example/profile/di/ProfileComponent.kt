package com.example.profile.di

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.data.di.DataModule
import com.example.profile.ProfileNavigator
import com.example.profile.ProfileScreenFragment
import dagger.Component
import javax.inject.Singleton
import kotlin.properties.Delegates.notNull

@Singleton
@Component(dependencies = [ProfileDeps::class], modules = [ProfileModule::class, DataModule::class])
interface ProfileComponent {
    fun inject(fragment: ProfileScreenFragment)

    @Component.Builder
    interface Builder {
        fun deps(deps: ProfileDeps): Builder
        fun profileModule(module: ProfileModule): Builder
        fun dataModule(module: DataModule): Builder
        fun build(): ProfileComponent
    }
}

interface ProfileDeps {
    val profileNavigator: ProfileNavigator
}

interface ProfileDepsProvider {
    var deps: ProfileDeps

    companion object : ProfileDepsProvider by ProfileDepsStore
}

object ProfileDepsStore : ProfileDepsProvider {
    override var deps: ProfileDeps by notNull()
}

class ProfileComponentViewModel(application: Application) : AndroidViewModel(application) {
    val profileComponent =
        DaggerProfileComponent.builder()
            .profileModule(ProfileModule())
            .dataModule(DataModule(application.applicationContext))
            .deps(ProfileDepsStore.deps)
            .build()
}

