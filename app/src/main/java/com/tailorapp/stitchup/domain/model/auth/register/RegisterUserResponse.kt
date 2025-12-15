package com.tailorapp.stitchup.domain.model.auth.register

data class RegisterUserResponse(
    val profile: List<ProfileDetails>,
    val user: List<UserDetails>
)