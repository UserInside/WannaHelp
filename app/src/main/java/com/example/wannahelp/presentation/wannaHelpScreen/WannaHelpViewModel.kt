package com.example.wannahelp.presentation.wannaHelpScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wannahelp.MainApp
import com.example.wannahelp.domain.entities.CategoryItem
import com.example.wannahelp.domain.interactors.CategoriesInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WannaHelpViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var interactor: CategoriesInteractor

    private val _screenState =
        MutableStateFlow<WannaHelpScreenState>(WannaHelpScreenState.Done(emptyList()))
    val screenState: StateFlow<WannaHelpScreenState> = _screenState

    init {
        MainApp.appComponent.inject(this)
        viewModelScope.launch {
            loadCategoriesListFromDb()
        }
    }

    suspend fun loadCategoriesListFromDb() {
        _screenState.value = WannaHelpScreenState.Progress
        delay(500) // для демонстрации
        val lts = interactor.getCategories()
        _screenState.value = WannaHelpScreenState.Done(lts)
    }
}

sealed class WannaHelpScreenState {
    object Progress : WannaHelpScreenState()

    class Done(val categoryList: List<CategoryItem>) : WannaHelpScreenState()
}
