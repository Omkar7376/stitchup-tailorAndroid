package com.tailorapp.stitchup.presentation.addCustomer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.AddCustomerRequestDto
import com.tailorapp.stitchup.domain.usecase.customer.AddCustomerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddCustomerUIState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val error: String? = null
)

class AddCustomerViewModel @Inject constructor(private val addCustomerUseCase: AddCustomerUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(AddCustomerUIState())
    val uiState: StateFlow<AddCustomerUIState> = _uiState.asStateFlow()

    fun addNewCustomer(request: AddCustomerRequestDto) {
        viewModelScope.launch {
            addCustomerUseCase.addCustomer(request).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = AddCustomerUIState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = AddCustomerUIState(isLoading = false, message = result.data?.message)
                    }
                    is Resource.Error -> {
                        _uiState.value = AddCustomerUIState(error = result.message)
                    }
                }
            }
        }
    }
}