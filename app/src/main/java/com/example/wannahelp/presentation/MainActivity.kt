package com.example.wannahelp.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.common.FragmentNavigationListener
import com.example.wannahelp.MainApp
import com.example.data.db.AppDatabase
import com.example.data.db.mapCategoryApiResponseItemToDbEntity
import com.example.data.db.mapEventApiResponseItemToDbEntity
import com.example.data.network.ApiService
import com.example.wannahelp.R
import com.example.wannahelp.presentation.newsScreen.NewsViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentNavigationListener {
    private lateinit var badge: BadgeDrawable
    private lateinit var navController: NavController
    private val viewModel: NewsViewModel by viewModels()

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainApp.Companion.appComponent.inject(this) // todo потестить. с инжектом, без него, с аннотацией и без и т.п.
        fetchDataToDB()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        val currentFragment = navController.currentDestination?.label

        // Определяем тип фрагмента и изменяем поведение активити
        when (currentFragment) {
            "AuthFragment" -> {
                bottomNavigationView.visibility = View.GONE
            }
            else -> {
                bottomNavigationView.visibility = View.VISIBLE
            }
        }

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

    override fun navigateTo() {
        navController.navigate(R.id.navigateToWannaHelpScreenFragment)
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
                db.getEventsDao().addEvent(
                    mapEventApiResponseItemToDbEntity(
                        it,
                    ),
                )
            }
        }
        lifecycleScope.launch {
            val categoriesResponse = apiService.getCategories()
            Log.i("DEMO", "categoriesResponse received $categoriesResponse") // для демонстрации

            categoriesResponse.forEach {
                db.getCategoriesDao()
                    .addCategory(mapCategoryApiResponseItemToDbEntity(it))
            }
        }
    }
}
