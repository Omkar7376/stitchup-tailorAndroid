package com.tailorapp.stitchup.domain.model.customer.getCustomers

import java.sql.Date

data class CustomerData(
    val ADDRESS: String,
    val BOOKNO: Int,
    val GENDER: String,
    val ID: Int,
    val MOB_NO: String,
    val NAME: String,
    val CREATED_AT: String,
    val UPDATED_AT: String
)