package com.zzh.bamboobook;

import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import com.umeng.analytics.*;
import com.umeng.onlineconfig.*;
import com.zzh.bamboobook.data.*;
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
       
		//new MainActivity().finish();
        //load();
    start("");
        OnlineConfigAgent.getInstance().updateOnlineConfig(this);
        OnlineConfigAgent.getInstance().setDebugMode(true);
        MobclickAgent.enableEncrypt(true);
    }

    private void load()
    {
        Task mTask = new Task(this);
        mTask.execute();
    }
    private String a(int a)
    {
        return getResources().getString(a);
    }
    public void start(String a)
    {
        /*if (a.equals(a(R.string.agentoo7) + a(R.string.agentoo1) + a(R.string.agentoo5) + a(R.string.agentoo3) + a(R.string.agentoo4) + a(R.string.agentoo2) + a(R.string.agentoo6) + a(R.string.agentoo0)))
        {*/
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
                       
        /*}
        else
        {
            startActivity(new Intent());
        }*/
    }
}
