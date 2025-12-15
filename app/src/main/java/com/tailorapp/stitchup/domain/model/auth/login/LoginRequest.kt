package com.tailorapp.stitchup.domain.model.auth.login

data class LoginRequest(
    val email: String,
    val password: String
)