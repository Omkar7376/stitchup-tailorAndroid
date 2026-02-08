package com.tailorapp.stitchup.domain.usecase.customer

import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.domain.model.customer.updateShirt.UpdateShirtRequest
import com.tailorapp.stitchup.domain.model.customer.updateShirt.UpdateShirtResponse
import com.tailorapp.stitchup.domain.repo.customerRepo.UpdateShirtRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateShirtUseCase(private val repo: UpdateShirtRepo) {
    fun updateShirt(
        id: Int,
        updateShirtRequest: UpdateShirtRequest
    ): Flow<Resource<UpdateShirtResponse>> = flow {
        emit(Resource.Loading())
        try {
            val result = repo.updateShirt(id, updateShirtRequest)
            emit(Resource.Success(result.body()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}