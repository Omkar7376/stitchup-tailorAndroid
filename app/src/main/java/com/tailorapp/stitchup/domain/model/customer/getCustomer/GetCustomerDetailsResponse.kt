package com.tailorapp.stitchup.domain.model.customer.getCustomer

data class GetCustomerDetailsResponse(
    val customer: List<CustomerDetails>,
    val pantMeasurements: List<PantMeasurement>,
    val shirtMeasurements: List<ShirtMeasurement>
)