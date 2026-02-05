package com.tailorapp.stitchup.presentation.addCustomer.dataClasses

data class MeasurementField(
    val label: String,
    val value: String,
    val onValueChange: (String) -> Unit
)