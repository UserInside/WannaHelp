package com.example.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModelFactory
@Inject
constructor(private val apiService: ApiService) : ViewModelProvider.Factory {
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
            val friendsList = apiService.getFriends().map {
                Mapper.mapFriendsFromDomainToUi(it)
            }
            _friendsList.value = friendsList
        }
    }
}
