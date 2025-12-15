package com.tailorapp.stitchup.data.repo.authRepo

import android.util.Log
import com.tailorapp.stitchup.domain.mapper.toLoginResponse
import com.tailorapp.stitchup.data.remote.api.AuthApiService
import com.tailorapp.stitchup.data.remote.dto.loginDto.LoginRequestDto
import com.tailorapp.stitchup.domain.model.auth.login.LoginResponse
import com.tailorapp.stitchup.domain.repo.authRepo.LoginRepo

class LoginRepoImp(
    private val apiService: AuthApiService,
) : LoginRepo {
    override suspend fun login(email: String, password: String): LoginResponse {
        try {
            val response = apiService.login(LoginRequestDto(email, password))
            return response.toLoginResponse()
            Log.d("###", "login: ${response}")
        } catch (e: Exception){
            Log.e("###", "Api Error Login failed: ${e.message}")
            throw e
        }
    }
}