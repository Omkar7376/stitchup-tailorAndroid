package com.tailorapp.stitchup.data.remote.api.customerApi

import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.AddCustomerRequestDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.AddCustomerResponseDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.customerList.GetCustomerResponseDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.getCustomerDetails.GetCustomerDetailsResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CustomerApiService {
    @GET("/customer/getCustomers")
    suspend fun getCustomerList(): Response<GetCustomerResponseDto>

    @POST("/customer/addCustomer")
    suspend fun addCustomer(
        @Body addCustomerRequest: AddCustomerRequestDto
    ): Response<AddCustomerResponseDto>

    @GET("/customer/getCustomer/{id}")
    suspend fun getCustomerById(
        @Path("id") id: String
    ): Response<GetCustomerDetailsResponseDto>
}
