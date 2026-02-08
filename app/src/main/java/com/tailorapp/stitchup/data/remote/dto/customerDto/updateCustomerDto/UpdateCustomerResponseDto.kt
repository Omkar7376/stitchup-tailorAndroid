package com.tailorapp.stitchup.data.remote.dto.customerDto.updateCustomerDto

data class UpdateCustomerResponseDto(
    val code: Int,
    val customer: List<CustomerDto>,
    val message: String
)