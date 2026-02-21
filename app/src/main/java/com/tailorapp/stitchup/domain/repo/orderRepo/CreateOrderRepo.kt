package com.tailorapp.stitchup.domain.repo.orderRepo

import com.tailorapp.stitchup.data.remote.dto.orderDto.createOrder.CreateOrderReqDto
import com.tailorapp.stitchup.data.remote.dto.orderDto.createOrder.CreateOrderRespDto
import retrofit2.Response

interface CreateOrderRepo {
    suspend fun createOrder(id: Int, createOrderRequest: CreateOrderReqDto): Response<CreateOrderRespDto>
}