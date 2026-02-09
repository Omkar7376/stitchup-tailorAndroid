package com.tailorapp.stitchup.domain.mapper.customerMapper.getCustomers

import com.tailorapp.stitchup.data.remote.dto.customerDto.getCustomerDetails.CustomerDetailsDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.getCustomerDetails.GetCustomerDetailsResponseDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.getCustomerDetails.PantMeasurementDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.getCustomerDetails.ShirtMeasurementDto
import com.tailorapp.stitchup.domain.model.customer.getCustomer.CustomerDetails
import com.tailorapp.stitchup.domain.model.customer.getCustomer.GetCustomerDetailsResponse
import com.tailorapp.stitchup.domain.model.customer.getCustomer.PantMeasurement
import com.tailorapp.stitchup.domain.model.customer.getCustomer.ShirtMeasurement

fun GetCustomerDetailsResponseDto.toGetCustomerResponse() = GetCustomerDetailsResponse(
    customer = customer.map { it.toCustomerDetails() },
    pantMeasurements = pantMeasurements.map { it.toPantMeasurement() },
    shirtMeasurements = shirtMeasurements.map { it.toShirtMeasurement() }
)

fun CustomerDetailsDto.toCustomerDetails() = CustomerDetails(
    ID = ID,
    BOOKNO = BOOKNO,
    NAME = NAME,
    GENDER = GENDER,
    MOB_NO = MOB_NO,
    ADDRESS = ADDRESS,
    CREATED_AT = CREATED_AT,
    UPDATED_AT = UPDATED_AT
)

fun ShirtMeasurementDto.toShirtMeasurement() = ShirtMeasurement(
    SHIRTID = SHIRTID,
    CHEST = CHEST,
    BACK = BACK,
    BICEP = BICEP,
    SLEEVE = SLEEVE,
    CUFF = CUFF,
    COLLAR = COLLAR,
    SHOULDER = SHOULDER,
    FRONT1 = FRONT1,
    FRONT2 = FRONT2,
    FRONT3 = FRONT3,
    LENGTH = LENGTH
)

fun PantMeasurementDto.toPantMeasurement() = PantMeasurement(
    PANTID = PANTID,
    INSIDE_LENGTH = INSIDE_LENGTH,
    OUTSIDE_LENGTH = OUTSIDE_LENGTH,
    WAIST = WAIST,
    THIGH = THIGH,
    SEAT = SEAT,
    RISE = RISE,
    KNEE = KNEE,
    BOTTOM = BOTTOM
)
