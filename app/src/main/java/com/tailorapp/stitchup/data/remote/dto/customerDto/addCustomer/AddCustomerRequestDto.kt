package com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer

data class AddCustomerRequestDto(
    val address: String,
    val gender: String,
    val mob_num: String,
    val name: String,
    val order: OrderDto,
    val shirtMeasurement: ShirtMeasurementDto? = null,
    val pantMeasurement: PantMeasurementDto? = null
)