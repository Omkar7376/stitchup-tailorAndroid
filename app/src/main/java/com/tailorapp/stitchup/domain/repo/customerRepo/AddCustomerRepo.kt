package com.tailorapp.stitchup.domain.repo.customerRepo

import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.AddCustomerRequestDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.AddCustomerResponseDto

interface AddCustomerRepo {
    suspend fun addCustomer(request: AddCustomerRequestDto) : Resource<AddCustomerResponseDto>
}