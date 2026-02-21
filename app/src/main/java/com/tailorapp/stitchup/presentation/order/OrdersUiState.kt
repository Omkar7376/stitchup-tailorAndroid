package com.tailorapp.stitchup.presentation.order

import com.tailorapp.stitchup.data.remote.dto.orderDto.createOrder.OrderDataResponse

data class OrdersUiState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val error: String? = null,
    val orders: List<OrderDataResponse> = emptyList(),
    val customerId: Int = 0,

    val customerName: String = "",
    val customerMobile: String = "",
    val orderDate: String = "",
    val deliveryDate: String = "",
    val orderType: String = "Select",
    val shirtQty: String = "",
    val shirtAmt: String = "",
    val pantQty: String = "",
    val pantAmt : String = "",
    val discount: String = "",
    val advance: String = "",
    val note: String = "",
    val selectedStatus: String = "Pending"
)
