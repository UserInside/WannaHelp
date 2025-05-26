package com.example.authcompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

internal class AuthViewModel : ViewModel() {
    var state by mutableStateOf(AuthScreenState())

    fun onEvent(event: AuthScreenEvent) {
        when (event) {
            is AuthScreenEvent.UpdateEmailEvent -> {
                state = state.copy(email = event.email)
                updateButtonStatus()
            }

            is AuthScreenEvent.UpdatePasswordEvent -> {
                state = state.copy(password = event.password)
                updateButtonStatus()
            }
        }
    }

    private fun isEmailValid(email: String) : Boolean {
        val isValid = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$").containsMatchIn(email)
        return isValid
    }

    private fun updateButtonStatus() {
        this.state = state.copy(
            isButtonActive = state.password.length >= REQUIRED_FIELD_LENGTH && isEmailValid(state.email)
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
    data class UpdateEmailEvent(val email: String) : AuthScreenEvent()
    data class UpdatePasswordEvent(val password: String) : AuthScreenEvent()
}
