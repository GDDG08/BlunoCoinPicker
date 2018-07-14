package com.zzh.blunocoin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends Activity {
    int SPLASH_TIME = 1000;
    //boolean firsttime = true;

    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.splash_layout)
    RelativeLayout splashLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1f);

//        动画的透明时间以毫秒为单位  5000ms
        alphaAnimation.setDuration(1000);

        // 动画关联到_image_logo ImageView组件上
        imageView2.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                overridePendingTransition(0, android.R.anim.fade_in);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                overridePendingTransition(0, android.R.anim.fade_in);
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intent);
//                finish();
//            }
//        }, SPLASH_TIME);

    }
}
