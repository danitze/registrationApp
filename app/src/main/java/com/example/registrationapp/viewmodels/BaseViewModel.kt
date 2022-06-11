package com.example.registrationapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.registrationapp.states.AuthState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel : ViewModel() {
    protected val _authorizedSharedFlow = MutableSharedFlow<AuthState>(replay = 1)
    val authorizedSharedFlow = _authorizedSharedFlow.asSharedFlow()

    protected val _errorsFlow = MutableSharedFlow<String>(replay = 1)
    val errorsFlow = _errorsFlow.asSharedFlow()
}