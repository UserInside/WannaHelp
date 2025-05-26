package com.example.authcompose.di

import androidx.lifecycle.ViewModel
import com.example.authcompose.AuthComposeFragment
import com.example.authcompose.navigation.AuthComposeNavigator
import com.example.common.Feature
import dagger.Component
import kotlin.properties.Delegates.notNull

@[Feature Component(dependencies = [AuthComposeDeps::class])]
internal interface AuthComposeComponent {
    fun inject(fragment: AuthComposeFragment)

    @Component.Builder
    interface Builder {
        fun deps(authComposeDeps: AuthComposeDeps): Builder
        fun build(): AuthComposeComponent
    }
}

interface AuthComposeDeps {
    val authComposeNavigator: AuthComposeNavigator
}

interface AuthComposeDepsProvider {
    val deps: AuthComposeDeps
    companion object : AuthComposeDepsProvider by AuthComposeComposeDepsStore
}

object AuthComposeComposeDepsStore : AuthComposeDepsProvider {
    override var deps: AuthComposeDeps by notNull()
}

internal class AuthComposeComponentViewModel: ViewModel() {
    val authComponent =
        DaggerAuthComposeComponent.builder()
            .deps(AuthComposeComposeDepsStore.deps)
            .build()
}