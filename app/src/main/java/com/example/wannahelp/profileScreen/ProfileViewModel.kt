package com.example.wannahelp.profileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wannahelp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

class ProfileViewModel : ViewModel() {

    @Inject lateinit var retrofit: Retrofit

    private val _friendsList = MutableStateFlow<List<FriendCard>>(emptyList())
    val friendsList: StateFlow<List<FriendCard>> = _friendsList

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
