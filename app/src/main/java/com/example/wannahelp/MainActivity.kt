package com.example.wannahelp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var badge: BadgeDrawable
//    private val viewModel: NewsViewModel by viewModels()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        bottomNavigationView.setupWithNavController(navController)

        badge = bottomNavigationView.getOrCreateBadge(R.id.newsScreenFragment)
        badge.apply {
            backgroundColor = resources.getColor(R.color.leaf, null)
            badgeTextColor = resources.getColor(R.color.white, null)
            maxCharacterCount = 3
        }
//        lifecycleScope.launch {
//            viewModel.unreadMsgCountStateFlow.collect { count ->
//                updateNewsBadge(count)
//            }
//        }
    }

    private fun updateNewsBadge(count: Int) {
        if (count > 0) {
            badge.number = count
            badge.isVisible = true
        } else {
            badge.isVisible = false
        }
    }
}
