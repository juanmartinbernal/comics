package com.comicsopentrends.feature.clans.di

import com.comicsopentrends.feature.clans.detail.ClanDetailViewModel
import com.comicsopentrends.feature.clans.list.ClanListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val featureClansModule = module {
    viewModelOf(::ClanListViewModel)
    viewModelOf(::ClanDetailViewModel)
}
