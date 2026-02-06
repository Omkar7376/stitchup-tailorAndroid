package com.tailorapp.stitchup.data.repo.customerRepo

import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.api.customerApi.CustomerApiService
import com.tailorapp.stitchup.domain.mapper.customerMapper.getCustomers.toGetCustomerResponse
import com.tailorapp.stitchup.domain.model.customer.getCustomer.GetCustomerDetailsResponse
import com.tailorapp.stitchup.domain.repo.customerRepo.GetCustomerDetailsRepo

class GetCustomerDetailsRepoImp(private val apiService: CustomerApiService) : GetCustomerDetailsRepo {
    override suspend fun getCustomerDetails(id: Int): GetCustomerDetailsResponse {
        return try {
            val response = apiService.getCustomerById(id.toString())
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.toGetCustomerResponse()
            } else {
                throw Exception(response.message())
            }
        } catch (e : Exception){
            throw Exception(e.message)
        }
    }
}