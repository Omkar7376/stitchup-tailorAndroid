package com.tailorapp.stitchup.domain.usecase.customer

import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.customerDto.CustomerDataDto
import com.tailorapp.stitchup.domain.repo.customerRepo.CustomersListRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CustomersListUseCase(private val repository: CustomersListRepo) {
    fun getCustomersList() : Flow<Resource<List<CustomerDataDto>>> = flow{
        emit(Resource.Loading())
        try {
            val result = repository.customerList()
            emit(result)
        } catch (e : Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}