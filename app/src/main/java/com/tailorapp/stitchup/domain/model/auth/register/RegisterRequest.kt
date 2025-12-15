package com.tailorapp.stitchup.domain.model.auth.register

data class RegisterRequest(
    val address: String,
    val age: Int,
    val email: String,
    val mob_no: String,
    val name: String,
    val password: String,
    val username: String
)