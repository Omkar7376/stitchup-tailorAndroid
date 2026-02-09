package com.tailorapp.stitchup.domain.repo.customerRepo

import com.tailorapp.stitchup.data.remote.dto.customerDto.updatePantDto.UpdatePantRequestDto
import com.tailorapp.stitchup.data.remote.dto.customerDto.updatePantDto.UpdatePantResponseDto
import retrofit2.Response

interface UpdatePantRepo {
    suspend fun updatePantMeasurement(id: Int, updatePantRequest: UpdatePantRequestDto): Response<UpdatePantResponseDto>
}
