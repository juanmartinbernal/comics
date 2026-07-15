package com.comicsopentrends.core.network

import okhttp3.Interceptor
import okhttp3.Response

/** Añade la cabecera `Authorization: Bearer <token>` exigida por la Clash of Clans API. */
internal class AuthInterceptor(private val config: ClanApiConfig) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${config.apiToken}")
            .build()
        return chain.proceed(request)
    }
}
