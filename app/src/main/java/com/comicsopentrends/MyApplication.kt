package com.comicsopentrends

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

/**
 * Created by Asus on 20/10/2017.
 */

class MyApplication : MultiDexApplication() {

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }
}
