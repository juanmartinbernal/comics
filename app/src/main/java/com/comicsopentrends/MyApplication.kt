package com.comicsopentrends

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication

/**
 * Created by Asus on 20/10/2017.
 */

class MyApplication : MultiDexApplication() {

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }
}
