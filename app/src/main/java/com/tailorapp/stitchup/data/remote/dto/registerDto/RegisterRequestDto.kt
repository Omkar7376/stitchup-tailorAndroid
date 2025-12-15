package com.tailorapp.stitchup.data.remote.dto.registerDto

data class RegisterRequestDto(
    val address: String,
    val age: Int,
    val email: String,
    val mob_no: String,
    val name: String,
    val password: String,
    val username: String
)