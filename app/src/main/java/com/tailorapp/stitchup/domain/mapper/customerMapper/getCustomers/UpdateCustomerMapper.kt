package com.tailorapp.stitchup.domain.mapper.customerMapper.getCustomers

import com.tailorapp.stitchup.data.remote.dto.customerDto.updateCustomerDto.CustomerDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.updateCustomerDto.UpdateCustomerResponseDto
import com.tailorapp.stitchup.domain.model.customer.updateCustomer.CustomerData
import com.tailorapp.stitchup.domain.model.customer.updateCustomer.UpdateCustomerResponse

fun UpdateCustomerResponseDto.toUpdateCustomerResponse() : UpdateCustomerResponse{
    return UpdateCustomerResponse(
        code = code,
        customer = customer.map{ it.toCustomerData() },
        message = message
    )
}

fun CustomerDto.toCustomerData() = CustomerData(
    ADDRESS = ADDRESS,
    AGE = AGE,
    GENDER = GENDER,
    MOB_NO = MOB_NO,
    NAME = NAME
)