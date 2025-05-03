package com.example.wannahelp.presentation.profileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wannahelp.data.network.RetrofitClient
import com.example.wannahelp.domain.entities.FriendCardItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

class ProfileViewModel : ViewModel() {

    @Inject lateinit var retrofit: Retrofit

    private val _friendsList = MutableStateFlow<List<FriendCardItem>>(emptyList())
    val friendsList: StateFlow<List<FriendCardItem>> = _friendsList

    init {
        loadFriendsList()
    }

    private fun loadFriendsList() {
        viewModelScope.launch {
            val response = RetrofitClient(retrofit).apiService.getFriends()
            _friendsList.value = response
        }
    }
}
