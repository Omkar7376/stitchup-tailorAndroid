package com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer

data class AddCustomerRequestDto(
    val address: String,
    val age: Int,
    val gender: String,
    val mob_num: String,
    val name: String,
    val order: OrderDto,
    val pantMeasurement: PantMeasurementDto,
    val shirtMeasurement: ShirtMeasurementDto
)