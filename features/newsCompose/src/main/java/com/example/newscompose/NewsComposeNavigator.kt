package com.example.newscompose

import com.example.common.navigation.NavigationCommand

interface NewsComposeNavigator {
    val toEvent: NavigationCommand
    val toNewsFilter: NavigationCommand
}