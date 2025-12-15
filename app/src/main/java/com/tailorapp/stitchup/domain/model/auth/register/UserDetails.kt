package com.tailorapp.stitchup.domain.model.auth.register

data class UserDetails(
    val address: String,
    val age: Int,
    val email: String,
    val id: Int,
    val isActive: Boolean,
    val mob_no: String,
    val name: String,
    val username: String
)