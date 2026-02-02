package com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer

data class OrderDto(
    val advanceAmount: Int,
    val deliveryDate: String,
    val discount: Int,
    val note: String,
    val orderDate: String,
    val orderType: String,
    val status: String
)