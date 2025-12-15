package com.tailorapp.stitchup.domain.model.auth.login

data class UserData(
    val ADDRESS: String,
    val EMAIL_ID: String,
    val ISACTIVE: Boolean,
    val MOBILE_NO: String,
    val NAME: String,
    val USERNAME: String,
    val USER_ID: Int
)