package com.tailorapp.stitchup.data.remote.dto.customerDto.updatePantDto

data class PantMeasurementDataDto(
    val bottom: Double,
    val insideLength: Double,
    val knee: Double,
    val outsideLength: Double,
    val rise: Double,
    val seat: Double,
    val thigh: Int,
    val waist: Double
)