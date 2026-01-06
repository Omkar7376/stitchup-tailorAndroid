package com.tailorapp.stitchup.data.remote.api.customerApi

import com.tailorapp.stitchup.data.remote.dto.customerDto.GetCustomerResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface CustomerApiService {
    @GET("/customer/getCustomers")
    suspend fun getCustomerList(): Response<GetCustomerResponseDto>
}