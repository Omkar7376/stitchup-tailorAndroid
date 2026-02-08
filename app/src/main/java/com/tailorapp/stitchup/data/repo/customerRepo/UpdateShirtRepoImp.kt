package com.tailorapp.stitchup.data.repo.customerRepo

import com.tailorapp.stitchup.data.remote.api.customerApi.CustomerApiService
import com.tailorapp.stitchup.domain.model.customer.updateShirt.UpdateShirtRequest
import com.tailorapp.stitchup.domain.model.customer.updateShirt.UpdateShirtResponse
import com.tailorapp.stitchup.domain.repo.customerRepo.UpdateShirtRepo
import retrofit2.Response

class UpdateShirtRepoImp(private val apiService: CustomerApiService) : UpdateShirtRepo {
    override suspend fun updateShirt(
        id: Int,
        updateShirtRequest: UpdateShirtRequest
    ): Response<UpdateShirtResponse> {
        return try {
            val response = apiService.updateShirtMeasurement(id, updateShirtRequest)
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