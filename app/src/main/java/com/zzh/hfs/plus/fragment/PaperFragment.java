package com.zzh.hfs.plus.fragment;

import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.zzh.hfs.plus.*;
import com.zzh.hfs.plus.R;
import com.zzh.hfs.plus.data.*;
import com.zzh.hfs.plus.tool.*;
import org.json.*;
import com.github.chrisbanes.photoview.*;
import java.net.*;
import android.net.*;
import android.app.*;
import lib.kingja.switchbutton.SwitchMultiButton;
import java.util.*;

public class PaperFragment extends MyFragment
{   private View view;
    private Context context;

    private JSONObject result;

    private int position;

//    private Double exam_ratio;

    private int paper_pos;


    
    private String subject;
    private Double realScore;
    private Double score;
	private JSONObject paper_all;
	
	public static String paperId;

	private MyRecyclerView mRecyclerView;

	private DataAdapter mAdapter;
	
	private boolean mistake_mode=false;
	
	private JSONArray json;

//	private JSONArray jso;
	
	//private float ScaleX=0;
	public static int ScaleY=0;

	private DataAdapter.MyItemOnClickListener dalistener;

	private DataAdapter mAdapter2;

	private SwitchMultiButton mSwitchMultiButton;
	
	private boolean delayed=false;
	
	public PaperFragment(){}
	
    public PaperFragment(String p_ID,
						String p_subject,
                        Double p_score,
                        Double p_realScore,
						JSONObject p_paper_all)
	{
		paperId=p_ID;
        subject = p_subject;
        realScore = p_realScore;
        score = p_score;
		paper_all=p_paper_all;

    }
    @Override
    public void onResume()
    {
        Varinfo.page = 8;
        //getActivity().setTitle(R.string.app_name);
        super.onResume();
    }

	@Override
	public void onHiddenChanged(boolean hidden)
	{
		if(!hidden){
			Varinfo.page = 8;
			new Handler().postDelayed(new Runnable(){

					@Override
					public void run()
					{
						mRecyclerView.setAdapter(mAdapter);
						new Handler().postDelayed(new Runnable(){

								@Override
								public void run()
								{
									Varinfo.page_scroll.scrollTo(0,ScaleY);
								}}, 200);
					}}, 400);
			
			}
		super.onHiddenChanged(hidden);
	}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Varinfo.page = 8;
        view = inflater.inflate(R.layout.content_paper, container, false);
        context = getActivity();
        content();
        return view;
    }
    void content()
    {
        result = Varinfo.trends_data;
        position = Varinfo.trends_position;
        //exam_ratio = Varinfo.exam_ratio;
        paper_pos = Varinfo.paper_pos;
		
		mRecyclerView = (MyRecyclerView)view.findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setNestedScrollingEnabled(false);
		
		/*ToggleButton tb=(ToggleButton) view.findViewById(R.id.paper_mistaketoggle);
		tb.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
				
				if (!mistake_mode){
					mistake_mode = true;
				}else{
					mistake_mode = false;
				}
					try
					{
						json(mistake_mode);
					}
					catch (JSONException e)
					{}
				}
			});*/
		
		mSwitchMultiButton = (SwitchMultiButton) view.findViewById(R.id.switchmultibutton);
        assert mSwitchMultiButton != null;
		List<String> list = new ArrayList<String>();
		list.add("全部题目");
		list.add("仅看错题");
        mSwitchMultiButton.setText(list).setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
				@Override
				public void onSwitch(int position, String tabText) {
					
					if (!mistake_mode){
						mistake_mode = true;
					}else{
						mistake_mode = false;
					}
						
					if(json!=null){
						json(json,mistake_mode);
					}else{
						Msg.Snack(view,"数据加载中……");
					}
				}
			});
		dalistener=new DataAdapter.MyItemOnClickListener(){

			@Override
			public void onItemOnClick(View view_i, int pos)
			{
				int real_pos=0;
				if (mistake_mode){
					real_pos=Varinfo.mistake_pos.get(pos);
				}else{
					real_pos=pos;
				}
				try
				{
					JSONObject real_jso=json.getJSONObject(real_pos);

					//替换内容
					mRecyclerView.setAdapter(mAdapter2);
					
					
					//ScaleX=Varinfo.page_scroll.getScaleX();
					//float ScaleY2=Varinfo.page_scroll.getScaleY();
					ScaleY=(int)((int)view_i.getHeight()*pos*1.08);
					//Toast.makeText(context,ScaleY+"",1).show();
					Fragment f=new PaperShowFragment();
					Bundle bundle = new Bundle();
					bundle.putString("json", real_jso.toString());
					f.setArguments(bundle);
					Varinfo.mainactivity.Page(f);
				}
				catch (JSONException e)
				{
					Toast.makeText(context,e.toString(),1).show();
				}

				//Toast.makeText(context,postion+"",1).show();
				/*new AlertDialog.Builder(MainActivity.this)
				 .setMessage(postion).show();*/

				/*PhotoView photoView = (PhotoView) view.findViewById(R.id.photo_view);
				 photoView.setImageURI(Uri.parse("/storage/emulated/0/DCIM/Screenshots/Screenshot_2017-06-11-02-56-23-792_com.learn2crack.recyclerviewsearch.png"));
			 */}
		};
		new Handler().postDelayed(new Runnable(){

				@Override
				public void run()
				{
					delayed=true;
				}}, 700); 
		
		HTTP.request(HTTP.question_detail, view, new HTTPCallBack(){

				@Override
				public void onHTTPEnd(String result2,int param)
				{
					
					try
					{
						String resultjson=new JSONObject(result2)
							.getJSONObject("data")
							.getJSONArray("questionDetail").toString();
							
						StringBuffer stringBuffer = new StringBuffer(resultjson);
						json=new JSONArray(stringBuffer.insert(1, paper_all.toString()+",").toString());
							
						
						//Toast.makeText(context,json.toString(),1).show();
						//new AlertDialog.Builder(context).setMessage(json.toString()).show();
							
						if(json.length()>40){
							Msg.Snack(view,"题目过多，优先显示错题！");
							mistake_mode=true;
							mSwitchMultiButton.setSelectedTab(1);
						}
						if(!delayed){
							new Handler().postDelayed(new Runnable(){

									@Override
									public void run()
									{
							json(json,mistake_mode);
									}}, 600); 
						}
						
						//((SwitchMultiButton)view.findViewById(R.id.switchmultibutton)).setVisibility(View.VISIBLE);
						new Handler().postDelayed(new Runnable(){

								@Override
								public void run()
								{
									((ProgressBar) view.findViewById(R.id.paper_ProgressBar)).setVisibility(View.INVISIBLE);
								}}, 700); 
					}
					catch (JSONException e)
					{
						
					}}});
					
		
		try
		{
			JSONArray ja=new JSONArray("[{\"shortName\":\"加载中\",\"score\":3,\"realScore\":3,\"isWrong\":2},{\"shortName\":\"加载中\",\"score\":3,\"realScore\":3,\"isWrong\":2},{\"shortName\":\"加载中\",\"score\":3,\"realScore\":3,\"isWrong\":2},{\"shortName\":\"加载中\",\"score\":3,\"realScore\":3,\"isWrong\":2},{\"shortName\":\"加载中\",\"score\":3,\"realScore\":3,\"isWrong\":2},{\"shortName\":\"加载中\",\"score\":3,\"realScore\":3,\"isWrong\":2}]");
			mAdapter2 = new DataAdapter(ja, false);
			mAdapter2.setItemOnClickListener(null);
		}
		catch (JSONException e)
		{}
		
	}
	void json(JSONArray jsons,boolean b) {

		mAdapter = new DataAdapter(jsons,b);
		mAdapter.setItemOnClickListener(dalistener);
		mRecyclerView.setAdapter(mAdapter);
		
	}
	
	
	/*
	 
	}*/
}
