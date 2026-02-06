package com.tailorapp.stitchup.domain.usecase.customer

import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.domain.model.customer.getCustomer.GetCustomerDetailsResponse
import com.tailorapp.stitchup.domain.repo.customerRepo.GetCustomerDetailsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class GetCustomerDetailsUseCase(private val repository: GetCustomerDetailsRepo) {
    fun getCustomerDetails(id: Int) : Flow<Resource<GetCustomerDetailsResponse>> = flow {
        emit(Resource.Loading())
        try {
            val result = repository.getCustomerDetails(id)
            emit(Resource.Success(result))
        } catch (e : Exception) {
            emit(Resource.Error(e.message ?: "Something went wrong"))
        } catch (e : HttpException){
            val errorMessage = when (e.code()){
                200 -> "Customer Found"
                400 -> "Invalid email or password"
                401 -> "Unauthorized access"
                404 -> "Customer not found"
                500 -> "Server Error. Please try again later."
                else -> {
                    e.message() ?: "Something went wrong"
                }
            }
            emit(Resource.Error(errorMessage))
        }
    }.flowOn(Dispatchers.IO)
}