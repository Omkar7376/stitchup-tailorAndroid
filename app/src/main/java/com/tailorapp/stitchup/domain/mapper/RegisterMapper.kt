package com.tailorapp.stitchup.domain.mapper

import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.registerDto.ProfileDetailsDto
import com.tailorapp.stitchup.data.remote.dto.registerDto.RegisterUserResponseDto
import com.tailorapp.stitchup.data.remote.dto.registerDto.UserDetailsDto
import com.tailorapp.stitchup.domain.model.auth.register.ProfileDetails
import com.tailorapp.stitchup.domain.model.auth.register.RegisterUserResponse
import com.tailorapp.stitchup.domain.model.auth.register.UserDetails

fun UserDetailsDto.toUserDetails() = UserDetails(
    id = id,
    username = username,
    name = name,
    age = age,
    email = email,
    mob_no = mob_no,
    address = address,
    isActive = isActive
)

fun ProfileDetailsDto.toProfileDetails() = ProfileDetails(
    id = id,
    bio = bio,
    userId = userId,
)

fun RegisterUserResponseDto.toUserDetails() = RegisterUserResponse(
    profile = profile.map { it.toProfileDetails() },
    user = user.map { it.toUserDetails() }
)