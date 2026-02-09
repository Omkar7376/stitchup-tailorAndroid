package com.tailorapp.stitchup.data.remote.dto.customerDto.updateCustomerDto

data class UpdateCustomerRequestDto(
    val address: String,
    val gender: String,
    val mob_num: String,
    val name: String
)