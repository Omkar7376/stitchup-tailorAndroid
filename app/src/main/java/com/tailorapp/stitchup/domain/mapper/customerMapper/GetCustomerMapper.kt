package com.tailorapp.stitchup.domain.mapper.customerMapper

import com.tailorapp.stitchup.data.remote.dto.customerDto.customerList.CustomerDataDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.customerList.GetCustomerResponseDto
import com.tailorapp.stitchup.domain.model.customer.CustomerData
import com.tailorapp.stitchup.domain.model.customer.GetCustomerResponse


fun GetCustomerResponseDto.toCustomerData() : GetCustomerResponse {
    return GetCustomerResponse(
        customer = customer.map { it.toCustomerData() }
    )
}

fun CustomerDataDto.toCustomerData() = CustomerData(
    ID = ID,
    BOOKNO = BOOKNO,
    NAME = NAME,
    AGE = AGE,
    GENDER = GENDER,
    MOB_NO = MOB_NO,
    ADDRESS = ADDRESS,
)