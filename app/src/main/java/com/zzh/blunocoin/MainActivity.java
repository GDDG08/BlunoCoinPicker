package com.zzh.blunocoin;

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

import com.google.gson.Gson;
import com.zzh.blunocoin.bluno.BlunoLibrary;
import com.zzh.blunocoin.data.*;
import com.zzh.blunocoin.fragment.*;
import java.io.*;
import java.util.*;
import org.xutils.*;
import org.xutils.common.*;
import org.xutils.http.*;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import com.zzh.blunocoin.tool.*;


public class MainActivity extends BlunoLibrary implements NavigationView.OnNavigationItemSelectedListener//,ColorChooserDialog.ColorCallback
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
        create();

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
        Varinfo.page_container = (FrameLayout) findViewById(R.id.container);
        Varinfo.page_progress = (ProgressBar) findViewById(R.id.PageProgressBar);
		Varinfo.page_scroll = (ScrollView) findViewById(R.id.MainScroll);
		
		Varinfo.hiddenfrag=new ArrayList<Fragment>();
		
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
	 

    @Override
    public void onResume()
    {
        super.onResume();
    }
    @Override
    public void onPause()
    {
        super.onPause();
		//if(!Varinfo.hasservice) finish();
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

        onCreateProcess();                                                        //onCreate Process by BlunoLibrary
        serialBegin(115200);                                                    //set the Uart Baudrate on BLE chip to 115200
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
                    //model(1);
                    //Page(new TrendsFragment());
                }
                else
                {
                    Toast.makeText(this, getString(R.string.drawer_notconnected), Toast.LENGTH_LONG).show();
                    Page(new ConnectFragment());
                }

            }
            else
            {
               // model(1);
               // Page(new TrendsFragment());
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

				//web(this);
                // activity(2, MemberActivity.class, null, true);
				// Page(new MemberFragment());
            }
        }
        else if (id == R.id.nav_slideshow)
        {
            //帮助
            //web("BZ");
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
                Page(new ConnectFragment());
                //Activity(EditActivity.class);
            }
        }
        else if (id == R.id.nav_send)
        {
            //免责
            //web("MZ");
        }
        else if (id == R.id.nav_analysis)
        {

            if (type != 3)
            {
                if (checklogin())
                {

                    //    activity(2, TrendsActivity.class, "analysis", true);
                   // model(2);
                    //Page(new TrendsFragment());
                }
                else
                {
                    Toast.makeText(this, getString(R.string.drawer_notconnected), Toast.LENGTH_LONG).show();
                    Page(new ConnectFragment());
                }
            }
            else
            {
                // activity(2, TrendsActivity.class, "analysis", false);
                //model(2);
                //Page(new TrendsFragment());
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
                    //model(3);
                    //Page(new TrendsFragment());
                }
                else
                {
                    Toast.makeText(this, getString(R.string.drawer_notconnected), Toast.LENGTH_LONG).show();

                    //     activity(2, LoginActivity.class, null, true);
                    Page(new ConnectFragment());
                    //Activity(EditActivity.class);
                }
            }
            else
            {
                // activity(2, TrendsActivity.class, "analysis", false);
                //model(3);
                //Page(new TrendsFragment());
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Boolean checklogin()
    {
        return false;
        //return !Varinfo.preferences_login.getString("cookie-value", "0").equals("0");
    }


	public FragmentTransaction Pagetrans(){
		//  getSupportFragmentManager().beginTransaction().replace(R.id.container, f).commit();
		//setTitle(R.string.app_name);

		// MT.start();
        FragmentTransaction transaction = Varinfo.mainactivity.getFragmentManager().beginTransaction();
        
        /*if (Varinfo.osver >= 19)
		{*/
			transaction.setCustomAnimations(R.animator.scalexy_enter, R.animator.scalexy_exit, 0, 0);//R.animator.slide_ver_right_in, R.animator.slide_hor_left_out, R.animator.slide_hor_left_in, R.animator.slide_ver_right_out);
//        }
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


    @Override
    public void onConectionStateChange(connectionStateEnum theConnectionState) {
        new ConnectFragment().onConectionStateChange(theConnectionState);

    }


    @Override
    public void onSerialReceived(String theString) {
        new ConnectFragment().onSerialReceived(theString);
    }


}
