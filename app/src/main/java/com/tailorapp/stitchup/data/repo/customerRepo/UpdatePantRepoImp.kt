package com.tailorapp.stitchup.data.repo.customerRepo

import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.api.customerApi.CustomerApiService
import com.tailorapp.stitchup.data.remote.dto.customerDto.updatePantDto.UpdatePantRequestDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.updatePantDto.UpdatePantResponseDto
import com.tailorapp.stitchup.domain.repo.customerRepo.UpdatePantRepo
import retrofit2.Response

class UpdatePantRepoImp(private val apiService: CustomerApiService) : UpdatePantRepo {
    override suspend fun updatePantMeasurement(
        id: Int,
        updatePantRequest: UpdatePantRequestDto
    ): Response<UpdatePantResponseDto> {
        return try {
            val response = apiService.updatePantMeasurement(id, updatePantRequest)
            if (response.isSuccessful && response.body() != null) {
                response
            } else {
                throw Exception(response.message())
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }
}