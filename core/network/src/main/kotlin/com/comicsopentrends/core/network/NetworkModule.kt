package com.comicsopentrends.core.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Módulo Koin encargado de proveer las dependencias de red (Moshi, OkHttp y Retrofit).
 * Recibe la [ClanApiConfig] ya resuelta (con el token de la API) para poder construir
 * el cliente HTTP autenticado.
 */
fun networkModule(config: ClanApiConfig) = module {
    single { config }

    single<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(get()))
            .apply {
                if (config.enableHttpLogging) {
                    addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                }
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(get<ClanApiConfig>().baseUrl)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
}
