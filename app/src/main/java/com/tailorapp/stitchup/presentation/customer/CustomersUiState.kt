package com.tailorapp.stitchup.presentation.customer

import com.tailorapp.stitchup.data.remote.dto.customerDto.customerList.CustomerDataDto
import com.tailorapp.stitchup.domain.model.customer.getCustomer.GetCustomerDetailsResponse

data class CustomersUiState(
    val isLoading: Boolean = false,
    val customers: List<CustomerDataDto> = emptyList(),
    val error: String? = null,
    val message: String? = null,
    val customerDetails: GetCustomerDetailsResponse? = null,
)