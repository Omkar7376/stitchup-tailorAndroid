package com.tailorapp.stitchup.domain.repo.customerRepo

import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.AddCustomerRequestDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.AddCustomerResponseDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.CustomerDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.customerList.GetCustomerResponseDto

interface AddCustomerRepo {
    suspend fun addCustomer(request: AddCustomerRequestDto) : Resource<AddCustomerResponseDto>
}
