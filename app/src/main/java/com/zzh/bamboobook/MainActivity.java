package com.zzh.bamboobook;

import android.a.k.*;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.view.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
//import com.afollestad.materialdialogs.color.*;
import com.umeng.analytics.*;
import com.zzh.bamboobook.activity.EditActivity;
import com.zzh.bamboobook.activity.ReviewActivity;
import com.zzh.bamboobook.data.*;
import com.zzh.bamboobook.fragment.*;
import java.io.*;
import java.util.*;
import org.xutils.*;
import org.xutils.common.*;
import org.xutils.http.*;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import com.zzh.bamboobook.tool.*;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener//,ColorChooserDialog.ColorCallback
{

    //private Context cont;
	/* @Override
	 public void onColorSelection(ColorChooserDialog p1, int p2)
	 {
	 Varinfo.analysisfrag.onColorSelection(p1,p2);
	 }

	 @Override
	 public void onColorChooserDismissed(ColorChooserDialog p1)
	 {}
	 */
    public static boolean cp=true;

    /* @Override
     protected void onDestroy() {
     super.onDestroy();
     // 展示广告条窗口的 onDestroy() 回调方法中调用
     BannerManager.getInstance(this).onDestroy();
     }*/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		if(savedInstanceState!=null)Varinfo.load(savedInstanceState);
        getVersionName();
        getVersionCode();

        create();
        //get();
        update2();

        sharepre();
        sharepre_edit();
        cookie();
        //Varinfo.toolbar=(Toolbar) findViewById(R.id.toolbar);
        Varinfo.mainactivity = this;
		if (savedInstanceState == null)
		{
			Fragment m=new MainFragment();
       		getFragmentManager()
            	.beginTransaction()
            	.replace(R.id.container, m,m.getClass().getName())
            	.commit();
			Varinfo.nowfrag = m;
			setTitle(R.string.app_name);
		}
        Varinfo.agent = Varinfo.onconfig.getConfigParams(this, "user");
        if (Varinfo.agent.length() < 3)
		{
            Varinfo.agent = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; BOIE9;ZHCN)";
        }
        Varinfo.active_user = Varinfo.preferences_login.getString("active_nick", "未登录");
		Varinfo.pre_offline = getSharedPreferences("offline", MODE_PRIVATE);
        Varinfo.page_container = (FrameLayout) findViewById(R.id.container);
        Varinfo.page_progress = (ProgressBar) findViewById(R.id.PageProgressBar);
		Varinfo.page_scroll = (ScrollView) findViewById(R.id.MainScroll);
		
		Varinfo.hiddenfrag=new ArrayList<Fragment>();
		Varinfo.osver = o0oo.thread();
		
		//Varinfo.hiddenfrag_scroll=new ArrayList<Integer>();
		/* byte[] b=new oo0o().textview(this);
		 byte[] t=getResources().getString(R.string.e_clause).getBytes();
		 String bm=SubjectAI.encryptionMD5(b);
		 String tm=SubjectAI.encryptionMD5(t);
		 new AlertDialog.Builder(this).setMessage(bm+"\n"+tm)
		 .show();*/
		 //varinfo测试
		//Varinfo.load(Varinfo.save(new Bundle()));
		
    }

	/*@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		// TODO: Implement this method
		super.onConfigurationChanged(newConfig);
		Fragment m=new MainFragment();
		getFragmentManager()
			.beginTransaction()
			.replace(R.id.container, m,m.getClass().getName())
			.commit();
		Varinfo.nowfrag = m;
	}*/
	
	@Override
	protected void onSaveInstanceState(Bundle outStat)
	{
		// TODO: Implement this method
		Bundle outState=Varinfo.save(outStat);
		super.onSaveInstanceState(outState);
	}
	


    /*    @Override
     public boolean onCreateOptionsMenu(Menu menu)
     {
     getMenuInflater().inflate(R.menu.main, menu);
     return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item)
     {
     int id = item.getItemId();
     if (id == R.id.action_settings)
     {
     start_tip(true);
     return true;
     }
     return super.onOptionsItemSelected(item);
     }*/

	/* public void get()
	 {
	 RequestParams params2 = new RequestParams("http://hfs-be.yunxiao.com"); 
	 params2.setUseCookie(false);

	 x.http().get(params2, new Callback.CommonCallback<String>() {

	 private String msg;
	 @Override
	 public void onSuccess(String result)
	 {}

	 @Override
	 public void onError(Throwable ex, boolean isOnCallback)
	 {
	 Toast.makeText(x.app(), "无法连接到服务器！请重试！", Toast.LENGTH_LONG).show();
	 finish();
	 }

	 @Override
	 public void onCancelled(CancelledException cex)
	 {
	 Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
	 }

	 @Override
	 public void onFinished()
	 {}
	 });
	 }*/
	 
	 
    private void sharepre()
    {
        Varinfo.preferences_login = getSharedPreferences("login", MODE_PRIVATE);
        Varinfo.preferences_user = getSharedPreferences("user2", MODE_PRIVATE);
        Varinfo.preferences_setting = getSharedPreferences("settings", MODE_PRIVATE);

    }
    public void sharepre_edit()
    {
        Varinfo.preferences_login_edit = Varinfo.preferences_login.edit();
        Varinfo.preferences_user_edit = Varinfo.preferences_user.edit();
        Varinfo.preferences_setting_edit = Varinfo.preferences_setting.edit();
    }

    @Override
    public void onResume()
    {
        super.onResume();
		Varinfo.hasservice=false;
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause()
    {
        super.onPause();
		//if(!Varinfo.hasservice) finish();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
       // finish();
    }

    @Override
    protected void onDestroy()
    {
		//finish();
        super.onDestroy();
    }
    public void create()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
					//测试
					//startActivity(null);
					//
                    Snackbar.make(view, "当前版本：" + getVersionName(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                    update2();
                }
            });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int type=Varinfo.page;
        int id = item.getItemId();
        if (id == R.id.nav_camera)
        {

            //  activity(2, MainActivity.class, null, false);
            if (type == 1)
			{

            }
			else
			{
				Page(new MainFragment());
				setTitle(R.string.app_name);
			}

        }
        else if (id == R.id.nav_gallery)
        {
            if (type != 3)
            {
                if (checklogin())
                {
                    //   activity(2, TrendsActivity.class, "trends", true);  
                    model(1);
                    //Page(new TrendsFragment());
                    Activity(ReviewActivity.class);
                }
                else
                {
                    Toast.makeText(this, "请先登录！", 1).show();
                    //   activity(2, LoginActivity.class, null, true);
                    //Page(new NewNoteFragment());
                    Activity(EditActivity.class);
                }

            }
            else
            {
                //    activity(2, TrendsActivity, "trends", false);
                model(1);
               // Page(new TrendsFragment());
                Activity(ReviewActivity.class);
            }
        }
        else if (id == R.id.nav_jifen)
        {
            if (type == 4 | type == 3)
            {
                // activity(2, MainActivity.class, null, true);
                Page(new MainFragment());
            }
            if (type != 5)
            {
				Varinfo.showjz = true;
				web(this);
                // activity(2, MemberActivity.class, null, true);
				// Page(new MemberFragment());
            }
        }
        else if (id == R.id.nav_slideshow)
        {
            //帮助
            web("BZ");
        }
        else if (id == R.id.nav_manage)
        {
            Intent intent = new Intent();
            intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + "vGFOdRd33MWoCnefFhpoJD4gPmg7O-kt"));
            // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try
            {
                startActivity(intent);
                return true;
            }
            catch (Exception e)
            {
                Toast.makeText(this, "未安装QQ或安装的版本不支持", Toast.LENGTH_LONG).show();
                return false;
            }


        }
        else if (id == R.id.nav_share)
        {                     
            if (type == 4 | type == 3)
            {
                //  activity(2, MainActivity.class, null, true);
                Page(new MainFragment());
            }
            if (type != 2)
            {
                //  activity(2, LoginActivity.class, null, true);
                //Page(new NewNoteFragment());
                Activity(EditActivity.class);
            }
        }
        else if (id == R.id.nav_send)
        {
            //免责
            web("MZ");
        }
        else if (id == R.id.nav_analysis)
        {

            if (type != 3)
            {
                if (checklogin())
                {

                    //    activity(2, TrendsActivity.class, "analysis", true);
                    model(2);
                    Page(new TrendsFragment());
                }
                else
                {
                    Toast.makeText(this, "请先登录！", 1).show();

                    //     activity(2, LoginActivity.class, null, true);
                    //Page(new LoginFragment());
                    Activity(EditActivity.class);
                }
            }
            else
            {
                // activity(2, TrendsActivity.class, "analysis", false);
                model(2);
                Page(new TrendsFragment());
            }
			//Toast.makeText(this,"试题分析功能正在更新中，暂时不可用哦！",1).show();
        }
		else if (id == R.id.nav_paperdetail)
        {

            if (type != 3)
            {
                if (checklogin())
                {

                    //    activity(2, TrendsActivity.class, "analysis", true);
                    model(3);
                    Page(new TrendsFragment());
                }
                else
                {
                    Toast.makeText(this, "请先登录！", 1).show();

                    //     activity(2, LoginActivity.class, null, true);
                   // Page(new LoginFragment());
                    Activity(EditActivity.class);
                }
            }
            else
            {
                // activity(2, TrendsActivity.class, "analysis", false);
                model(3);
                Page(new TrendsFragment());
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

	public FragmentTransaction Pagetrans(){
		//  getSupportFragmentManager().beginTransaction().replace(R.id.container, f).commit();
		//setTitle(R.string.app_name);

		// MT.start();
        FragmentTransaction transaction = Varinfo.mainactivity.getFragmentManager().beginTransaction();
        
        if (Varinfo.osver >= 19)
		{
			transaction.setCustomAnimations(R.animator.scalexy_enter, R.animator.scalexy_exit, 0, 0);//R.animator.slide_ver_right_in, R.animator.slide_hor_left_out, R.animator.slide_hor_left_in, R.animator.slide_ver_right_out);
        }
		return transaction;
	}
	
    public void Page(Fragment f)
    {
		//Varinfo.hiddenfrag_scroll.add(Varinfo.page_scroll.getScrollY());
		
//可能Varinfo.nowfrag存在null问题
		FragmentTransaction transaction=Pagetrans();
        //transaction.replace(R.id.container, f);
		if (Varinfo.nowfrag == null)
		{
			MainFragment mainFragment = new MainFragment();
			transaction.replace(R.id.container, mainFragment/*, mainFragment.getClass().getName()*/);
			//transaction.addToBackStack(mainFragment.getClass().getName());
		}
		else
		{
			transaction.hide(Varinfo.nowfrag);
			Varinfo.hiddenfrag.add(Varinfo.nowfrag);
			
			if (f.isAdded())
			{
				transaction.show(f);
			}
			else
			{
				transaction.add(R.id.container, f/*,f.getClass().getName()*/);
			}
			//transaction.addToBackStack(f.getClass().getName());
		}
        
        transaction.commit();
		Varinfo.nowfrag = f;
		
		new Handler().postDelayed(new Runnable(){

				@Override
				public void run()
				{
					Varinfo.page_scroll.fullScroll(ScrollView.FOCUS_UP);
				}}, 400);
    }
    public void Activity(Class a){
        Intent intent=new Intent(this, a);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


    private void model(int model)
    {
        Varinfo.model_rank = model;
    }

    public void web(String type)
    {
        String url="http://huaifenshu.com";
        switch (type)
        {
            case "MZ":
                url += "/hfs/exeception_clause/";
                break;
            case "BZ":
                url = "http://www.coolapk.com/apk/com.zzh.bamboobook";
                break;
            case "KM":
                url += "/hfs/km";
                break;
            case "SJ":
				String ur=Varinfo.onconfig.getConfigParams(this, "url");
				if (ur.length() < 1)
				{
					url = "http://www.coolapk.com/apk/com.zzh.bamboobook";
				}
				else
				{
					url = ur;}
        }

        Intent intent= new Intent();        
        intent.setAction("android.intent.action.VIEW");    
        Uri content_url = Uri.parse(url);   
        intent.setData(content_url);  
        startActivity(intent);
    }


    public int getVersionCode()
    {
        PackageManager manager = getPackageManager();//获取包管理器
        try
        {
            //通过当前的包名获取包的信息
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);//获取包对象信息
            Varinfo.versioncode = info.versionCode;
            return  info.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public String getVersionName()
    {
        PackageManager manager = getPackageManager();
        try
        {
            //第二个参数代表额外的信息，例如获取当前应用中的所有的Activity
            PackageInfo packageInfo = manager.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            Varinfo.versionname = packageInfo.versionName;
            return packageInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onBackPressed()
    {   DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            if (Varinfo.page == 1)
            {
                /*QMCPConnect.getQumiConnectInstance(this)
				 .showQuMiExitAd(this, new QMExitListener() {
				 @Override
				 public void onExit() {
				 MainActivity.super.onBackPressed();*/
				exitBy2Click();
				// }});
            }
            else
            {
				
				//MainActivity.this.getFragmentManager().popBackStack();
				//FragmentManager fragmentManager = getFragmentManager();
				//String tag= fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1).getName();
				int pos = Varinfo.hiddenfrag.size() - 1;
				//final int pos_s = Varinfo.hiddenfrag_scroll.size() - 1;
				Fragment last=Varinfo.hiddenfrag.get(pos);
				Varinfo.hiddenfrag.remove(pos);
				
				Pagetrans().remove(Varinfo.nowfrag).show(last).commit();
				Varinfo.nowfrag=last;
				
				/*new Handler().postDelayed(new Runnable(){

						@Override
						public void run()
						{
							Varinfo.page_scroll.scrollTo(0,Varinfo.hiddenfrag_scroll.get(pos_s));

						}}, 200);
				Varinfo.hiddenfrag_scroll.remove(pos_s);*/
                //super.onBackPressed();
            }
        }

    }
    private static Boolean isExit = false;
    private void exitBy2Click()
    {
        Timer tExit = null;
        if (isExit == false)
        {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出坏分数PLUS", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                    @Override
                    public void run()
                    {
                        isExit = false; // 取消退出
                    }
                }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        }
        else
        {
            //super.onBackPressed();
            Intent intent=new Intent();
            //设置Intent的动作，动作是作为初始Activity显示出来
            intent.setAction(Intent.ACTION_MAIN);
            //设置Intent的种类，让桌面作为初始的Activity显示出来
            intent.addCategory(Intent.CATEGORY_HOME);   
            startActivity(intent);
            /*   PointsManager.getInstance(this).unRegisterNotify(new PointsChangeNotify(){

             @Override
             public void onPointBalanceChange(float p1){}
             });

             SpotManager.getInstance(this).onAppExit();
             BannerManager.getInstance(this).onDestroy();
             AppConnect.getInstance(this).close();
             */
            //System.exit(0);
        }
    }


    public void cookie()
    {
        Varinfo.cookie_value = Varinfo.preferences_login.getString("cookie-value", "0");
    }

    public Boolean checklogin()
    {
        return true;
        //return !Varinfo.preferences_login.getString("cookie-value", "0").equals("0");
    }

    public void update2()
    {/*
        try
        {
            String value=Varinfo.onconfig.getConfigParams(this, "newcode");
            int newversion =Integer.valueOf(value).intValue();

            if (newversion > getVersionCode())//versioncode)
            {
                Toast.makeText(this, "检测到新版本", 1).show();

                String force_text="暂不更新";
                DialogInterface.OnClickListener force_click=null;
                if (Varinfo.onconfig.getConfigParams(this, "force").equals("1"))
                {
                    force_text = "退出";
                    force_click = new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            finish();
                        }};
                }
                new AlertDialog.Builder(this)
                    .setTitle("检测到新版本")
                    //.setView(MyDialog.InputDialog(RankActivity.this))
                    .setMessage(Varinfo.onconfig.getConfigParams(this, "vername") + "版本发布啦！\n\n●更新日志：\n" + Varinfo.onconfig.getConfigParams(this, "changelog") + "\n\n●点击立即更新↘")
                    .setPositiveButton("立即更新", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which)
                        {
                            if (Varinfo.onconfig.getConfigParams(MainActivity.this, "url-down2").equals("1"))
                            {

                                final String filePath=Environment.getExternalStorageDirectory().getPath() + "/HFS-PLUS.apk";

                                File apk=new File(filePath);
                                if (apk.exists())
								{
                                    apk.delete();
                                }


                                RequestParams params = new RequestParams(Varinfo.onconfig.getConfigParams(MainActivity.this, "url"));
                                params.setAutoRename(true);//断点下载
                                params.setSaveFilePath(filePath);
                                x.http().get(params, new Callback.ProgressCallback<File>() {
                                        private ProgressDialog progressDialog;
                                        @Override
                                        public void onCancelled(CancelledException arg0)
                                        {}
                                        @Override
                                        public void onError(Throwable arg0, boolean arg1)
                                        {
                                            if (progressDialog != null && progressDialog.isShowing())
                                            {
                                                progressDialog.dismiss();
                                            }
                                            Toast.makeText(MainActivity.this, "更新失败,请手动更新！", 1).show();
                                            web("SJ");
                                            update2();
                                        }

                                        @Override
                                        public void onFinished()
                                        {}

                                        @Override
                                        public void onSuccess(File arg0)
                                        {
                                            if (progressDialog != null && progressDialog.isShowing())
                                            {
                                                progressDialog.dismiss();
                                            }
                                            Intent intent = new Intent(Intent.ACTION_VIEW);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
                                            startActivity(intent);
                                            update2();
                                        }

                                        @Override
                                        public void onLoading(long arg0, long arg1, boolean arg2)
                                        {
                                            progressDialog.setMax((int)arg0);
                                            progressDialog.setProgress((int)arg1);
                                        }

                                        @Override
                                        public void onStarted()
                                        {
                                            progressDialog = new ProgressDialog(MainActivity.this);
                                            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置为水进行条
                                            progressDialog.setMessage("拼命下载中...");
                                            progressDialog.setProgress(0);
                                            progressDialog.setCancelable(false);
                                            progressDialog.show();
                                        }

                                        @Override
                                        public void onWaiting()
                                        {}
                                    });
                            }
                            else
                            {
                                web("SJ");
                                finish();
                            }
                        }
                    })
                    .setNegativeButton(force_text, force_click)
                    .setCancelable(false)
                    .show();
            }
            else
            {
                Toast.makeText(this, "已是最新版本", 1).show();
            }
        }
        catch (NumberFormatException e)
        {}*/
    }

	public void web(final Context c)
    {
		if (Varinfo.showjz)
		{
			AlertDialog adlog=new AlertDialog.Builder(c)
				.setView(R.layout.dialog_km)
				.setNegativeButton("更多信息", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						Intent intent= new Intent();        
						intent.setAction("android.intent.action.VIEW");    
						Uri content_url = Uri.parse("http://huaifenshu.com/");   
						intent.setData(content_url);  
						c.startActivity(intent);
					}})
				.setPositiveButton("残忍拒绝", null)
				.setNeutralButton("本次不再提示", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						Varinfo.showjz = false;
					}
				})
				.setTitle("捐赠支持我们")
				.show();
			Window dia=adlog.getWindow();
			Button wx1=(Button) dia.findViewById(R.id.KM_wx_1);
			wx1.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View p1)
					{
						save1(c, R.drawable.km_hans, "tencent.mm");
					}
				});
			/*Button qq1=(Button) dia.findViewById(R.id.KM_qq_1);
			qq1.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View p1)
					{
						save(c, R.drawable.km_qq, "mobileqq");
					}
				});*/
			Button qq2=(Button) dia.findViewById(R.id.KM_qq_2);
			qq2.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View p1)
					{
						/*if (Math.random() > 0.5)
						{*/
							save1(c, R.drawable.km_mj, "mobileqq");
							/*}
						else
						{
							save(c, R.drawable.km_qq, "mobileqq");
						}*/
					}
				});
		}
    }

	void save1(Context c, int id, String str){

		Resources res = this.getResources();
		BitmapDrawable d = (BitmapDrawable) res.getDrawable(id);
		
		save(c,d,str,false);
		
		Toast.makeText(this, "二维码图片已自动保存到相册，扫一扫即可支持我们", 1).show();
		openApp(c, str);
	}
	
    public static void save(Context c, BitmapDrawable d, String str,boolean paper)
    {
		Bitmap img = d.getBitmap();

		String fn="";
		if(paper){
			fn+="坏分数试卷图片/";
		}
		
		String path = Environment.getExternalStorageDirectory().getPath() + "/" + fn;
		
		if(paper){
			File f= new File(path);
			if(!f.exists()){
				f.mkdirs();
			}
		}
		path += "hfsplus-" + str + ".jpg";
		try
		{
			OutputStream os = new FileOutputStream(path);
			img.compress(Bitmap.CompressFormat.JPEG, 100, os);
			os.close();
			if(paper){
				Toast.makeText(c,"试卷已保存到："+path,1).show();
			}
		}
		catch (Exception e)
		{
			Toast.makeText(c,e.toString(),1).show();
			//Log.e("TAG", "", e);
		}

		
    }
	private void openApp(Context mContext, String str)
	{  
        //应用过滤条件  
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);  
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);  
        PackageManager mPackageManager = mContext.getPackageManager();  
        List<ResolveInfo> mAllApps = mPackageManager.queryIntentActivities(mainIntent, 0);  
        //按报名排序  
        Collections.sort(mAllApps, new ResolveInfo.DisplayNameComparator(mPackageManager));  

        for (ResolveInfo res : mAllApps)
		{  
            //该应用的包名和主Activity  
            String pkg = res.activityInfo.packageName;  
            String cls = res.activityInfo.name;  
            //System.out.println("pkg---" + pkg);  
			// System.out.println("打印出来的----" + str);  

            // 打开QQ pkg中包含"qq"，打开微信，pkg中包含"mm"  
            if (pkg.contains(str))
			{  
                //ComponentName componet = new ComponentName(pkg, cls);  
                Intent intent = new Intent();  
                //intent.setComponent(componet);  
				intent.setAction(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
				intent.setClassName(pkg, cls);
                mContext.startActivity(intent);  
				break;
            }  
        }
	}
}
