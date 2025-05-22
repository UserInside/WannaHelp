package com.example.authcompose

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

internal class AuthViewModel : ViewModel() {
    var state by mutableStateOf(AuthScreenState())

    fun onEvent(event: AuthScreenEvent) {
        when (event) {
            is AuthScreenEvent.SetEmailEvent -> {
                state = state.copy(email = event.email)
                Log.e("AUVM", "email - ${state.email}")
                updateButtonStatus()
            }

            is AuthScreenEvent.SetPasswordEvent -> {
                state = state.copy(password = event.password)
                updateButtonStatus()
            }
        }
    }

    fun updateButtonStatus() {
        Log.e("s", "stateE - ${state.email.length}")
        Log.e("s", "stateP - ${state.password.length}")
        Log.e("s", "button - ${state.isButtonActive}")
        this.state = state.copy(
            isButtonActive = state.email.length >= REQUIRED_FIELD_LENGTH && state.password.length >= REQUIRED_FIELD_LENGTH
        )
    }

    companion object {
        private const val REQUIRED_FIELD_LENGTH = 3
    }
}

data class AuthScreenState(
    val email: String = "",
    val password: String = "",
    val isButtonActive: Boolean = false,
)

sealed class AuthScreenEvent() {
    data class SetEmailEvent(val email: String) : AuthScreenEvent()
    data class SetPasswordEvent(val password: String) : AuthScreenEvent()
}
