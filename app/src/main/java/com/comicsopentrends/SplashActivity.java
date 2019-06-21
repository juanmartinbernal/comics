package com.comicsopentrends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Juan Martin Bernal on 20/10/2017.
 */

public class SplashActivity extends Activity {

    @BindView(R.id.imgVerse)
    ImageView imgVerse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initApp();
    }

    private void initApp() {
        imgVerse.startAnimation(AnimationUtils.loadAnimation(this, R.anim.animation));
        int DURACION_SPLASH = 4000;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }

            ;
        }, DURACION_SPLASH);
    }

    @Override
    protected void onPause() {
        super.onPause();
        imgVerse.clearAnimation();
    }
}

