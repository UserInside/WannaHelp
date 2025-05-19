package com.example.wannahelp.navigation

import com.example.common.navigation.NavigationCommand
import com.example.profile.ProfileNavigator
import com.example.wannahelp.R
import javax.inject.Inject

class ProfileNavigatorImpl
    @Inject
    constructor() : ProfileNavigator {
        override val toProfileEditing = NavigationCommand(R.id.navigateToEditProfileScreen)
    }
