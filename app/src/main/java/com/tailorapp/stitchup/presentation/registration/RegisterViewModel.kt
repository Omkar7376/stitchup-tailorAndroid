package com.tailorapp.stitchup.presentation.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.registerDto.RegisterRequestDto
import com.tailorapp.stitchup.domain.usecase.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class AuthState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val message : String? = null,
    val authenticated : Boolean = false
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState : StateFlow<AuthState> = _authState

    fun register(request: RegisterRequestDto) {
        Log.d("###", "Register request: $request")

        viewModelScope.launch {
            registerUseCase.register(request).collect { result ->
                Log.d("###", "Register request: $request")
                when (result) {
                    is Resource.Loading -> {
                        _authState.value = _authState.value.copy(isLoading = true, error = null, message = null)
                    }
                    is Resource.Success -> {
                        _authState.value = _authState.value.copy(isLoading = false, authenticated = true, message = "Registration Successful")
                    }
                    is Resource.Error -> {
                        _authState.value = _authState.value.copy(isLoading = false, message = result.message ?: "Something went wrong")
                    }
                }
            }
        }
    }

    fun consumeRegisterEvent() {
        _authState.value = _authState.value.copy(authenticated = false)
    }

    fun clearMessage() {
        _authState.value = _authState.value.copy(message = null)
    }

    fun clearState() {
        _authState.value = AuthState()
    }
}