package com.tailorapp.stitchup.data.remote.dto.customerDto.getCustomerDetails

data class GetCustomerDetailsResponseDto(
    val customer: List<CustomerDetailsDto>,
    val pantMeasurements: List<PantMeasurementDto>,
    val shirtMeasurements: List<ShirtMeasurementDto>
)