package com.example.registrationapp.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.registrationapp.data.UserInfo
import com.example.registrationapp.events.ProfileEvent
import com.example.registrationapp.repo.abstraction.AuthRepo
import com.example.registrationapp.states.AuthState
import com.example.registrationapp.states.ProfileFragmentState
import com.example.registrationapp.utils.getFormattedPhoneNumber
import com.example.registrationapp.utils.toFullPhoneNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val stateHandle: SavedStateHandle
) : BaseViewModel() {
    private var initialUserInfo: UserInfo = UserInfo()
    private var _currentUserInfo: UserInfo = UserInfo()
    val currentUserInfo: UserInfo
    get() = _currentUserInfo

    private val _profileFragmentStateFlow = MutableStateFlow(ProfileFragmentState())
    val profileFragmentStateFlow = _profileFragmentStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            _profileFragmentStateFlow.value = _profileFragmentStateFlow.value.copy(
                isLoading = true
            )
            stateHandle.get<Int>("userId")?.let { uid ->
                getUserInfo(uid)?.let { userInfo ->
                    initialUserInfo = userInfo
                    _currentUserInfo = initialUserInfo.copy()
                    _profileFragmentStateFlow.value = _profileFragmentStateFlow.value.copy(
                        name = userInfo.name,
                        surname = userInfo.surname,
                        phoneNumber = toFullPhoneNumber(userInfo.phoneCode, userInfo.phoneNumber)
                    )
                } ?: _authorizedSharedFlow.emit(AuthState.Unauthorized)
            } ?: _authorizedSharedFlow.emit(AuthState.Unauthorized)
            _profileFragmentStateFlow.value = _profileFragmentStateFlow.value.copy(
                isLoading = false
            )
        }
    }

    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.NameChanged -> {
                changeName(event.value)
            }
            is ProfileEvent.SurnameChanged -> {
                changeSurname(event.value)
            }
            is ProfileEvent.PhoneNumberChanged -> {
                changePhoneNumber(event.value)
            }
            is ProfileEvent.SaveProfile -> {
                saveUserInfo()
            }
        }
    }

    private suspend fun getUserInfo(uid: Int) = authRepo.getUserInfo(uid)

    private fun changeName(value: String) {
        _currentUserInfo = _currentUserInfo.copy(
            name = value
        )
        _profileFragmentStateFlow.value = _profileFragmentStateFlow.value.copy(
            name = value,
            dataChanged = initialUserInfo != currentUserInfo
        )
    }

    private fun changeSurname(value: String) {
        _currentUserInfo = _currentUserInfo.copy(
            surname = value
        )
        _profileFragmentStateFlow.value = _profileFragmentStateFlow.value.copy(
            surname = value,
            dataChanged = initialUserInfo != currentUserInfo
        )
    }

    private fun changePhoneNumber(value: String) {
        _currentUserInfo = try {
            val formattedPhoneNumber = getFormattedPhoneNumber(value)
            _currentUserInfo.copy(
                phoneCode = "+" + formattedPhoneNumber.countryCode,
                phoneNumber = "" + formattedPhoneNumber.nationalNumber
            )
        } catch (e: Exception) {
            _currentUserInfo.copy(
                phoneCode = "",
                phoneNumber = ""
            )
        }
        _profileFragmentStateFlow.value = _profileFragmentStateFlow.value.copy(
            phoneNumber = value,
            dataChanged = initialUserInfo != currentUserInfo
        )
    }

    private fun saveUserInfo() {
        viewModelScope.launch {
            _profileFragmentStateFlow.value = _profileFragmentStateFlow.value.copy(
                isLoading = true
            )
            authRepo.updateUserInfo(currentUserInfo).onOk {
                initialUserInfo = currentUserInfo
                _profileFragmentStateFlow.value = _profileFragmentStateFlow.value.copy(
                    dataChanged = false
                )
            }.onFail { e ->
                e.message?.let { _errorsFlow.emit(it) }
                    ?: _errorsFlow.emit("Unknown error occurred")
            }
        }
        _profileFragmentStateFlow.value = _profileFragmentStateFlow.value.copy(
            isLoading = false
        )
    }

}