package com.tailorapp.stitchup.data.remote.dto.customerDto.updatePantDto

data class UpdatePantResponseDto(
    val message: String,
    val pantMeasurment: List<PantMeasurementDataDto>
)