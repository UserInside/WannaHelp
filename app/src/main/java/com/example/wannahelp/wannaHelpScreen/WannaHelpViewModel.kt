package com.example.wannahelp.wannaHelpScreen

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wannahelp.common.extentions.parseToList
import com.example.wannahelp.network.RetrofitClient
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class WannaHelpViewModel(application: Application) : AndroidViewModel(application) {
    private val _screenState =
        MutableStateFlow<WannaHelpScreenState>(WannaHelpScreenState.Done(emptyList()))
    val screenState: StateFlow<WannaHelpScreenState> = _screenState

    init {
        viewModelScope.launch {
            loadCategoriesListFromAPI(application.applicationContext)
        }
    }

    suspend fun loadCategoriesListFromAPI(context: Context) {
        val exHandler =
            CoroutineExceptionHandler { _, throwable ->
                _screenState.value = WannaHelpScreenState.Done(loadCategoriesListFromFile(context))
                Log.i("DEMO", "categories from file + ${throwable.message} + ${throwable.cause}") // для демонстрации
            }
        _screenState.value = WannaHelpScreenState.Progress
        delay(500) // для демонстрации
        viewModelScope.launch(exHandler) {
            val response = RetrofitClient.apiService.getCategories()
            _screenState.value = WannaHelpScreenState.Done(response.values.toList())
            Log.i("DEMO", "categories from api") // для демонстрации
        }
    }

    private fun loadCategoriesListFromFile(context: Context): List<CategoryItem> {
        return Json.parseToList<CategoryItem>(context, CATEGORIES_FILE_NAME)
    }

    companion object {
        private const val CATEGORIES_FILE_NAME = "categories.json"
    }
}

sealed class WannaHelpScreenState {
    object Progress : WannaHelpScreenState()

    class Done(val categoryList: List<CategoryItem>) : WannaHelpScreenState()
}
