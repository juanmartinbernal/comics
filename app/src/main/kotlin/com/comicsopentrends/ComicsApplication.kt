package com.comicsopentrends

import android.app.Application
import com.comicsopentrends.core.common.di.commonModule
import com.comicsopentrends.core.network.ClanApiConfig
import com.comicsopentrends.core.network.networkModule
import com.comicsopentrends.data.di.dataModule
import com.comicsopentrends.domain.di.domainModule
import com.comicsopentrends.feature.clans.di.featureClansModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ComicsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val apiConfig = ClanApiConfig(
            apiToken = BuildConfig.CLAN_API_TOKEN,
            enableHttpLogging = BuildConfig.DEBUG,
        )

        startKoin {
            androidContext(this@ComicsApplication)
            modules(
                commonModule,
                networkModule(apiConfig),
                domainModule,
                dataModule,
                featureClansModule,
            )
        }
    }
}
