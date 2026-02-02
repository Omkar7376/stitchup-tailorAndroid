package com.tailorapp.stitchup.domain.usecase.customer

import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.AddCustomerRequestDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.AddCustomerResponseDto
import com.tailorapp.stitchup.data.repo.customerRepo.AddCustomerRepoImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AddCustomerUseCase(private val repoImp: AddCustomerRepoImp) {
    fun addCustomer(request: AddCustomerRequestDto) : Flow<Resource<AddCustomerResponseDto>> = flow{
        emit(Resource.Loading())
        try {
            val result = repoImp.addCustomer(request)
            emit(result)
        } catch (e : Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}