package com.example.registrationapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrationapp.events.SignInEvent
import com.example.registrationapp.repo.abstraction.AuthRepo
import com.example.registrationapp.states.AuthState
import com.example.registrationapp.states.SignInFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {
    private val _signInStateFlow = MutableStateFlow(SignInFragmentState())
    val signInStateFlow = _signInStateFlow.asStateFlow()

    private val _authorizedSharedFlow = MutableSharedFlow<AuthState>(replay = 1)
    val authorizedSharedFlow = _authorizedSharedFlow.asSharedFlow()

    private val _errorsFlow = MutableSharedFlow<String>(replay = 1)
    val errorsFlow = _errorsFlow.asSharedFlow()

    fun onEvent(event: SignInEvent) {
        when(event) {
            is SignInEvent.PhoneNumberChanged -> {
                changePhoneNumber(event.value)
            }
            is SignInEvent.PasswordChanged -> {
                changePassword(event.value)
            }
            is SignInEvent.SignIn -> {
                logIn()
            }
        }
    }

    private fun changePhoneNumber(newValue: String) {
        _signInStateFlow.value = _signInStateFlow.value.copy(
            phoneNumber = newValue
        )
    }

    private fun changePassword(newValue: String) {
        _signInStateFlow.value = _signInStateFlow.value.copy(
            password = newValue
        )
    }

    private fun logIn() {
        viewModelScope.launch {
            val currentValue = signInStateFlow.value
            authRepo.signIn(
                currentValue.phoneNumber,
                currentValue.password
            ).onSuccess { response ->
                _authorizedSharedFlow.emit(AuthState.Authorized(response.userInfo.userId))
            }.onError {_, msg ->
                _authorizedSharedFlow.emit(AuthState.Unauthorized)
                msg?.let { _errorsFlow.emit(it) }
            }.onException {
                _authorizedSharedFlow.emit(AuthState.Unauthorized)
                it.localizedMessage?.let { msg -> _errorsFlow.emit(msg) }
            }
        }
    }
}