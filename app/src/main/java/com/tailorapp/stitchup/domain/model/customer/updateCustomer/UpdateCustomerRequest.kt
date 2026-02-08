package com.tailorapp.stitchup.domain.model.customer.updateCustomer

data class UpdateCustomerRequest(
    val address: String,
    val age: Int,
    val gender: String,
    val mob_num: String,
    val name: String
)