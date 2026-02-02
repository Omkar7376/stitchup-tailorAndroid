package com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer

data class OrderResponseDto(
    val ADVANCE_AMOUNT: Int,
    val CUSTOMER_ID: Int,
    val DELIVERY_DATE: String,
    val DISCOUNT: Int,
    val FINAL_PAYABLE: Int,
    val NOTE: String,
    val ORDERID: Int,
    val ORDER_DATE: String,
    val ORDER_TYPE: String,
    val PANT_AMOUNT: Int,
    val SHIRT_AMOUNT: Int,
    val STATUS: String,
    val TOTAL_AMOUNT: Int
)