package com.example.wannahelp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.wannahelp.MainApp
import com.example.wannahelp.R
import com.example.wannahelp.data.db.mapCategoryApiResponseItemToDbEntity
import com.example.wannahelp.data.db.mapEventApiResponseItemToDbEntity
import com.example.wannahelp.data.network.ApiService
import com.example.wannahelp.presentation.newsScreen.NewsViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var badge: BadgeDrawable
    private val viewModel: NewsViewModel by viewModels()

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainApp.Companion.appComponent.inject(this) //todo потестить. с инжектом, без него, с аннотацией и без и т.п.
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
            val eventsResponse = apiService.getEvents()
            Log.i("DEMO", "eventsResponse received $eventsResponse") // для демонстрации

            eventsResponse.forEach {
                MainApp.Companion.database.getEventsDao().addEvent(
                    mapEventApiResponseItemToDbEntity(
                        it
                    )
                )
            }
        }
        lifecycleScope.launch {
            val categoriesResponse = apiService.getCategories()
            Log.i("DEMO", "categoriesResponse received $categoriesResponse") // для демонстрации

            categoriesResponse.forEach {
                MainApp.Companion.database.getCategoriesDao()
                    .addCategory(mapCategoryApiResponseItemToDbEntity(it))
            }
        }
    }
}