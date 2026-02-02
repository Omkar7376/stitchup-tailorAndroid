package com.tailorapp.stitchup.data.repo.customerRepo

import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.api.customerApi.CustomerApiService
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.AddCustomerRequestDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.AddCustomerResponseDto
import com.tailorapp.stitchup.domain.repo.customerRepo.AddCustomerRepo

class AddCustomerRepoImp(private val apiService: CustomerApiService) : AddCustomerRepo {
    override suspend fun addCustomer(request: AddCustomerRequestDto) : Resource<AddCustomerResponseDto> {
        return try {
            val response = apiService.addCustomer(request)
            Resource.Success(response.body()!!)
        } catch (e : Exception){
            Resource.Error(e.message ?: "Unknown Error")
        }
    }
}