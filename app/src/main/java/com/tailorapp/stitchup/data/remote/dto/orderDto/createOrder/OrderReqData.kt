package com.tailorapp.stitchup.data.remote.dto.orderDto.createOrder

data class OrderReqData(
    val advanceAmount: Any,
    val deliveryDate: String,
    val discount: Any,
    val note: String,
    val orderDate: String,
    val orderType: String,
    val pantAmount: Any,
    val pantQnt: Any,
    val shirtAmount: Any,
    val shirtQnt: Any,
    val status: String
)