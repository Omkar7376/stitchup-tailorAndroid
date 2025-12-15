package com.tailorapp.stitchup.domain.repo.authRepo

import com.tailorapp.stitchup.domain.model.auth.login.LoginResponse

interface LoginRepo {
    suspend fun login(email: String, password: String): LoginResponse
}