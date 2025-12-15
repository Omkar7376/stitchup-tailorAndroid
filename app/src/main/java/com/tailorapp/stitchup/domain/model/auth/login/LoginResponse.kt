package com.tailorapp.stitchup.domain.model.auth.login

data class LoginResponse(
    val code: Int,
    val message: String,
    val data: List<LoginResponseData>,

)