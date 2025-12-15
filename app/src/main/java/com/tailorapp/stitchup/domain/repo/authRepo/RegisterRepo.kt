package com.tailorapp.stitchup.domain.repo.authRepo

import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.registerDto.RegisterRequestDto
import com.tailorapp.stitchup.domain.model.auth.register.RegisterUserResponse

interface RegisterRepo {
    suspend fun register(request: RegisterRequestDto) : Resource<RegisterUserResponse?>
}