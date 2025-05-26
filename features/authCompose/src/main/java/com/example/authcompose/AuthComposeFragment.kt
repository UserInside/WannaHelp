package com.example.authcompose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import com.example.authcompose.di.AuthComposeComponentViewModel
import com.example.authcompose.navigation.AuthComposeNavigator
import com.example.common.compose.BaseComposeFragment
import com.example.common.utils.extensions.navigate
import javax.inject.Inject

class AuthComposeFragment : BaseComposeFragment() {

    @Inject
    lateinit var navigator: AuthComposeNavigator

    override fun onAttach(context: Context) {
        ViewModelProvider(this)[AuthComposeComponentViewModel::class].authComponent.inject(this)
        super.onAttach(context)
    }

    @Composable
    override fun ScreenContent() {
        AuthScreen(
            onBackClicked = { requireActivity().finishAffinity() },
            onNavigate = { navigate(navigator.toCategories) },
        )
    }
}