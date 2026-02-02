package com.tailorapp.stitchup.domain.usecase.auth

import android.util.Log
import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.domain.model.auth.login.LoginResponse
import com.tailorapp.stitchup.domain.repo.authRepo.LoginRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class LoginUseCase(private val logiRepo: LoginRepo) {
    fun login(email: String, password: String) : Flow<Resource<LoginResponse>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(logiRepo.login(email,password)))
            Log.d("###", "login: ${logiRepo.login(email,password)}")
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                400 -> "Invalid email or password"
                401 -> "Unauthorized access"
                404 -> "User not found"
                500 -> "Server Error. Please try again later."
                else -> e.message() ?: "Something went wrong"
            }
            emit(Resource.Error(errorMessage))
        } catch (e: IOException) {
            emit(Resource.Error("Could not reach server. Check your internet connection."))

        } catch (e : Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}