package com.tailorapp.stitchup.domain.repo.customerRepo

import com.tailorapp.stitchup.data.remote.dto.customerDto.updateCustomerDto.UpdateCustomerRequestDto
import com.tailorapp.stitchup.domain.model.customer.updateCustomer.UpdateCustomerResponse
import retrofit2.Response

interface UpdateCustomerRepo {
    suspend fun updateCustomer(
        id: Int,
        updateCustomerRequest: UpdateCustomerRequestDto
    ): Response<UpdateCustomerResponse>
}