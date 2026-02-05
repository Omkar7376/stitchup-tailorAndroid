package com.tailorapp.stitchup.presentation.customer

import com.tailorapp.stitchup.data.remote.dto.customerDto.customerList.CustomerDataDto

data class CustomersUiState(
    val isLoading: Boolean = false,
    val customers: List<CustomerDataDto> = emptyList(),
    val error: String? = null,
    val message: String? = null,
)