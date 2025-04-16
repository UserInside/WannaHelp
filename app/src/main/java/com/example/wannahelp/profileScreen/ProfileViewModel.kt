package com.example.wannahelp.profileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wannahelp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _friendsList = MutableStateFlow<List<FriendCard>>(emptyList())
    val friendsList: StateFlow<List<FriendCard>> = _friendsList


    init {
        loadFriendsList()
    }

    fun loadFriendsList() {
        viewModelScope.launch {
            val response = RetrofitClient.apiService.getFriends()
            _friendsList.value = response.values.toList()
        }
    }
}