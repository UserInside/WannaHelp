package com.example.wannahelp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.wannahelp.db.mapCategoryApiResponseItemToDbEntity
import com.example.wannahelp.db.mapEventApiResponseItemToDbEntity
import com.example.wannahelp.network.RetrofitClient
import com.example.wannahelp.newsScreen.NewsViewModel
import com.example.wannahelp.MainApp
import com.example.wannahelp.di.AppComponent
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var badge: BadgeDrawable
    private val viewModel: NewsViewModel by viewModels()

    @Inject
    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainApp.appComponent.inject(this) //todo потестить. с инжектом, без него, с аннотацией и без и т.п.
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
            val eventsResponse = RetrofitClient(retrofit).apiService.getEvents()
            Log.i("DEMO", "eventsResponse received $eventsResponse") // для демонстрации

            eventsResponse.forEach {
                MainApp.database.getEventsDao().addEvent(mapEventApiResponseItemToDbEntity(it))
            }
        }
        lifecycleScope.launch {
            val categoriesResponse = RetrofitClient(retrofit).apiService.getCategories()
            Log.i("DEMO", "categoriesResponse received $categoriesResponse") // для демонстрации

            categoriesResponse.forEach {
                MainApp.database.getCategoriesDao()
                    .addCategory(mapCategoryApiResponseItemToDbEntity(it))
            }
        }
    }
}
