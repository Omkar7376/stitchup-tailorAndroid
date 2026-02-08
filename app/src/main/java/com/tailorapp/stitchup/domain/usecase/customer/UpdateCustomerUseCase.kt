package com.tailorapp.stitchup.domain.usecase.customer

import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.customerDto.updateCustomerDto.UpdateCustomerRequestDto
import com.tailorapp.stitchup.domain.model.customer.updateCustomer.UpdateCustomerResponse
import com.tailorapp.stitchup.domain.repo.customerRepo.UpdateCustomerRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class UpdateCustomerUseCase(private val repo: UpdateCustomerRepo) {
    fun updateCustomer(
        id: Int,
        updateCustomerRequest: UpdateCustomerRequestDto
    ): Flow<Resource<UpdateCustomerResponse>> = flow {
        emit(Resource.Loading())
        try {
            val result = repo.updateCustomer(id, updateCustomerRequest)
            emit(Resource.Success(result.body()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                200 -> "Customer Found"
                401 -> "Unauthorized access"
                404 -> "Customer not found"
                500 -> "Server Error. Please try again later."
                else -> e.message() ?: "Something went wrong"
            }
            emit(Resource.Error(errorMessage))
        }
    }
}