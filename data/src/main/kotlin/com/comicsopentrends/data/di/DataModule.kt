package com.comicsopentrends.data.di

import com.comicsopentrends.data.remote.ClanApiService
import com.comicsopentrends.data.repository.ClanRepositoryImpl
import com.comicsopentrends.domain.repository.ClanRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
    single<ClanApiService> { get<Retrofit>().create(ClanApiService::class.java) }
    single<ClanRepository> { ClanRepositoryImpl(api = get(), dispatchers = get()) }
}
