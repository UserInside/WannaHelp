package com.example.authcompose

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
        private set

//    private val _isFieldsLengthSufficient = combine(state.email, state.password) { email, pass ->
//        email.length >= REQUIRED_FIELD_LENGTH && pass.length >= REQUIRED_FIELD_LENGTH
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.Lazily,
//        initialValue = false,
//    )
//    val isFieldsLengthSufficient: StateFlow<Boolean> = _isFieldsLengthSufficient

    fun onEvent(event: AuthScreenEvent) {
        when (event) {
            is AuthScreenEvent.SetEmailEvent -> {
                this.state = state.copy(email = event.email)
                updateButtonStatus()
            }

            is AuthScreenEvent.SetPasswordEvent -> {
                this.state = state.copy(password = event.password)
                updateButtonStatus()
            }
        }
    }

    fun updateButtonStatus() {
        this.state = state.copy(
            isButtonActive = state.email.length >= REQUIRED_FIELD_LENGTH && state.password.length >= REQUIRED_FIELD_LENGTH
        )
    }

    companion object {
        private const val REQUIRED_FIELD_LENGTH = 0
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
