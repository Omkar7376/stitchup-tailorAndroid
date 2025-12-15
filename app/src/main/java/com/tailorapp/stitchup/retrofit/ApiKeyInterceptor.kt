package com.tailorapp.stitchup.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(
    private val apikey : String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("api-key", apikey)
            .build()

        return chain.proceed(request)
    }
}