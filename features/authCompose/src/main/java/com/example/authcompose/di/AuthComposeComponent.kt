package com.example.authcompose.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.authcompose.AuthorizationNavigator
import com.example.authcompose.AuthComposeFragment
import com.example.common.Feature
import dagger.Component
import kotlin.properties.Delegates.notNull

@[Feature Component(dependencies = [AuthDeps::class])]
internal interface AuthComponent {
    fun inject(fragment: AuthComposeFragment)

    @Component.Builder
    interface Builder {
        fun deps(authDeps: AuthDeps): Builder
        fun build(): AuthComponent
    }
}

interface AuthDeps {
    val authNavigator: AuthorizationNavigator
}

interface AuthDepsProvider {
    val deps: AuthDeps
    companion object : AuthDepsProvider by AuthDepsStore
}

object AuthDepsStore : AuthDepsProvider {
    override var deps: AuthDeps by notNull()
}

internal class AuthComponentViewModel: ViewModel() {
    val authComponent =
        DaggerAuthComposeComponent.builder()
            .deps(AuthDepsStore.deps)
            .build()
}