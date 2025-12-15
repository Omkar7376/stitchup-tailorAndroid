package com.tailorapp.stitchup.data.remote.api

import com.tailorapp.stitchup.data.remote.dto.loginDto.LoginRequestDto
import com.tailorapp.stitchup.data.remote.dto.loginDto.LoginResponseDto
import com.tailorapp.stitchup.data.remote.dto.registerDto.RegisterRequestDto
import com.tailorapp.stitchup.data.remote.dto.registerDto.RegisterUserResponseDto
import com.tailorapp.stitchup.domain.model.user.UserUpdateRequest
import com.tailorapp.stitchup.domain.model.user.UserUpdateResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApiService {
    @POST("/user/login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body loginRequestDto: LoginRequestDto) : LoginResponseDto

    @PUT("/user/updateuser")
    @Headers("Content-Type: application/json")
    suspend fun updateUser(@Body userUpdateRequest: UserUpdateRequest) : UserUpdateResponse

    @POST("/user/addUser")
    @Headers("Content-Type: application/json")
    suspend fun addUser(@Body registerRequestDto: RegisterRequestDto) : RegisterUserResponseDto
}