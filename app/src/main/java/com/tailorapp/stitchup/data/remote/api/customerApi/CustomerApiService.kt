package com.tailorapp.stitchup.data.remote.api.customerApi

import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.AddCustomerRequestDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.AddCustomerResponseDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.customerList.GetCustomerResponseDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.getCustomerDetails.GetCustomerDetailsResponseDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.updateCustomerDto.UpdateCustomerRequestDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.updateCustomerDto.UpdateCustomerResponseDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.updatePantDto.UpdatePantRequestDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.updatePantDto.UpdatePantResponseDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.updateShirtDto.UpdateShirtResponseDto
import com.tailorapp.stitchup.domain.model.customer.updateShirt.UpdateShirtRequest
import com.tailorapp.stitchup.domain.model.customer.updateShirt.UpdateShirtResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @PUT("/customer/updatecustomer/{id}")
    suspend fun updateCustomer(
        @Path("id") id: Int,
        @Body updateCustomerRequest: UpdateCustomerRequestDto
    ): Response<UpdateCustomerResponseDto>

    @PUT("/shirt/updateShirtMeasurment/{id}")
    suspend fun updateShirtMeasurement(
        @Path("id") id: Int,
        @Body updateShirtRequest: UpdateShirtRequest
    ): Response<UpdateShirtResponse>

    @PUT("/pant/updatePantMeasurment/{id}")
    suspend fun updatePantMeasurement(
        @Path("id") id: Int,
        @Body updatePantRequest: UpdatePantRequestDto
    ): Response<UpdatePantResponseDto>
}
