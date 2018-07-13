package com.zzh.blunocoin;

import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import com.zzh.blunocoin.data.*;
import java.io.*;

public class SplashActivity extends Activity
{
    int SPLASH_TIME=800;
    boolean firsttime=true;
    public static boolean sp=true;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        start("");

    }

    private String a(int a)
    {
        return getResources().getString(a);
    }
    public void start(String a)
    {
                                new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run()
                                        {
                                            overridePendingTransition(0, android.R.anim.fade_in);
                                            Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, SPLASH_TIME);

    }
}
