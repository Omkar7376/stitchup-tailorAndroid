package com.tailorapp.stitchup.data.remote.dto.customerDto.addShirtMeasurement

data class addShirtMeasurResponseDto(
    val code: Int,
    val message: String,
    val shirt: List<Shirt>
)