package com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer

data class AddCustomerResponseDto(
    val code: Int,
    val customer: List<CustomerDto>,
    val message: String,
    val order: List<OrderResponseDto>,
    val pant: List<PantResponseDto>,
    val shirt: List<ShirtResponseDto>
)