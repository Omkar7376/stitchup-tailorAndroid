package com.tailorapp.stitchup.domain.model.user

data class UserUpdateRequest(
    val address: String,
    val age: Int,
    val email: String,
    val mob_no: String,
    val name: String,
    val password: String,
    val username: String
)