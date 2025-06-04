package com.example.wannahelp.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.data.db.AppDatabase
import com.example.data.network.ApiService
import com.example.wannahelp.MainApp
import com.example.wannahelp.R
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var badge: BadgeDrawable
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navController: NavController

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainApp.Companion.appComponent.inject(this)
        fetchDataToDB()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.authComposeFragment -> bottomNavView.visibility = View.GONE
                R.id.eventDetailsScreenFragment -> bottomNavView.visibility = View.GONE
                else -> bottomNavView.visibility = View.VISIBLE
            }
        }

        bottomNavView.setupWithNavController(navController)

        badge = bottomNavView.getOrCreateBadge(R.id.newsComposeFragment)
        badge.apply {
            backgroundColor = resources.getColor(R.color.leaf, theme)
            badgeTextColor = resources.getColor(R.color.white, null)
            maxCharacterCount = 3
        }
        lifecycleScope.launch {
//            viewModel.unreadMsgCountStateFlow.collect { count ->
//                updateNewsBadge(count)
//            }
        }

        if (intent?.action == Intent.ACTION_VIEW) {
            val eventId = intent.data?.lastPathSegment?.toIntOrNull()
            if (eventId != null) {
                val navHostFragment =
                    supportFragmentManager
                        .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

                navHostFragment.navController.navigate(
                    R.id.eventDetailsScreenFragment,
                    Bundle().apply { putInt("eventId", eventId) },
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.action == Intent.ACTION_VIEW) {
            val navHostFragment =
                supportFragmentManager
                    .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController

            val deepLinkUri = intent.data
            if (deepLinkUri != null) {
                val request = NavDeepLinkRequest.Builder.fromUri(deepLinkUri).build()
                navController.handleDeepLink(request)
            }
        }
    }

    private fun updateNewsBadge(count: Int) {
        if (count > 0) {
            badge.number = count
            badge.isVisible = true
        } else {
            badge.isVisible = false
        }
    }

    private fun fetchDataToDB() {
        lifecycleScope.launch {
            val eventsResponse = apiService.getEvents()
            Log.i("DEMO", "eventsResponse received $eventsResponse") // для демонстрации

            eventsResponse.forEach {
                db.getEventsDao().addEvent(it)
            }
        }
        lifecycleScope.launch {
            val categoriesResponse = apiService.getCategories()
            Log.i("DEMO", "categoriesResponse received $categoriesResponse") // для демонстрации

            categoriesResponse.forEach {
                db.getCategoriesDao()
                    .addCategory(it)
            }
        }
    }
}
