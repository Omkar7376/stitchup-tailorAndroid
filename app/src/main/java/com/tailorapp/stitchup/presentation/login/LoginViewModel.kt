package com.tailorapp.stitchup.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.datastore.AuthDataStore
import com.tailorapp.stitchup.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class LoginUIState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val token: String? = null,
    val error: String? = null,
    val message: String? = null,
    val loginSuccessEvent: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val authDataStore: AuthDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(LoginUIState())
    val state : StateFlow<LoginUIState> = _state

    fun onEmailChanged(newEmail: String){
        _state.value = _state.value.copy(email = newEmail)
    }

    fun onPasswordChanged(newPassword: String){
        _state.value = _state.value.copy(password = newPassword)
    }

    fun login() {
        val email = _state.value.email
        val password = _state.value.password

        viewModelScope.launch {
            loginUseCase.login(email, password).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        Log.d("###", "Loading")
                        _state.value = _state.value.copy(isLoading = true, error = null, message = null)
                    }
                    is Resource.Success -> {
                        Log.d("###", "Success: ${result.data}")
                        val token = result.data?.data?.get(0)?.token
                        token?.let {
                            authDataStore.saveToken(it)
                        }
                        _state.value = _state.value.copy(
                            isLoading = false,
                            loginSuccessEvent = true,
                            token = result.data?.data?.get(0)?.token,
                            error = null,
                            message = "Login Successful"
                        )
                    }
                    is Resource.Error -> {
                        Log.d("###", "Error: ${result.message}")
                        _state.value = _state.value.copy(
                            isLoading = false,
                            message = result.message ?: "Something went wrong"
                        )
                    }
                }
            }
        }
    }

    fun consumeLoginEvent() {
        _state.value = _state.value.copy(loginSuccessEvent = false)
    }
    fun clearMessage() {
        _state.value = _state.value.copy(message = null)
    }

    fun clearState() {
        _state.value = LoginUIState()
    }
}