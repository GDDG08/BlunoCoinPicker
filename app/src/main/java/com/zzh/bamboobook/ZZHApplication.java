package com.zzh.bamboobook;
import android.app.*;
import org.xutils.*;
import com.zzh.bamboobook.tool.*;

import java.io.File;

public class ZZHApplication extends Application {
    private static ZZHApplication mInstance;

        @Override
        public void onCreate() {
                super.onCreate();
				CrashHandler crashHandler = CrashHandler.getInstance();
				crashHandler.init(getApplicationContext());
                x.Ext.init(this);//Xutils初始化
            mInstance=this;
            }


    public static ZZHApplication getInstance(){
        if(mInstance == null){
            mInstance = new ZZHApplication();
        }
        return mInstance;
    }

    public DbManager getDbManager() {
        DbManager.DaoConfig daoconfig = new DbManager.DaoConfig();
        //默认在data/data/包名/database/数据库名称
        daoconfig.setDbDir(new File("/sdcard"));
        daoconfig.setDbName("test.db");
//        daoconfig.setDbVersion(1);//默认1
        //通过manager进行增删改查
        return x.getDb(daoconfig);
    }
    }
