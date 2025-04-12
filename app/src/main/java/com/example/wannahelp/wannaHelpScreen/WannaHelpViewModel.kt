package com.example.wannahelp.wannaHelpScreen

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wannahelp.common.extentions.parseToList
import com.example.wannahelp.network.RetrofitClient
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
//            loadNewsWithProgress(application.applicationContext)
            loadCategoriesListFromAPI()
        }
    }

    private suspend fun loadNewsWithProgress(context: Context) {
        for (i in 0..100 step 9) {
            delay(50)
            _screenState.value = WannaHelpScreenState.Progress(i)
        }
        _screenState.value = WannaHelpScreenState.Done(loadCategoriesListFromFile(context))
    }


    fun loadCategoriesListFromAPI() {
        _screenState.value = WannaHelpScreenState.Progress(50)
        viewModelScope.launch {
            val response = RetrofitClient.apiService.getCategories()
            _screenState.value = WannaHelpScreenState.Done(response.values.toList())
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
    class Progress(val progress: Int) : WannaHelpScreenState()
    class Done(val categoryList: List<CategoryItem>) : WannaHelpScreenState()
}
