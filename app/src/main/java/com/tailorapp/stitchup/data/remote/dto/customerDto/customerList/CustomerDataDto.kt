package com.tailorapp.stitchup.data.remote.dto.customerDto.customerList

import java.sql.Date

data class CustomerDataDto(
    val ADDRESS: String,
    val BOOKNO: Int,
    val GENDER: String,
    val ID: Int,
    val MOB_NO: String,
    val NAME: String,
    val CREATED_AT: String,
    val UPDATED_AT: String
)