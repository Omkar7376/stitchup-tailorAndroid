package com.tailorapp.stitchup.domain.model.customer.updateShirt

data class UpdateShirtResponse(
    val code: Int,
    val message: String,
    val shirtMeasurment: List<ShirtMeasurementData>
)