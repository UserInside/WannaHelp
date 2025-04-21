package com.example.wannahelp

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.wannahelp.db.mapCategoryApiResponseItemToDbEntity
import com.example.wannahelp.db.mapEventApiResponseItemToDbEntity
import com.example.wannahelp.network.RetrofitClient
import com.example.wannahelp.newsScreen.NewsViewModel
import com.example.wannahelp.newsScreen.NewsViewModelFactory
import com.example.wannahelp.wannaHelpScreen.MainApp
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var badge: BadgeDrawable
    private lateinit var viewModel: NewsViewModel

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = NewsViewModelFactory(applicationContext as Application, MainApp.database.getEventsDao()).create(NewsViewModel::class.java)
//        viewModel = ViewModelProvider(this, NewsViewModelFactory(applicationContext as Application, MainApp.database.getEventsDao()))[NewsViewModel::class.java]
        setContentView(R.layout.activity_main)
        fetchDataToDB()

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
        lifecycleScope.launch {
            viewModel.unreadMsgCountStateFlow.collect { count ->
                updateNewsBadge(count)
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
            val eventsResponse = RetrofitClient.apiService.getEvents()
            Log.i("DEMO", "eventsResponse received $eventsResponse") // для демонстрации

            eventsResponse.forEach {
                MainApp.database.getEventsDao().addEvent(mapEventApiResponseItemToDbEntity(it))
            }
        }
        lifecycleScope.launch {
            val categoriesResponse = RetrofitClient.apiService.getCategories()
            Log.i("DEMO", "categoriesResponse received $categoriesResponse") // для демонстрации

            categoriesResponse.forEach {
                MainApp.database.getCategoriesDao()
                    .addCategory(mapCategoryApiResponseItemToDbEntity(it))
            }
        }
    }
}


