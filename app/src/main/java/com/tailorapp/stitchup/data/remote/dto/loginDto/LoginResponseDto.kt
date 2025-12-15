package com.tailorapp.stitchup.data.remote.dto.loginDto


data class LoginResponseDto(
    val code: Int,
    val `data`: List<LoginResponseDataDto>,
    val message: String
)