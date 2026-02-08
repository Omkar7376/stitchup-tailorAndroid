package com.tailorapp.stitchup.domain.model.customer.updateCustomer

data class UpdateCustomerResponse(
    val code: Int,
    val customer: List<CustomerData>,
    val message: String
)