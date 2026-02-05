package com.tailorapp.stitchup.domain.usecase.customer

import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.AddCustomerRequestDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.addCustomer.AddCustomerResponseDto
import com.tailorapp.stitchup.domain.repo.customerRepo.AddCustomerRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class AddCustomerUseCase(private val repoImp: AddCustomerRepo) {
    fun addCustomer(request: AddCustomerRequestDto) : Flow<Resource<AddCustomerResponseDto>> = flow{
        emit(Resource.Loading())
        try {
            val result = repoImp.addCustomer(request)
            emit(result)
        } catch (e : Exception){
            emit(Resource.Error(e.message.toString()))
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                200 -> "Ok"
                201 -> "Customer Created"
                400 -> "Invalid email or password"
                401 -> "Unauthorized access"
                500 -> "Server Error. Please try again later."
                else -> e.message() ?: "Something went wrong"
            }
            emit(Resource.Error(errorMessage))
        }
    }.flowOn(Dispatchers.IO)
}