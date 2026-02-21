package com.tailorapp.stitchup.data.remote.dto.orderDto.createOrder

data class OrderDataResponse(
    val ADVANCE_AMOUNT: Int,
    val CUSTOMER_ID: Int,
    val CUSTOMERNAME: String,
    val CUSTOMERMOBILE: String,
    val DELIVERY_DATE: String,
    val DISCOUNT: Int,
    val FINAL_PAYABLE: Int,
    val NOTE: String,
    val ORDERID: Int,
    val ORDER_DATE: String,
    val ORDER_TYPE: String,
    val PANT_AMOUNT: Int,
    val PANT_QNT: Int,
    val PANT_TOTAL: Int,
    val SHIRT_AMOUNT: Int,
    val SHIRT_QNT: Int,
    val SHIRT_TOTAL: Int,
    val STATUS: String,
    val TOTAL_AMOUNT: Int
)