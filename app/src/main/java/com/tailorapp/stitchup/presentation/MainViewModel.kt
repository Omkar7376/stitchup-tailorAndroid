package com.tailorapp.stitchup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailorapp.stitchup.datastore.AuthDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthState {
    object Loading : AuthState()
    object Unauthenticated : AuthState()
    object Authenticated : AuthState()
}

@HiltViewModel
class MainViewModel @Inject constructor (private val authDataStore: AuthDataStore) : ViewModel() {
    val authState: StateFlow<AuthState> =
            authDataStore.getToken()
                .map { token ->
                if (token.isNullOrEmpty()) AuthState.Unauthenticated else AuthState.Authenticated
            }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    AuthState.Loading
                )

}