package com.tailorapp.stitchup.data.remote.dto.loginDto


data class LoginResponseDataDto(
    val token: String,
    val userData: List<UserDataDto>
)