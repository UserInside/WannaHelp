package com.example.wannahelp.navigation

import com.example.authcompose.navigation.AuthComposeNavigator
import com.example.common.navigation.NavigationCommand
import com.example.wannahelp.R
import javax.inject.Inject

class AuthComposeNavigatorImpl
    @Inject
    constructor() : AuthComposeNavigator {
        override val toCategories: NavigationCommand =
            NavigationCommand(R.id.navigateToCategoriesScreen)
    }
