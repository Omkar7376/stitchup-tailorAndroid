package com.tailorapp.stitchup.presentation.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.customerDto.customerList.CustomerDataDto
import com.tailorapp.stitchup.domain.usecase.customer.CustomersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersViewModel @Inject constructor(private val customersListUseCase: CustomersListUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(CustomersUiState())
    val uiState: StateFlow<CustomersUiState> = _uiState.asStateFlow()

    private var allCustomer: List<CustomerDataDto> = emptyList()

    init {
        getCustomers()
    }

    fun getCustomers() {
        viewModelScope.launch {
            customersListUseCase.getCustomersList().collect { result ->
                when(result) {
                    is Resource.Loading -> {
                        _uiState.value = CustomersUiState(isLoading = true)
                    }
                    is Resource.Success -> {
                        allCustomer = result.data ?: emptyList()
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            customers = allCustomer,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = result.message,
                            customers = emptyList()
                        )
                    }
                }
            }
        }
    }

    fun search(query: String){
        if (query.isBlank()){
            _uiState.value = _uiState.value.copy(
                customers = allCustomer
            )
            return
        }

        val filtered = allCustomer.filter { customer ->
            customer.NAME.contains(query, ignoreCase = true)
            || customer.MOB_NO.contains(query)
        }
        _uiState.value = _uiState.value.copy(
            customers = filtered
        )
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }

    fun getCustomersList() = customersListUseCase.getCustomersList()
}