package com.tailorapp.stitchup.data.repo.authRepo

import android.util.Log
import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.api.authApi.AuthApiService
import com.tailorapp.stitchup.data.remote.dto.registerDto.RegisterRequestDto
import com.tailorapp.stitchup.domain.mapper.authMapper.toUserDetails
import com.tailorapp.stitchup.domain.model.auth.register.RegisterUserResponse
import com.tailorapp.stitchup.domain.repo.authRepo.RegisterRepo

class RegisterRepoImp(private val apiService: AuthApiService) : RegisterRepo {
    override suspend fun register(request: RegisterRequestDto) : Resource<RegisterUserResponse?> {
        return try {
            val response = apiService.addUser(request)
            Log.d("###", "Register: ${response.user}")
            Resource.Success(response.toUserDetails())
        } catch (e : Exception){
            Resource.Error(e.message ?: "Unknown Error")
        }
    }
}