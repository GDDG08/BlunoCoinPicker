package com.zzh.hfs.plus.fragment;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.annotation.*;
import android.support.design.widget.*;
//import android.support.v4.app.*;
import android.support.v7.app.*;
import android.view.*;
import android.support.v7.widget.Toolbar;
import co.mobiwise.materialintro.animation.*;
import co.mobiwise.materialintro.prefs.*;
import com.umeng.onlineconfig.*;
import com.zzh.hfs.plus.*;
import com.zzh.hfs.plus.data.*;
import com.zzh.hfs.plus.tool.*;
import co.mobiwise.materialintro.view.*;
import co.mobiwise.materialintro.shape.*;
import android.widget.*;
import android.app.Fragment;
import android.support.v4.widget.*;
import android.support.v7.widget.*;
import android.text.method.*;
import java.util.*;

public class MainFragment extends MyFragment
{

    private View view;

    private Context context;

    @Override
    public void onResume()
    {
        Varinfo.page = 1;
       // getActivity().setTitle(R.string.app_name);
        super.onResume();
    }

	@Override
	public void onHiddenChanged(boolean hidden)
	{
		if(!hidden){
			Varinfo.page = 1;
			getActivity().setTitle(R.string.app_name);}
		super.onHiddenChanged(hidden);
	}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Varinfo.page = 1;
        view = inflater.inflate(R.layout.content_main, container, false);
        //context = getContext();
        context=getActivity();
        setHasOptionsMenu(true);
        content();
        start_tip(false);
       // MT.finish();
       Varinfo.page_container.setVisibility(View.VISIBLE);
       Varinfo.page_progress.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
    }

    void content()
    {
		OnlineConfigAgent OnlineCA=OnlineConfigAgent.getInstance();

        TextView info = (TextView) view.findViewById(R.id.mainInfoTextView);
        info.setText(OnlineCA.getConfigParams(context, "info"));
		
		final TextView main666=(TextView)view.findViewById(R.id.main666);
		main666.setOnLongClickListener(new View.OnLongClickListener(){
				private Boolean isTest = false;
				@Override
				public boolean onLongClick(View p1)
				{
					main666.setOnClickListener(new View.OnClickListener(){
							
							private int times =0;
							@Override
							public void onClick(View p1)
							{
								if (isTest == true)
								{
									times+=1;
									if(times==6){
										Msg.Snack(view,"您已进入开发者模式");
										Varinfo.showjz=false;
										ad(main666);
									}
								}
							}
						});
					isTest=true;
					new Handler().postDelayed(new Runnable(){

                            @Override
                            public void run()
                            {
                                isTest=false;
                            }}, 5000);
					Toast.makeText(context,"666",1).show();
					return false;
				}
			});
		
		if(OnlineCA.getConfigParams(context,"hfsfree").equals("6")){
			ad(main666);
			
		}
    }

	private void ad(TextView main666)
	{
		main666.setVisibility(View.GONE);
		((CardView)view.findViewById(R.id.hfsfree_cv)).setVisibility(View.VISIBLE);
		TextView weblink = (TextView)view.findViewById(R.id.hfsfree_weblink);
		weblink.setMovementMethod(LinkMovementMethod.getInstance());
		int logosize=((TextView)view.findViewById(R.id.contentmainTextView4)).getHeight() + 5 + weblink.getHeight();
		ImageView logo = (ImageView)view.findViewById(R.id.hfsfree_logo);
		//Toast.makeText(context, logosize + "", 1).show();
		logo.setMaxHeight(logosize);
		logo.setVisibility(View.VISIBLE);
	}

    MaterialIntroListener ls;
    private void start_tip(Boolean force)
    {
        if (force)
        {
            new PreferencesManager(context).resetAll();
            Toast.makeText(context, "开启引导模式成功～", 1).show();
        }
        if (!new PreferencesManager(context).isDisplayed("main_update"))
        {
            ls = new MaterialIntroListener(){

                @Override
                public void onUserClicked(String p1)
                {
                    switch (p1)
                    {
                        case "main_nav":
							RecyclerView d=(RecyclerView) Varinfo.mainactivity.findViewById(R.id.design_navigation_view);
                            View mem = d;
							for (int i = 0; i < d.getChildCount(); i++)
							{
								if (d.getChildAt(i) instanceof LinearLayoutCompat)
								{
									if(((CheckedTextView)((LinearLayoutCompat)d.getChildAt(i)).getChildAt(0)).getText().equals("支持我们(۩⊙o⊙۩)")){
										mem=d.getChildAt(i);
									}
								}
							}
							intro(mem, true, "如果您觉得坏分数PLUS做的不错，可以点这里支持我们，您的捐赠可以支持我们走得更远！","main_mem",null);
                            break;

                        case "main_info":
							intro(getDrawerView(), true, "点击左上角或滑动，显示菜单", "main_nav", ls);
                                break;
						case "main_update":
							View set=getActivity().findViewById(R.id.action_settings);
							intro(set, false, "点击右上角，显示更新日志&引导", "main_info", ls);
							break;
                    }
                }

				private DrawerLayout findViewById(int drawer_layout)
				{
					// TODO: Implement this method
					return null;
				}
            };
			/*FloatingActionButton fb=(FloatingActionButton) getActivity().findViewById(R.id.fab);
			intro(fb, true, "点击右下角浮动按钮，检查更新", "main_update", ls);*/
			intro((TextView)view.findViewById(R.id.mainInfoTextView),true,"欢迎使用坏分数PLUS!","main_update",ls);
        }
        int versionCode=Varinfo.versioncode;
        if (Varinfo.preferences_setting.getBoolean("changelog-" + versionCode, true) || force){
        
            new ChangelogDialog().create(false, Color.BLUE,context, versionCode);
                //.show (new android.support.v4.app.Fragment().getFragmentManager(), "changelog");
        }

        // String tip= OnlineConfigAgent.getInstance().getConfigParams(this, "start-tip");
        if (Varinfo.preferences_setting.getBoolean("start-tip2", true) /*& !tip.equals(0) */|| force)
        {
            AlertDialog dialog= new AlertDialog.Builder(context)
                .setTitle("欢迎使用坏分数")
                .setMessage(getResources().getString(R.string.e_clause))
                .setPositiveButton("授权", null)
                .setNegativeButton("退出", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        new MainActivity().finish();
                    }

                })
                .setNeutralButton("永久授权", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Varinfo.preferences_setting_edit.putBoolean("start-tip2", false);
                        Varinfo.preferences_setting_edit.commit();
                    }

                })
                .setCancelable(false)
                .create();

            dialog.show();
        }
    }
    public void intro(View v, Boolean click, String message, String id, MaterialIntroListener listener)
    {
        new MaterialIntroView.Builder(getActivity())
            .enableDotAnimation(true)
            .enableIcon(true)
            .setFocusGravity(FocusGravity.CENTER)
            .setFocusType(Focus.MINIMUM)
            //.setDelayMillis(100)
            .enableFadeAnimation(true)
            .performClick(click)
            .setInfoText(message)
            .setShape(ShapeType.CIRCLE)
            .setTarget(v)
            .setListener(listener)
            .setUsageId(id) //THIS SHOULD BE UNIQUE ID
            .show();
    }
    public View getDrawerView()
    {
        Toolbar toolbar=(Toolbar) getActivity().findViewById(R.id.toolbar);
        for (int i = 0; i < toolbar.getChildCount(); i++)
        {
            if (toolbar.getChildAt(i) instanceof ImageButton)
            {
                return toolbar.getChildAt(i);
            }
        }
        return null;
    }
}
