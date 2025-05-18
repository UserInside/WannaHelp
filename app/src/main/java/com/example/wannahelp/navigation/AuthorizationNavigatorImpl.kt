package com.example.wannahelp.navigation


import com.example.authorization.AuthorizationNavigator
import com.example.common.navigation.NavigationCommand
import com.example.wannahelp.R
import javax.inject.Inject

class AuthorizationNavigatorImpl @Inject constructor(): AuthorizationNavigator {
    override val toCategories: NavigationCommand = NavigationCommand(R.id.navigateToCategoriesScreen)
}