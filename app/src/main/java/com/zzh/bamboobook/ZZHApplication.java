package com.zzh.bamboobook;
import android.app.*;
import android.content.Context;
import android.util.Log;

import org.xutils.*;
import org.xutils.db.table.TableEntity;

import com.zzh.bamboobook.tool.*;
import com.zzh.bamboobook.tool.photo.ActivityStack;

import java.io.File;

public class ZZHApplication extends Application {
    private static ZZHApplication mInstance;

    /** 上下文 */
    protected Context mContext          = null;
    /** Activity 栈 */
    public ActivityStack mActivityStack = null;


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
        /*DbManager.DaoConfig daoconfig = new DbManager.DaoConfig();
        //默认在data/data/包名/database/数据库名称
        daoconfig.setDbDir(new File("/sdcard"));
        daoconfig.setDbName("test.db");
//        daoconfig.setDbVersion(1);//默认1
        //通过manager进行增删改查
        return x.getDb(daoconfig);*/

        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                //设置数据库名，默认xutils.db
                .setDbName("myapp.db")
                // 不设置dbDir时, 默认存储在app的私有目录.
                .setDbDir(new File("/sdcard")) // "sdcard"的写法并非最佳实践, 这里为了简单, 先这样写了.
                .setDbVersion(1)//数据库版本

                //设置是否允许事务，默认true
                //.setAllowTransaction(true)

                //设置表创建的监听
                .setTableCreateListener(new DbManager.TableCreateListener(){

                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table)
                    {
                        Log.i("JAVA", "onTableCreated：" + table.getName());
                    }
                })

                //设置数据库更新的监听
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion)
                    {

                    }
                })
                //设置数据库打开的监听
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db)
                    {
                        //开启数据库支持多线程操作，提升性能
                        db.getDatabase().enableWriteAheadLogging();
                    }
                });
        DbManager db = x.getDb(daoConfig);
        return db;
    }
    }
