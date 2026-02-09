package com.tailorapp.stitchup.data.remote.dto.customerDto.updateCustomerDto

import java.sql.Date

data class CustomerDto(
    val ADDRESS: String,
    val BOOKNO: Int,
    val CUSTOMERID: Int,
    val GENDER: String,
    val MOB_NO: String,
    val NAME: String,
    val CREATED_AT: String,
    val UPDATED_AT: String
)