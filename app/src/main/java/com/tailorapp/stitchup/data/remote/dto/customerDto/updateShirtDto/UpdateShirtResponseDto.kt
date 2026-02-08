package com.tailorapp.stitchup.data.remote.dto.customerDto.updateShirtDto

data class UpdateShirtResponseDto(
    val code: Int,
    val message: String,
    val shirtMeasurment: List<ShirtMeasurementDataDto>
)