package com.example.wannahelp.authScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class AuthViewModel : ViewModel() {
    var emailTextValue: MutableStateFlow<String> = MutableStateFlow<String>("")
    var passwordTextValue: MutableStateFlow<String> = MutableStateFlow<String>("")

    private val _isFieldsLengthSufficient =
        combine(emailTextValue, passwordTextValue) { email, pass ->
            email.length >= REQUIRED_FIELD_LENGTH && pass.length >= REQUIRED_FIELD_LENGTH
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = false,
        )

    val isFieldsLengthSufficient: StateFlow<Boolean> = _isFieldsLengthSufficient

    companion object {
        private const val REQUIRED_FIELD_LENGTH = 0
    }
}
