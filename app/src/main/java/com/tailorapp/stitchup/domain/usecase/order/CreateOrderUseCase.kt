package com.tailorapp.stitchup.domain.usecase.order

import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.orderDto.createOrder.CreateOrderReqDto
import com.tailorapp.stitchup.data.remote.dto.orderDto.createOrder.CreateOrderRespDto
import com.tailorapp.stitchup.domain.repo.orderRepo.CreateOrderRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateOrderUseCase(private val createOrderRepo: CreateOrderRepo) {
    fun createOrder(
        id: Int,
        createOrderRequest: CreateOrderReqDto
    ): Flow<Resource<CreateOrderRespDto>> = flow {
        emit(Resource.Loading())
        try {
            val result = createOrderRepo.createOrder(id, createOrderRequest)
            emit(Resource.Success(result.body()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}