package com.example.wannahelp.wannaHelpScreen

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wannahelp.common.extentions.parseToList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class WannaHelpViewModel(application: Application) : AndroidViewModel(application) {
    private val _wannaHelpScreenStateFlow =
        MutableStateFlow<WannaHelpScreenState>(WannaHelpScreenState.Done(emptyList()))
    val wannaHelpScreenStateFlow: StateFlow<WannaHelpScreenState> = _wannaHelpScreenStateFlow

    init {
        viewModelScope.launch { loadNewsWithProgress(application.applicationContext) }
    }

    private suspend fun loadNewsWithProgress(context: Context) {
        for (i in 0..100 step 9) {
            delay(50)
            _wannaHelpScreenStateFlow.value = WannaHelpScreenState.Progress(i)
        }
        _wannaHelpScreenStateFlow.value = WannaHelpScreenState.Done(loadCategoriesList(context))
    }

    private fun loadCategoriesList(context: Context): List<CategoryItem> {
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
