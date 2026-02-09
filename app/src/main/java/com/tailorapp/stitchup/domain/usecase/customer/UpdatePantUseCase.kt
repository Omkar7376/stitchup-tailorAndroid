package com.tailorapp.stitchup.domain.usecase.customer

import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.customerDto.updatePantDto.UpdatePantRequestDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.updatePantDto.UpdatePantResponseDto
import com.tailorapp.stitchup.domain.repo.customerRepo.UpdatePantRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdatePantUseCase(private val updatePantRepo: UpdatePantRepo) {
    fun updatePantMeasurement(
        id: Int,
        updatePantRequest: UpdatePantRequestDto
    ): Flow<Resource<UpdatePantResponseDto>> = flow {
        emit(Resource.Loading())
        try {
            val result = updatePantRepo.updatePantMeasurement(id, updatePantRequest)
            emit(Resource.Success(result.body()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}