package com.tailorapp.stitchup.domain.usecase.auth

import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.registerDto.RegisterRequestDto
import com.tailorapp.stitchup.domain.model.auth.register.RegisterUserResponse
import com.tailorapp.stitchup.domain.repo.authRepo.RegisterRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RegisterUseCase(private val registerRepo: RegisterRepo) {
    fun register(request: RegisterRequestDto) : Flow<Resource<RegisterUserResponse?>> = flow {
        emit(Resource.Loading())
        try {
            emit(registerRepo.register(request))
        } catch (e : Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}