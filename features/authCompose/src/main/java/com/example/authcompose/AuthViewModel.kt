package com.example.authcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

internal class AuthViewModel: ViewModel() {
    private val _emailTextValue: MutableStateFlow<String> = MutableStateFlow<String>("")
    val emailTextValue = _emailTextValue as StateFlow<String>

    private val _passwordTextValue: MutableStateFlow<String> = MutableStateFlow<String>("")
    val passwordTextValue = _passwordTextValue as StateFlow<String>

    private val _isFieldsLengthSufficient =
        combine(emailTextValue, passwordTextValue) { email, pass ->
            email.length >= REQUIRED_FIELD_LENGTH && pass.length >= REQUIRED_FIELD_LENGTH
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = false,
        )
    val isFieldsLengthSufficient: StateFlow<Boolean> = _isFieldsLengthSufficient

    fun setEmailValue(value: String) {
        _emailTextValue.update {
            value
        }
    }

    fun setPasswordValue(value: String) {
        _passwordTextValue.value = value
    }

    companion object {
        private const val REQUIRED_FIELD_LENGTH = 0
    }
}
