package com.tailorapp.stitchup.domain.repo.customerRepo

import com.tailorapp.stitchup.data.remote.dto.customerDto.updateShirtDto.UpdateShirtResponseDto
import com.tailorapp.stitchup.domain.model.customer.updateShirt.UpdateShirtRequest
import com.tailorapp.stitchup.domain.model.customer.updateShirt.UpdateShirtResponse
import retrofit2.Response

interface UpdateShirtRepo {
    suspend fun updateShirt(id: Int, updateShirtRequest: UpdateShirtRequest) : Response<UpdateShirtResponse>
}