package com.tailorapp.stitchup.domain.mapper.authMapper

import com.tailorapp.stitchup.data.remote.dto.loginDto.LoginResponseDataDto
import com.tailorapp.stitchup.data.remote.dto.loginDto.LoginResponseDto
import com.tailorapp.stitchup.data.remote.dto.loginDto.UserDataDto
import com.tailorapp.stitchup.domain.model.auth.login.LoginResponse
import com.tailorapp.stitchup.domain.model.auth.login.LoginResponseData
import com.tailorapp.stitchup.domain.model.auth.login.UserData

fun LoginResponseDto.toLoginResponse() : LoginResponse {
    return LoginResponse(
        code = code,
        message = message,
        data = data.map { it.toLoginResponse() },
    )
}

fun LoginResponseDataDto.toLoginResponse() = LoginResponseData(
    token = token,
    userData = userData.map { it.toLoginResponse() }
)

fun UserDataDto.toLoginResponse() = UserData(
    USER_ID =  USER_ID,
    NAME = NAME,
    EMAIL_ID = EMAIL_ID,
    MOBILE_NO = MOBILE_NO,
    ADDRESS = ADDRESS,
    USERNAME = USERNAME,
    ISACTIVE = ISACTIVE
)