package com.example.news

import com.example.common.navigation.NavigationCommand

interface NewsNavigator {
    val toEvent: NavigationCommand
    val toNewsFilter: NavigationCommand
}