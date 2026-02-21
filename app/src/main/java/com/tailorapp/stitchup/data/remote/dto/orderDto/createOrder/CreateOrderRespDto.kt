package com.tailorapp.stitchup.data.remote.dto.orderDto.createOrder

data class CreateOrderRespDto(
    val code: Int,
    val message: String,
    val order: List<OrderDataResponse>
)