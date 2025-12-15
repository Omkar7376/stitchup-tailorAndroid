package com.tailorapp.stitchup.domain.model.auth.login

data class LoginResponseData(
    val token: String,
    val userData: List<UserData>
)