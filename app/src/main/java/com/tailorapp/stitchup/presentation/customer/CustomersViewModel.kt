package com.tailorapp.stitchup.presentation.customer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.customerDto.customerList.CustomerDataDto
import com.tailorapp.stitchup.domain.usecase.customer.CustomersListUseCase
import com.tailorapp.stitchup.domain.usecase.customer.GetCustomerDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersViewModel @Inject constructor(
    private val customersListUseCase: CustomersListUseCase,
    private val getCustomerDetailsUseCase: GetCustomerDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CustomersUiState())
    val uiState: StateFlow<CustomersUiState> = _uiState.asStateFlow()

    private var allCustomer: List<CustomerDataDto> = emptyList()

    init {
        getCustomers()
    }

    fun getCustomerDetails(id: Int){
        viewModelScope.launch {
            getCustomerDetailsUseCase.getCustomerDetails(id).collect { result ->
                when(result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true,)
                        Log.d("###", "getCustomerDetails: Loading")
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            customerDetails = result.data,
                            error = null
                        )
                        Log.d("###", "getCustomerDetails: ${result.data}")
                    }
                    is Resource.Error -> {
                        _uiState.value = uiState.value.copy(error = result.message,)
                        Log.d("###", "getCustomerDetails: ${result.message}")
                    }
                }
            }
        }
    }

    fun getCustomers() {
        viewModelScope.launch {
            customersListUseCase.getCustomersList().collect { result ->
                when(result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true,)
                    }
                    is Resource.Success -> {
                        allCustomer = result.data ?: emptyList()
                        _uiState.value = _uiState.value.copy(
                            customers = allCustomer,
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = result.message,
                        )
                    }
                }
            }
        }
    }

    fun search(query: String){
        if (query.isBlank()){
            _uiState.value = _uiState.value.copy(
                customers = allCustomer,
            )
            return
        }

        val filtered = allCustomer.filter { customer ->
            customer.NAME.contains(query, ignoreCase = true)
            || customer.MOB_NO.contains(query)
        }
        _uiState.value = _uiState.value.copy(
            customers = filtered,
        )
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy()
    }

    fun getCustomersList() = customersListUseCase.getCustomersList()
}