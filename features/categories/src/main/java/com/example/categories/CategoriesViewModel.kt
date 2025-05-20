package com.example.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.CategoryDomainModel
import com.example.domain.interactors.CategoriesInteractor
import com.example.categories.di.CategoriesComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoriesViewModelFactory @Inject constructor(
    private val component: CategoriesComponent
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoriesViewModel(component) as T
    }
}

class CategoriesViewModel(component: CategoriesComponent) : ViewModel() {

    @Inject
    lateinit var interactor: CategoriesInteractor

    private val _screenState =
        MutableStateFlow<CategoriesScreenState>(CategoriesScreenState.Done(emptyList()))
    val screenState: StateFlow<CategoriesScreenState> = _screenState

    init {
        component.inject(this)
        viewModelScope.launch {
            loadCategoriesListFromDb()
        }
    }

    suspend fun loadCategoriesListFromDb() {
        _screenState.value = CategoriesScreenState.Progress
        delay(500) // для демонстрации
        val lts = interactor.getCategories()
        _screenState.value = CategoriesScreenState.Done(lts)
    }
}

sealed class CategoriesScreenState {
    object Progress : CategoriesScreenState()

    class Done(val categoryList: List<CategoryDomainModel>) : CategoriesScreenState()
}
