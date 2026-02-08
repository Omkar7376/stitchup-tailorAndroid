package com.tailorapp.stitchup.data.remote.dto.customerDto.updateShirtDto

data class UpdateShirtRequestDto(
    val back: Double,
    val bicep: Double,
    val chest: Double,
    val collar: Double,
    val cuff: Double,
    val front1: Double,
    val front2: Double,
    val front3: Double,
    val length: Double,
    val shoulder: Double,
    val sleeve: Double
)