package com.comicsopentrends.rest


import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class AuthInterceptor(private val tokenClans: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $tokenClans")
                .build()
        return chain.proceed(newRequest)
    }
}