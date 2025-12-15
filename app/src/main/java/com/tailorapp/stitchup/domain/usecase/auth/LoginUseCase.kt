package com.tailorapp.stitchup.domain.usecase.auth

import android.util.Log
import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.domain.model.auth.login.LoginResponse
import com.tailorapp.stitchup.domain.repo.authRepo.LoginRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginUseCase(private val logiRepo: LoginRepo) {
    fun login(email: String, password: String) : Flow<Resource<LoginResponse>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(logiRepo.login(email,password)))
            Log.d("###", "login: ${logiRepo.login(email,password)}")
        } catch (e : Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)


}