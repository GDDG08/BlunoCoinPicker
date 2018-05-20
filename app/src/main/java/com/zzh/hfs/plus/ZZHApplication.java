package com.zzh.hfs.plus;
import android.app.*;
import org.xutils.*;
import com.zzh.hfs.plus.tool.*;
public class ZZHApplication extends Application {
        @Override
        public void onCreate() {
                super.onCreate();
				CrashHandler crashHandler = CrashHandler.getInstance();
				crashHandler.init(getApplicationContext());
                x.Ext.init(this);//Xutils初始化
            }
  /*  private static ZZHApplication mInstance;

    public static ZZHApplication getInstance(){
        if(mInstance == null){
            mInstance = new ZZHApplication();
        }
        return mInstance;
    }*/
    }
