package com.zzh.hfs.plus.fragment;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
//import android.support.v4.app.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.zzh.hfs.plus.*;
import com.zzh.hfs.plus.charts.*;
import com.zzh.hfs.plus.data.*;
import org.json.*;
import org.xutils.http.*;
import org.xutils.*;
import org.xutils.common.*;
import org.xutils.common.Callback.*;
import android.app.*;
import com.zzh.hfs.plus.tool.*;
import java.math.*;
import java.text.*;
import java.util.*;

public class AnaSFragment extends MyFragment
{   private View view;
    private Context context;

    private JSONObject result;

    private int position;

//    private Double exam_ratio;

    private int paper_pos;

    private JSONObject choosen_subject;

    private SharedPreferences pre_settings;

    public static int color_radar;
    public static int color_radar_bak;
    public static int color_bar_pre;
    public static int color_bar_now;
    private int color_radar_text;

    private String subject;
    private Double realScore;
    private Integer classRank;
    private Integer gradeRank;
    private Double classMaxScore;
    private Double gradeMaxScore;
   // private Double classAvg;…
   // private Double gradeAvg;

	//public static String chosen_paperId;

	public AnaSFragment(){}
	
    public AnaSFragment(String p_subject,
                        Double p_realScore,
                        int p_classRank,
                        int p_gradeRank,
                        Double p_classMaxScore,
                        Double p_gradeMaxScore
                       // Double p_classAvg,
                       // Double p_gradeAvg
					   )
	{
        subject = p_subject;
        realScore = p_realScore;
        classRank = p_classRank;
        gradeRank = p_gradeRank;
        classMaxScore = p_classMaxScore;
        gradeMaxScore = p_gradeMaxScore;
       // classAvg = p_classAvg;
        //gradeAvg = p_gradeAvg;

    }
    @Override
    public void onResume()
    {
        Varinfo.page = 7;
        //getActivity().setTitle(R.string.app_name);
        super.onResume();
    }

	@Override
	public void onHiddenChanged(boolean hidden)
	{
		if(!hidden){
			Varinfo.page = 7;}
		super.onHiddenChanged(hidden);
	}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Varinfo.page = 7;
        view = inflater.inflate(R.layout.content_anas, container, false);
        context = getActivity();
        //setHasOptionsMenu(true);
        content();
		// MT.finish();
		// Varinfo.analysisfrag = this;
        return view;
    }
	/*
	 @Override
	 public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	 {
	 inflater.inflate(R.menu.color, menu);
	 super.onCreateOptionsMenu(menu, inflater);
	 if (!new PreferencesManager(context).isDisplayed("analysis_color_button"))
	 {
	 new Handler().postDelayed(new Runnable() {
	 @Override
	 public void run()
	 {
	 View color=getActivity().findViewById(R.id.action_color);
	 // intro(color, true, "欢迎！坏分数PLUS为您提供了个性化功能，点击开始享受自己的色彩空间！", "analysis_color_button", null);
	 new MaterialIntroView.Builder(getActivity())
	 .enableDotAnimation(true)
	 .enableIcon(true)
	 .setFocusGravity(FocusGravity.CENTER)
	 .setFocusType(Focus.MINIMUM)
	 //.setDelayMillis(100)
	 .enableFadeAnimation(true)
	 .performClick(true)
	 .setInfoText("欢迎！坏分数PLUS为您提供了个性化功能，点击开始享受自己的色彩空间！")
	 .setShape(ShapeType.CIRCLE)
	 .setTarget(color)
	 //.setListener(listener)
	 .setUsageId("analysis_color_button") //THIS SHOULD BE UNIQUE ID
	 .show();
	 }
	 }, 500);
	 }
	 }

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item)
	 {
	 ONColorSettings(null);
	 return super.onOptionsItemSelected(item);
	 }*/

    void content()
    {
        result = Varinfo.trends_data;
        position = Varinfo.trends_position;
        //exam_ratio = Varinfo.exam_ratio;
        paper_pos = Varinfo.paper_pos;

        //头部信息表
		TextView name =(TextView) view.findViewById(R.id.anas_papername);
		try
		{
			name.setText(result.getJSONArray("papers").getJSONObject(paper_pos).getString("name"));
		}
		catch (JSONException e)
		{}
		TextView nick =(TextView) view.findViewById(R.id.anas_nick);
		nick.setText(Varinfo.preferences_login.getString("active_nick", null)+nick.getText());
		
		TextView score = (TextView) view.findViewById(R.id.anas_score);
        score.setText(realScore.toString());
        TextView rank_class=(TextView) view.findViewById(R.id.anas_rank_class);
       // rank_class.setText(rank_class.getText() + classRank.toString());
        TextView rank_grade=(TextView) view.findViewById(R.id.anas_rank_grade);
        rank_grade.setText(rank_grade.getText() + gradeRank.toString());
        TextView high_class=(TextView) view.findViewById(R.id.anas_high_class);
        high_class.setText(high_class.getText() + classMaxScore.toString());
        TextView high_grade=(TextView) view.findViewById(R.id.anas_high_grade);
        high_grade.setText(high_grade.getText() + gradeMaxScore.toString());
        TextView aver_class=(TextView) view.findViewById(R.id.anas_aver_class);
        //aver_class.setText(aver_class.getText() + classAvg.toString());
        TextView aver_grade=(TextView) view.findViewById(R.id.anas_aver_grade);
        //aver_grade.setText(aver_grade.getText() + gradeAvg.toString());

       // HorizontalScrollView sco=(HorizontalScrollView)view.findViewById(R.id.anas_sco_area1);
       // sco.setOverScrollMode(View.OVER_SCROLL_NEVER);
        HorizontalScrollView sco2=(HorizontalScrollView)view.findViewById(R.id.anas_sco_area2);
        sco2.setOverScrollMode(View.OVER_SCROLL_NEVER);		


        pre_settings = context.getSharedPreferences("settings", context.MODE_PRIVATE);
        color_radar = pre_settings.getInt("color_radar", getResources().getColor(R.color.color_radar));
        //color_radar_bak = pre_settings.getInt("color_radar_bak", getResources().getColor(R.color.color_radar_bak));
        //color_bar_pre = pre_settings.getInt("color_bar_pre", getResources().getColor(R.color.color_bar_pre));
        //color_bar_now = pre_settings.getInt("color_bar_now", getResources().getColor(R.color.color_bar_now));
        color_radar_text = pre_settings.getInt("color_radar_text", getResources().getColor(R.color.color_radar_text));

        try
        {
            json();
        }
        catch (JSONException e)
        {}
		
		/*//test
		String[] eventTime3=new String[]{"17-1-1","17-1-2","17-1-3"};
		Double[] grade_ratio=new Double[]{98.05,95.55,80.76,50.9};
		charts_area(eventTime3,  grade_ratio);
        text(grade_ratio);*/
		

        //Varinfo.mainactivity.StudentName(context);

    }

	String date(long time){
		SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
		//ParsePosition pos = new ParsePosition(0);
		Date date=new Date();
		
		/*try
		{
			date = formatter.parse(time);
		}
		catch (ParseException e)
		{}*/
		date.setTime(time);
		String str=formatter.format(date);

		//Toast.makeText(context, str, 1).show();
		return str;
	}
	
	
	//理论实际4//加了=
	int examNum=5;
	String paperId="";
	String examId="";
	String paramo="";
	String eventTime="";
	String gradeRatio="";
	int ihttp1;
	int ihttp2;
	
	void getGraderatio(int param_o,String examIdg,String paperIdg){
		Varinfo.examId=examIdg;
		Varinfo.paperId=paperIdg;
		HTTP.request(HTTP.question_detail, view, param_o, new HTTPCallBack(){

				@Override
				public void onHTTPEnd(String result_ov, int param)
				{
					ihttp2++;
					/*if(ihttp2!=2||ihttp2!=3){
						if(ihttp2==4){
							ihttp2=2;
						}*/
					//Toast.makeText(context,ihttp1+","+ihttp2,Toast.LENGTH_SHORT).show();
					//Toast.makeText(context,"2",Toast.LENGTH_SHORT).show();
					//Toast.makeText(context,result_ov,Toast.LENGTH_SHORT).show();
					try
					{
						gradeRatio += new JSONObject(result_ov).getJSONObject("data").getDouble("paperBeatRate");
						if(param!=position+examNum){
							gradeRatio+=",";
						}
					}
					catch (JSONException e)
					{}
					
					if(ihttp2==ihttp1/*+1*/&&gotexam==examNum){
						String[] eventTime2=eventTime.split(",");
						String[] grade_ratio2=gradeRatio.split(",");
						String[] eventTime3=new String[eventTime2.length];
						Double[] grade_ratio=new Double[grade_ratio2.length];
						int length = grade_ratio2.length-1;
						for (int i=0;i < length;i++)
						{
							eventTime3[i]=eventTime2[length-i];
							grade_ratio[i]=Double.parseDouble(grade_ratio2[length-i]);
							Toast.makeText(context,grade_ratio[i]+"&"+eventTime3[i],1).show();
						}

						Toast.makeText(context, "输出", 1).show();
						charts_area(eventTime3,  grade_ratio);
                        text(grade_ratio);
						
						
					}
					//}
					
			}
			});
	}
	//已验证
	int gotexam;
    private void json() throws JSONException
    {
        choosen_subject = result.getJSONArray("papers").getJSONObject(paper_pos);
       // paperId = choosen_subject.getString("paperId");
		ihttp1=0;
		ihttp2=0;
		
		long Time=Varinfo.trends_org.getJSONObject(position).getLong("time");
		eventTime=date(Time)+",";
		//Toast.makeText(context,eventTime,Toast.LENGTH_SHORT).show();
		//getGraderatio(position);
		gradeRatio=Varinfo.rank_qd_graderatio+",";
		
		for(int i=position+1;i<=position+examNum;i++){
	
			gotexam=i-position;
			//Toast.makeText(context,gotexam+"",Toast.LENGTH_SHORT).show();
				Varinfo.examId=Varinfo.trends_org.getJSONObject(i).getString("examId");
				HTTP.request(HTTP.examoverview,view, i, new HTTPCallBack(){

						@Override
						public void onHTTPEnd(String result_eo, int param)
						{
							
							try
							{
								JSONArray papers=new JSONObject(result_eo).getJSONObject("data").getJSONArray("papers");
								for(int i2=0;i2<papers.length();i2++){
									JSONObject jSONObject = papers.getJSONObject(i2);
									String subjectName=jSONObject.getString("subject");
									
									if(subjectName.equals(subject)){
										
										//Toast.makeText(context,subjectName,Toast.LENGTH_SHORT).show();
										
										JSONObject jSONObject2 = Varinfo.trends_org.getJSONObject(param);
										paperId+=jSONObject.getString("paperId");
										examId+=jSONObject2.getString("examId");
										paramo+=param;
										
										long Time=jSONObject2.getLong("time");
										eventTime+=date(Time);
										
										ihttp1++;
										if(param!=position+examNum){
											paperId+=",";
											examId+=",";
											paramo+=",";
											eventTime+=",";
										}
										
										//Toast.makeText(context,"1",Toast.LENGTH_SHORT).show();
										
									}
								}
								if(param==position+examNum){
									String[] paperIds=paperId.split(",");
									String[] examIds=examId.split(",");

									String[] paramos = paramo.split(",");
									for (int g=0;g<ihttp1;g++){

										Toast.makeText(context,examIds[g]+"&"+paperIds[g],0).show();
										getGraderatio(Integer.valueOf(paramos[g]),examIds[g],paperIds[g]);
									}
								}
							}
							catch (JSONException e)
							{
								Toast.makeText(context,e.toString(),1).show();
							}
						}
					});
			
			
		}
		
		
		
		
	/*old*/	
       /* HTTP.request("substat",view, new HTTPCallBack(){

				@Override
				public void onHTTPEnd(String p1,int param)
				{
                    try
                    {
                        JSONArray array=new JSONObject(p1).getJSONArray("data");
                        String[] eventTime=new String[array.length()];
                        String[] paperId=new String[array.length()];
                        Double[] class_ratio=new Double[array.length()];
                        Double[] grade_ratio=new Double[array.length()];
                        for (int i=0; i < array.length(); i++)
                        {
                            JSONObject paper = (JSONObject)array.get(i);
							eventTime[i] = paper.getString("eventTime");
							paperId[i]= paper.getString("paperId");
							class_ratio[i] = SubjectAI.GetRatio(paper.getInt("classRank"), paper.getInt("classNum"));
                            grade_ratio[i] = SubjectAI.GetRatio(paper.getInt("gradeRank"), paper.getInt("gradeNum"));
                        }
                        charts_area(eventTime, class_ratio, grade_ratio);
                        text(paperId, class_ratio, grade_ratio);
                    }
                    catch (JSONException e)
                    {}}
			});*/
			
			
			

    }

    void charts_area(String[] eventTime, Double[] grade_ratio)
	{
        DisplayMetrics dm = getResources().getDisplayMetrics();        
        int scrWidth = (int) (dm.widthPixels * 0.9);    
        scrWidth = scrWidth + grade_ratio.length * 55;
        int scrHeight = (int) (dm.heightPixels * 0.50);                     
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(scrWidth, scrHeight);

        //layoutParams.addRule(RelativeLayout.BELOW, R.id.ana_TextView2);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);   
		/*
		 if (update)
		 {
		 r.removeView(radarview);
		 }*/
       // RelativeLayout r = (RelativeLayout)view.findViewById(R.id.ana_area_cla);
        RelativeLayout r2 = (RelativeLayout)view.findViewById(R.id.ana_area_grade);
       // AreaView areaview = new AreaView(context, eventTime, class_ratio, color_radar);
        AreaView areaview2= new AreaView(context, eventTime, grade_ratio, color_radar);
        //修复高度不够
        //r.getLayoutParams().height = scrHeight;
        //r.addView(areaview, layoutParams);
        r2.addView(areaview2, layoutParams);

		/* RelativeLayout rl=(RelativeLayout)view.findViewById(R.id.AnalysisContent_s);
		 RelativeLayout. LayoutParams paramsl= (RelativeLayout.LayoutParams) rl.getLayoutParams();
		 paramsl.topMargin = scrHeight + 20;
		 rl.setLayoutParams(paramsl);*/


    }
    public void text(Double[] grade_ratio)
	{
        //String area_class="在班级中来看，";
        String area_grade="我们又分析了你在年级中的情况，";

		/*//获取此次考试时间
		String thisId="";
		
		try
		{
			thisId=result.getJSONArray("papers").getJSONObject(paper_pos).getString("paperId");
		}
		catch (JSONException e)
		{
			thisId = "WRONG";
		}
		int thisinevent=0;
		for (int i=0;i < paperID.length;i++)
		{
			if (paperID[i].equals(thisId))
			{
				thisinevent = i;
			}
		}*/
		//Toast.makeText(context,thisId+"哈哈"+thisinevent,1).show();
		//end&&&&&&&&&&&&&&&&&&&&&
		int thisinevent=grade_ratio.length-1;

		//area_class += text_area(thisinevent, class_ratio)+text_area2(1,thisinevent,class_ratio)+"\n"+SubjectAI.getGradeDrift(class_ratio,thisinevent);
		area_grade += text_area(thisinevent, grade_ratio)+text_area2(1,thisinevent,grade_ratio)+"\n"+SubjectAI.getGradeDrift(grade_ratio,thisinevent);

		/*TextView comu=(TextView) view.findViewById(R.id.anas_score_com);
		Double ratio=grade_ratio[thisinevent];
		String comtext="G";
		if(ratio>=90){
			comtext="A";
		}else 
		if(ratio>=80){
			comtext="B";
		}else 
		if(ratio>=60){
			comtext="C";
		}else 
		if(ratio>=40){
			comtext="D";
		}else 
		if(ratio>=20){
			comtext="E";
		}
        comu.setText(comtext);*/
		
		//TextView ac=(TextView)view.findViewById(R.id.anas_text_area1);
		//ac.setText(AnalysisFragment.format(area_class));
		TextView ag=(TextView)view.findViewById(R.id.anas_text_area2);
		ag.setText(AnalysisFragment.format(area_grade));
		
		//总体
		String alltext="总体上来看,童鞋你这次考试的"+subject+"科目中，拿到了"+realScore+"分，";
		alltext+="和上次考试相比，"+text_area2(2,thisinevent,grade_ratio)+"\n";
		alltext += "整体来看,你的" + subject + "成绩"+/*在班级中击败了" + class_ratio[thisinevent] + "％ 的同学，而*/"在年级中，"+/*也同样*/"PK掉了" + grade_ratio[thisinevent] + "％ 的人";
		
		Toast.makeText(context,"Varinfo.exam_ratio&"+Varinfo.exam_ratio,1).show();
			alltext+="，"+text_3(1-Varinfo.exam_ratio);
	
		alltext+="\n期待你通过坏分数单科&全科分析报告的指导，认真安排学习计划喔，下次考试取得更大进步！";
		TextView alltexttv=(TextView) view.findViewById(R.id.Anas_AllText);
		alltexttv.setText(AnalysisFragment.format(alltext));
	}

	public String text_area(int thisPaper, Double[] ratio)
	{
		String area="";
        if (SubjectAI.getMax(ratio)==thisPaper)
		{
            area += "截止目前，这是你历次最高的成绩，但坏分数相信只有更高，没有最高，期待你更优秀的表现！";
        }
		else 
        if (SubjectAI.getMin(ratio)==thisPaper)
		{
            area += "这是你历次考试最低的成绩，/(ㄒoㄒ)/~~，希望你能化悲愤为动力，好分数期待你触底反弹的大进步！";
        }
		else
		{
            area += "成绩中等再接再厉";
        }
        area += "\n";
		return area;
    }
	public String text_area2(int mode,int pos, Double[] ratio)
	{
		if (pos == 0)
		{
			return "第一次";
		}
		else
		{
			String atext="这一次考试比上一次考试";
			String btext="";
			Double sub=new BigDecimal(ratio[pos]).subtract(new BigDecimal(ratio[pos - 1])).doubleValue();
			switch (SubjectAI.getProLevel(sub))
			{
				case 0:
					atext+= "成绩基本持平";
					btext+= "争取下次有所进步，一鸣惊人，创造奇迹！";
					break;
				case 1:
					atext+= "有一定的进步哦( ´▽` )";
					btext+= "保持住现在的学习热情与状态，下次考试要继续挑战自己哦，坏分数在此期待你的辉煌！(◍ ´꒳` ◍)";
					break;
				case 2:
					atext+= "进步比较大呢*罒▽罒*";
					btext+= "不要过于骄傲哦（*＾ワ＾*），保持稳定的成绩，坏分数相信你会再创辉煌！";
					break;
				case 3:
					atext+= "进步真的特别大，恭喜恭喜(ฅ>ω<*ฅ)";
					btext+= "保持住这简直神了的进步趋势，坏分数期待你金榜题名！（°Д°）Ъ大赞！";
					break;
				case -1:
					atext+= "有一定的退步";
					btext+= "不要灰心，平时注意养成良好的学习方法，研究一下坏分数为你定制的智能分析报告，未到悬崖先勒马，坏分数相信你能行，会成功！期待你超越自我哦！加油↖(^ω^)↗";
					break;
				default:
					return "";
			}
			if(mode==2){
				return atext+"，希望你继续努力，"+btext;
			}else{
				return atext+"。";
			}
		}
	}
	public String text_3(Double all_grade_rank_an){
		String grade_rank_str="还可以哦。";
        if (5 >= all_grade_rank_an)
        {
            grade_rank_str = "学神舍你其谁！";
        }
        else if (all_grade_rank_an > 5 && 15 >= all_grade_rank_an)
        {
            grade_rank_str = "你的学霸气质突显！";
        }
        else if (all_grade_rank_an > 15 && 30 >= all_grade_rank_an)
        {
            grade_rank_str = "你的成绩也颇为优秀。";
        }
        else if (all_grade_rank_an > 30 && 50 >= all_grade_rank_an)
        {
            grade_rank_str = "你成绩还算不错。";
        }
        else if (all_grade_rank_an > 50 && 75 >= all_grade_rank_an)
        {
            grade_rank_str = "你处于中游位置。";
        }
        else if (all_grade_rank_an > 75 && 100 >= all_grade_rank_an)
        {
            grade_rank_str = "你的成绩亟待提高！";
        }
		return grade_rank_str;
	}
}
