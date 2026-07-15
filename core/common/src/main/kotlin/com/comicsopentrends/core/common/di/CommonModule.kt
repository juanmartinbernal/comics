package com.comicsopentrends.core.common.di

import com.comicsopentrends.core.common.DefaultDispatcherProvider
import com.comicsopentrends.core.common.DispatcherProvider
import org.koin.dsl.module

val commonModule = module {
    single<DispatcherProvider> { DefaultDispatcherProvider() }
}
