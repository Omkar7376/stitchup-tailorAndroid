package com.tailorapp.stitchup.data.remote.dto.registerDto

data class UserDetailsDto(
    val address: String,
    val age: Int,
    val createdAt: String,
    val email: String,
    val id: Int,
    val isActive: Boolean,
    val mob_no: String,
    val name: String,
    val password: String,
    val updatedAt: String,
    val username: String
)