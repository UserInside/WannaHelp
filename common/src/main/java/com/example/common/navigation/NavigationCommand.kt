package com.example.common.navigation

import android.os.Bundle

data class NavigationCommand(
    val action: Int,
    var args: Bundle? = null,
)