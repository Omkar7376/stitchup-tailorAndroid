package com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer

data class OrderDto(
    val advanceAmount: Any,
    val deliveryDate: String,
    val discount: Any,
    val note: String,
    val orderDate: String,
    val orderType: String,
    val shirtQnt: Any,
    val shirtAmount: Any,
    val pantQnt: Any,
    val pantAmount: Any,
    val status: String
)