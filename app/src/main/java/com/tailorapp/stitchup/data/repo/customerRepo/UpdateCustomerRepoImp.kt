package com.tailorapp.stitchup.data.repo.customerRepo

import com.tailorapp.stitchup.data.remote.api.customerApi.CustomerApiService
import com.tailorapp.stitchup.data.remote.dto.customerDto.updateCustomerDto.UpdateCustomerRequestDto
import com.tailorapp.stitchup.domain.model.customer.updateCustomer.UpdateCustomerResponse
import com.tailorapp.stitchup.domain.repo.customerRepo.UpdateCustomerRepo
import com.tailorapp.stitchup.domain.mapper.customerMapper.getCustomers.toUpdateCustomerResponse
import retrofit2.Response

class UpdateCustomerRepoImp(private val apiService: CustomerApiService) : UpdateCustomerRepo {
    override suspend fun updateCustomer(
        id: Int,
        updateCustomerRequest: UpdateCustomerRequestDto
    ): Response<UpdateCustomerResponse> {
        return try {
            val response = apiService.updateCustomer(id, updateCustomerRequest)
            if (response.isSuccessful && response.body() != null) {
                val domainModel = response.body()!!.toUpdateCustomerResponse()
                Response.success(domainModel)
            } else {
                throw Exception(response.message())
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }
}