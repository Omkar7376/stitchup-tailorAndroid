package com.tailorapp.stitchup.data.remote.dto.registerDto

data class RegisterUserResponseDto(
    val profile: List<ProfileDetailsDto>,
    val user: List<UserDetailsDto>
)