package com.comicsopentrends

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils

import kotlinx.android.synthetic.main.splash.*

/**
 * Created by Juan Martin Bernal on 20/10/2017.
 */

class SplashActivity : Activity() {

    companion object {
        private const val DURATION_SPLASH = 3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        initApp()
    }


    private fun initApp() {
        imgVerse!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.animation))

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, DURATION_SPLASH)
    }

    override fun onPause() {
        super.onPause()
        imgVerse!!.clearAnimation()
    }
}

