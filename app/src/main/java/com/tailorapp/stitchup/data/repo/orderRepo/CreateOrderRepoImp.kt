package com.tailorapp.stitchup.data.repo.orderRepo

import com.tailorapp.stitchup.data.remote.api.customerApi.CustomerApiService
import com.tailorapp.stitchup.data.remote.dto.orderDto.createOrder.CreateOrderReqDto
import com.tailorapp.stitchup.data.remote.dto.orderDto.createOrder.CreateOrderRespDto
import com.tailorapp.stitchup.domain.repo.orderRepo.CreateOrderRepo
import retrofit2.Response

class CreateOrderRepoImp(private val api: CustomerApiService) : CreateOrderRepo {
    override suspend fun createOrder(
        id: Int,
        createOrderRequest: CreateOrderReqDto
    ): Response<CreateOrderRespDto> {
        return try {
            val response = api.createOrder(id, createOrderRequest)
            if (response.isSuccessful && response.body() != null){
                response
            } else {
                throw Exception(response.message())
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }
}