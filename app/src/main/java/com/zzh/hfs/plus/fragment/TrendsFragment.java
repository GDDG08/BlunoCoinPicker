package com.zzh.hfs.plus.fragment;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
//import android.support.v4.app.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;
import com.zzh.hfs.plus.*;
import com.zzh.hfs.plus.data.HTTPCallBack;
import org.json.*;
import com.zzh.hfs.plus.data.*;
import org.xutils.*;
import org.xutils.common.*;
import org.xutils.common.Callback.*;
import com.zzh.hfs.plus.tool.*;
import android.app.Fragment;
import android.app.*;

public class TrendsFragment extends MyFragment
{
	String cookie_name;
	String cookie_value;

	String[] exam_name;
	String[] exam_id;
	Double[] exam_level;
	int[] paper_visible;
	String[] paper_id;

	String paper_visible_id;
	String exam_selected_id;
	int stunum;

    public  String model;

    private View view;

	private OnItemClickListener itlistener;
	private String[] exam_name2;

    private Context context;

    @Override
    public void onResume()
    {
        Varinfo.page = 3;
        super.onResume();
    }

	@Override
	public void onHiddenChanged(boolean hidden)
	{
		if(!hidden){
		Varinfo.page = 3;
			new Handler().postDelayed(new Runnable(){

					@Override
					public void run()
					{
						item(exam_name,itlistener);
					}}, 400);
		
		}
		super.onHiddenChanged(hidden);
	}

	
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Varinfo.page = 3;
        view = inflater.inflate(R.layout.content_trends, container, false);
        context = getActivity();
        switch (Varinfo.model_rank)
		{
            case 1:
                getActivity().setTitle(R.string.title_rank);
				break;
            case 2:
                getActivity().setTitle(R.string.title_ana);
				break;
			case 3:
				getActivity().setTitle(R.string.title_pap);
        }
		//radio();
        content();
        //MT.finish();
        return view;
    }
	
	/*void radio(){
		RadioGroup group=(RadioGroup)view.findViewById(R.id.trends_rgm);
		group.setVisibility(View.VISIBLE);
		//if (Varinfo.onconfig.getConfigParams(context,"rbm_tuijian").equals("2")) {
			RadioButton rbm2=(RadioButton)view.findViewById(R.id.trends_rbm2);
			rbm2.setChecked(true);
		//}
			
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(RadioGroup p1, int p2)
				{
					switch(p2){
						case R.id.trends_rbm1:
							Msg.Snack(view,"方式一");
						break;
						case R.id.trends_rbm2:
							Msg.Snack(view,"方式二");
						break;
					}
				}
		});
	}*/
	
    void content()
    {
		
        HTTP.request(HTTP.trends, view, new HTTPCallBack(){

				private JSONArray trends;

				@Override
				public void onHTTPEnd(String result,int param)
				{
                    try
					{
						JSONObject jsonObj = new JSONObject(result)/*.getJSONObject("data")*/; 

						trends = jsonObj.getJSONArray("data"/*"trends"*/);
						exam_name = new String[trends.length()];
						exam_id = new String[trends.length()];
						exam_level = new Double[trends.length()];

						for (int i=0; i < trends.length(); i++)
						{
							JSONObject mark = (JSONObject)trends.get(i);
							//System.out.println(mark);
							exam_name[i] = mark.getString("name");
							exam_id[i] = mark.getString("examId");
							exam_level[i] = mark.getDouble("level");
						}
					}
					catch (JSONException e)
					{} 

					final ProgressBar progressbar=(ProgressBar) view.findViewById(R.id.trendsProgressBar);
					progressbar.setVisibility(View.INVISIBLE);

					itlistener=new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> l, View v, final int positionv2, long id)
						{
							progressbar.setVisibility(View.VISIBLE);
							exam_selected_id = exam_id[positionv2];
							Varinfo.examId=exam_selected_id;
							Varinfo.examId_o=exam_selected_id;
							

							final String s = (String) l.getItemAtPosition(positionv2);
							Msg.Snack(view, "您在“" + s + "”中成绩超越了" + exam_level[positionv2] + "%的人");
							item(exam_name2,null);
							HTTP.request(HTTP.examoverview, view, new HTTPCallBack(){

									private JSONObject data;

									@Override
									public void onHTTPEnd(String result2,int param)
									{
										
										intent(result2);
										progressbar.setVisibility(View.INVISIBLE);
									}

									private void intent(String result3)
									{
										try
										{
											data = new JSONObject(result3).getJSONObject("data");
										}
										catch (JSONException e)
										{} 

										/*int position=0;
										for (int i=0;i < data.length();i++)
										{
											try
											{
												String id=data.getJSONObject(i).getString("examId");
												if(id.equals(exam_id[positionv2])){
													position=i;
												}
											}
											catch (JSONException e)
											{}
										}*/
										int position=positionv2;
										/*if (Varinfo.model_rank == 2)
										{*/
											Varinfo.exam_ratio = exam_level[position];
											if (position + 1 != exam_level.length)
											{
												Varinfo.exam_ratio2 = exam_level[position + 1];}
											else
											{
												Varinfo.exam_ratio2 = 0d;
											}
										//}
										Varinfo.trends_data = data;
										Varinfo.trends_position = position;
										Varinfo.trends_examName=s;
										Varinfo.trends_org=trends;
										try
										{
											Varinfo.trends_examClassRank = trends.getJSONObject(position).getInt("classRank");
										}
										catch (JSONException e)
										{}
										//Varinfo.examId=exam_selected_id;
										Varinfo.mainactivity.Page(new RankFragment());
									}
								});//二次网络返回
						}};//列表单击
					item(exam_name,itlistener);
				}});//一次网络请求
		exam_name2=new String[1];
		exam_name2[0]="加载中……";
	}
	private void item(String[] s,OnItemClickListener listener)
	{
		ListAdapter adapter0 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, s);
		MyListView listView0 = (MyListView) view.findViewById(R.id.ExamList);
		listView0.setAdapter(adapter0);
		listView0.setOnItemClickListener(listener);
	}

}
