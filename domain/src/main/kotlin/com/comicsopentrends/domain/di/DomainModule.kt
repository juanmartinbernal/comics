package com.comicsopentrends.domain.di

import com.comicsopentrends.domain.usecase.GetClanDetailUseCase
import com.comicsopentrends.domain.usecase.GetClansUseCase
import com.comicsopentrends.domain.usecase.SearchClansUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetClansUseCase(get()) }
    factory { SearchClansUseCase(get()) }
    factory { GetClanDetailUseCase(get()) }
}
