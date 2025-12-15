package com.tailorapp.stitchup.data

import com.tailorapp.stitchup.constant.ApiConstant.API_KEY
import com.tailorapp.stitchup.constant.ApiConstant.BASE_URL
import com.tailorapp.stitchup.retrofit.ApiKeyInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val client = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(API_KEY))
        .build()

    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}