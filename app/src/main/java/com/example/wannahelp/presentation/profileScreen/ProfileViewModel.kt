package com.example.wannahelp.presentation.profileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wannahelp.data.network.ApiService
import com.example.wannahelp.domain.entities.FriendCardItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModelFactory @Inject constructor(private val apiService: ApiService): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(apiService) as T
    }
}

class ProfileViewModel(private val apiService: ApiService) : ViewModel() {

    private val _friendsList = MutableStateFlow<List<FriendCardItem>>(emptyList())
    val friendsList: StateFlow<List<FriendCardItem>> = _friendsList

    init {
        loadFriendsList()
    }

    private fun loadFriendsList() {
        viewModelScope.launch {
            val response = apiService.getFriends()
            _friendsList.value = response
        }
    }
}
