package com.zzh.hfs.plus.fragment;

//import com.afollestad.materialdialogs.color.*;
//import android.support.v4.app.*;
import org.json.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import android.support.annotation.*;
import com.zzh.hfs.plus.*;
import co.mobiwise.materialintro.prefs.*;
import android.os.*;
import android.util.*;
import com.zzh.hfs.plus.tool.*;
import com.zzh.hfs.plus.data.*;
import android.text.*;
import android.text.style.*;
import android.graphics.*;
import android.support.v7.app.*;
import com.zzh.hfs.plus.charts.*;
import co.mobiwise.materialintro.view.*;
import co.mobiwise.materialintro.shape.*;
import android.support.v7.widget.Toolbar;
import java.math.*;
import android.app.Fragment;

public class AnalysisFragment extends MyFragment
{
    /*****全科分析报告部分****/
	private JSONObject result;

	public static String[] papers_name;

	public static Double[] papers_ratio;
    public static Double[] papers_ratio2;
    public static boolean lastexam;

    public static Double exam_ratio;
	public static Double exam_ratio2;

    private int position;
	/*
	 private int primaryPreselect;
	 private int choice;

	 private SharedPreferences pre_settings;*/

    public static int color_radar;
    public static int color_radar_bak;
    public static int color_bar_pre;
    public static int color_bar_now;
    private int color_radar_text;

    private RelativeLayout r;

    private View radarview;
    private View barview;

    private Double all_score;
    private Double all_ratio_class;
    private Double all_grade_rank_an;

    private View view;
    private Context context;

	private JSONArray papers;

    @Override
    public void onResume()
    {
        Varinfo.page = 6;
        getActivity().setTitle(R.string.app_name);
        super.onResume();
    }

	@Override
	public void onHiddenChanged(boolean hidden)
	{
		if (!hidden)
		{
			Varinfo.page = 6;}
		super.onHiddenChanged(hidden);
	}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Varinfo.page = 6;
        view = inflater.inflate(R.layout.content_analysis, container, false);
        context = getActivity();
        setHasOptionsMenu(true);
        content();
		//  MT.finish();
        //Varinfo.analysisfrag=this;
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
        /*        Bundle intent = getIntent().getExtras(); 
         try
         {
         result = new JSONArray(intent.getString("data"));
         }
         catch (JSONException e)
         {}
         position = intent.getInt("pos", 0);*/

        result = Varinfo.trends_data;
        position = Varinfo.trends_position;
        exam_ratio = Varinfo.exam_ratio;

        //pre_settings = context.getSharedPreferences("settings", context.MODE_PRIVATE);
        color_radar = /*pre_settings.getInt("color_radar", */getResources().getColor(R.color.color_radar);
        color_radar_bak = /*pre_settings.getInt("color_radar_bak", */getResources().getColor(R.color.color_radar_bak);
        color_bar_pre = /*pre_settings.getInt("color_bar_pre", */getResources().getColor(R.color.color_bar_pre);
        color_bar_now = /*pre_settings.getInt("color_bar_now", */getResources().getColor(R.color.color_bar_now);
        color_radar_text = /*pre_settings.getInt("color_radar_text", */getResources().getColor(R.color.color_radar_text);

        try
		{
			json();
		}
		catch (JSONException e)
		{}

		// Varinfo.mainactivity.StudentName(context);
    }

	/*

	 public void onColorSelection(ColorChooserDialog p1, int color)
	 {
	 SharedPreferences.Editor pre_settings_edit=pre_settings.edit();
	 switch (choice)
	 {
	 case 1:
	 color_radar = color;
	 charts(true);
	 pre_settings_edit.putInt("color_radar", color);
	 break;
	 case 2:
	 color_radar_bak = color;
	 charts(true);
	 pre_settings_edit.putInt("color_radar_bak", color);
	 break;
	 case 0:
	 color_radar_text = color;

	 TextView analysistext=(TextView)view.findViewById(R.id.AnalysisText),
	 alltext=(TextView)view.findViewById(R.id.AnalysisAllText),
	 bartext=(TextView)view.findViewById(R.id.AnalysisBarText);
	 analysistext.setTextColor(color);
	 alltext.setTextColor(color);
	 bartext.setTextColor(color);

	 pre_settings_edit.putInt("color_radar_text", color);
	 break;
	 case 3:
	 color_bar_pre = color;
	 charts(true);
	 pre_settings_edit.putInt("color_bar_pre", color);
	 break;
	 case 4:
	 color_bar_now = color;
	 charts(true);
	 pre_settings_edit.putInt("color_bar_now", color);
	 break;
	 }
	 pre_settings_edit.commit();
	 }*/

	int ih1;
	int classNum=0;
    void json() throws JSONException
    {

		papers = result.getJSONArray("papers");

		papers_name = new String[papers.length()];
		papers_ratio = new Double[papers.length()];

		ih1=0;
		for (int i = 0; i < papers.length(); i++)
		{
			JSONObject paper = (JSONObject)papers.get(i);

			papers_name[i] = paper.getString("subject");
			// Double rank =paper .getDouble("gradeRank");
			// Double num =paper .getDouble("gradeNum");
			//papers_ratio[i] =Double.parseDouble(String.format("%.2f", 100 * (1 - (rank - 1) / (num - 1))));

			Varinfo.paperId = paper.getString("paperId");

			HTTP.request(HTTP.question_detail, view, i, new HTTPCallBack(){

					@Override
					public void onHTTPEnd(String result_p, int ihttp2)
					{
						ih1++;
						try
						{
							JSONObject jSONObject = new JSONObject(result_p).getJSONObject("data");
							papers_ratio[ihttp2] = jSONObject.getDouble("paperBeatRate");

							if (ihttp2 == 0)
							{
								classNum = jSONObject.getJSONArray("questionDetail").getJSONObject(0).getInt("classNum");
							}
							if (ih1 == papers.length() - 1)
							{
								//Toast.makeText(context,ihttp2+"",0).show();
								json2();
								//for(int i=0;i<papers_ratio.length;i++){
								//Toast.makeText(context,papers_ratio[i]+"",0).show();
								//}
							}
						}
						catch (JSONException e)
						{
							//Toast.makeText(context,e.toString(),1).show();
						}

					}});
		}
	}
	int ih2;
	JSONObject paper2;
	void json2() throws JSONException
	{
		//JSONObject quanke=exam.getJSONArray("details").getJSONObject(0);
		all_score = result.getDouble("score");
		all_ratio_class = Double.parseDouble(String.format("%.2f", SubjectAI.GetRatio(Varinfo.trends_examClassRank, classNum)));
		all_grade_rank_an = Varinfo.exam_ratio;//Double.parseDouble(String.format("%.2f", (double)100 * (quanke.getInt("gradeRank") / exam.getInt("classStudentNum"))));

		lastexam = position + 1 != Varinfo.trends_org.length();
		if (lastexam)
		{
			Varinfo.examId = Varinfo.trends_org.getJSONObject(position + 1).getString("examId");
			HTTP.request(HTTP.examoverview, view, new HTTPCallBack(){

					@Override
					public void onHTTPEnd(String result_ov, int param)
					{
						try
						{
							JSONArray papers2 = new JSONObject(result_ov).getJSONObject("data").getJSONArray("papers");

							papers_ratio2 = new Double[papers.length()];
							for (int r=0;r<papers_ratio2.length;r++){
								papers_ratio2[r]=0d;
							}

							ih2=0;
							for (int ii = 0; ii < papers.length(); ii++)
							{
								for (int i2=0; i2 < papers2.length(); i2++)
								{
									paper2 = (JSONObject)papers2.get(i2);

									if (papers_name[ii].equals(paper2.getString("subject")))
									{
										//Toast.makeText(context,paper2.getString("subject"),1).show();
										Varinfo.paperId = paper2.getString("paperId");

										HTTP.request(HTTP.question_detail, view, ii, new HTTPCallBack(){

												@Override
												public void onHTTPEnd(String result_p, int ihttp2)
												{
													ih2++;
													try
													{
														JSONObject jSONObject = new JSONObject(result_p).getJSONObject("data");
														papers_ratio2[ihttp2] = jSONObject.getDouble("paperBeatRate");
														//Double rank =paper2 .getDouble("gradeRank");
														//Double num =paper2 .getDouble("gradeNum");
														//papers_ratio2[ii] =Double.parseDouble(String.format("%.2f", 100 * (1 - (rank - 1) / (num - 1))));

													}
													catch (JSONException e)
													{
														Toast.makeText(context,e.toString(),1).show();
													}
													//Toast.makeText(context,ihttp2+"",1).show();
													if (ih2 == papers.length() - 1)
													{
														r = (RelativeLayout)view.findViewById(R.id.analysis);
														charts(false);
														text();
													}

													/*if (papers_ratio2[ii] == null)
													{
														papers_ratio2[ii] = 0d;
													}*/
												}});
									}
								}
							}
						}
						catch (JSONException e)
						{
							Toast.makeText(context,e.toString(),1).show();
						}
					}});
		}else{
			r = (RelativeLayout)view.findViewById(R.id.analysis);
			charts(false);
			text();
		}
	}
	

	void charts(Boolean update)
	{
		DisplayMetrics dm = getResources().getDisplayMetrics();		   
		int scrWidth = (int) (dm.widthPixels * 0.9); 	
		int scrHeight = (int) (dm.heightPixels * 0.50); 			   		
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(scrWidth, scrHeight);

		layoutParams.addRule(RelativeLayout.BELOW, R.id.ana_TextView2);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);   

		if (update)
		{
			r.removeView(radarview);
		}
		radarview = new RadarView(context);
		r.addView(radarview, layoutParams);

		RelativeLayout rl=(RelativeLayout)view.findViewById(R.id.AnalysisContent);
		RelativeLayout. LayoutParams paramsl= (RelativeLayout.LayoutParams) rl.getLayoutParams();
		paramsl.topMargin = scrHeight + 20;
		rl.setLayoutParams(paramsl);

		DisplayMetrics dm_bar = getResources().getDisplayMetrics();        
		int scrWidth_bar = (int) (dm_bar.widthPixels * 0.9);    
		int scrHeight_bar = (int) (dm_bar.heightPixels * 0.50);                     
		RelativeLayout.LayoutParams layoutParams_bar = new RelativeLayout.LayoutParams(scrWidth_bar, scrHeight_bar);

		layoutParams_bar.addRule(RelativeLayout.BELOW, R.id.AnalysisContent);
		layoutParams_bar.addRule(RelativeLayout.CENTER_IN_PARENT);   

		if (update)
		{
			r.removeView(barview);
		}
		barview = new BarChart2(context);
		r.addView(barview, layoutParams_bar);

		RelativeLayout rl_bar=(RelativeLayout)view.findViewById(R.id.AnalysisContent2);
		RelativeLayout. LayoutParams paramsl_bar= (RelativeLayout.LayoutParams) rl_bar.getLayoutParams();
		paramsl_bar.topMargin = scrHeight_bar + 20;
		rl_bar.setLayoutParams(paramsl_bar);
		
		((RelativeLayout)view.findViewById(R.id.analysis)).setVisibility(View.VISIBLE);
		((FrameLayout) view.findViewById(R.id.loading_ana)).setVisibility(View.GONE);
		
	}

	void text()
	{
		int adv=SubjectAI.advantage(papers_ratio);
		int inf=SubjectAI.inferiority(papers_ratio);

		String text="";
//优势科目
		String advan_infer="以考试反映的成绩来说，如果你";

		if (adv == 999 && inf == 999)
		{
			if (exam_ratio >= 80)
			{
				text = "恭喜你，童鞋！你的所有科目都处于优秀的水平，没有任何一科是明显拉后腿的，继续保持这种学习状态吧！*罒▽罒*";
			}
			else
			{
				text = "童鞋，你的所有科目都处于平均的水平，建议去看一看哪些科目错的简单题比较多，先从这些科目突破，不断超越自我，追求卓越吧！(๑`･ᴗ･´๑)";
			}
		}
		else
		{
			if (adv != 999)
			{
				text = "与你自己的总成绩相比，" + papers_name[adv] + "是你的优势科目，直接拉高了你的总体分数，童鞋请继续努力，保持领先哟！(＊￣︶￣＊)";

				advan_infer += "保持" + papers_name[adv] + "等优势科目的领先位置，";
			}
			if (inf != 999)
			{
				if (text != "")
				{
					text += "\n";
				}
				text += "与你自己的总成绩相比，" + papers_name[inf] + "是你拖后腿的科目，童鞋请注意加强学习，掌握好的学习方法，把" + papers_name[inf] + "分提上来！加油↖(^ω^)↗！现在快去看看错了哪些题吧，建议先从简单的着手，夯实基础。";

				advan_infer += "加强对" + papers_name[inf] + "等劣势科目的练习，";
			}
		}

		TextView tv=(TextView)view.findViewById(R.id.AnalysisText);
		tv.setText(format(text));
		tv.setTextColor(color_radar_text);


////********分割线************//

		String text_bar="";

		String all_text="你本次考试的总分为" + all_score + "，和上次考试相比，";
		//进步快的科目
		String advan_infer2="";

		if (!lastexam)
		{
			text_bar += "欢迎使用坏分数PLUS!\n初次见面，还没有你之前的考试成绩记录，期待下次考试后在这里看到你的进步!";
		}
		else
		{
			Double exam_ratio_pro=Varinfo.exam_ratio - Varinfo.exam_ratio2;
			int alls=SubjectAI.getProLevel(exam_ratio_pro);
			String DY0="成绩有了明显进步，继续保持哦。";
			switch (alls)
			{
				case -1:
					text_bar += "警告！前方高能！！ 本次考试你的你年级排名下降了" + exam_ratio_pro + "%,痛定思痛， 查看考试分析，细思考试错题，先查缺再补漏！";
					all_text += "成绩有了一些退步，要重视起来，防微杜渐。";
					break;
				case 0:
					text_bar += "本次考试你的年级排名与上次基本持平，期待下次考试有进步喔！";
					all_text += "成绩基本持平，争取下次有所进步。";
					break;
				case 1:
					text_bar += "本次考试你的年级排名上升了" + exam_ratio_pro + "%,简直是进步可喜呦！";
					all_text += DY0;
					break;
				case 2:
					text_bar += "本次考试你的年级排名上升了" + exam_ratio_pro + "%,简直是进步神速！";
					all_text += DY0;
					break;
				case 3:
					text_bar += "本次考试你的年级排名上升了" + exam_ratio_pro + "%,简直是超音速！";
					all_text += DY0;
					break;
			}

			String upsubject="";
			String downsubject="";
			String upsubject_s="";
			String downsubject_s="";
			for (int i=0; i < papers_ratio.length; i++)
			{
				if (papers_ratio2[i] != 0)
				{
					BigDecimal b1=new BigDecimal(Double.toString(papers_ratio[i]));
					BigDecimal b2=new BigDecimal(Double.toString(papers_ratio2[i]));

					Double paper_prog=b1.subtract(b2).doubleValue();
					int paper_pro= SubjectAI.getProLevel(paper_prog);

					if (paper_pro >= 1)
					{
						upsubject += papers_name[i] + "、";
						upsubject_s += paper_pro + "%、";
					}
					else if (paper_pro == -1)
					{
						downsubject += papers_name[i] + "、";
						downsubject_s += (-paper_prog) + "%、";
					}
				}    
			}

			if (!upsubject.equals(""))
			{
				String upsubject_o=upsubject.substring(0, upsubject.length() - 1);
				String upsubject_s_o=upsubject_s.substring(0, upsubject_s.length() - 1);
				text_bar += "\n" + upsubject_o + "科目分别提升了" + upsubject_s_o + "，进步可喜可贺！望继续保持！";

				advan_infer2 += "对于提升快的科目" + upsubject_o + "，你可以继续保持目前的学习方法，保持日常的知识积累与练习，就可基本保持目前的较好上升趋势；";
			}

			if (!downsubject.equals(""))
			{
				String downsubject_o=downsubject.substring(0, downsubject.length() - 1);
				String downsubject_s_o=downsubject_s.substring(0, downsubject_s.length() - 1);
				text_bar += "\n" + downsubject_o + "科目分别降低了" + downsubject_s_o + "，有退步，需要引起注意！";

				advan_infer2 += "对于退步的科目" + downsubject_o + "，需要你找出差距，找到提分点，有的放矢，各个击破，希望坏分数分析报告能帮到你！";
			}
		}

		TextView tv2=(TextView)view.findViewById(R.id.AnalysisBarText);
		tv2.setTextColor(color_radar_text);
		tv2.setText(format(text_bar));


		Integer all_score_zheng=(int)Math.floor(all_score);
		String a_s_z=all_score_zheng.toString();
		String fanwei_pre=a_s_z.substring(0, a_s_z.length() - 1);
		int fanwei_lat=Integer.parseInt(fanwei_pre) + 1;
		String fanwei=fanwei_pre + "0～" + fanwei_lat + "0";

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

		String fullytext="";
		if (lastexam)
		{
			fullytext = all_text + "\n整体来看，本次考试你处于" + fanwei + "分数段,你的成绩击败了班级中" + all_ratio_class + "%的同学，而在年级中，" + grade_rank_str + "\n" + advan_infer + "稍稍努力提升总分，就可以再提升不少名次。\n" + advan_infer2 + "\n期待你通过分析报告的指导，认真安排学习计划喔，下次考试取得更大进步！";
		}
		else
		{
			fullytext = all_text + "\n整体来看，本次考试你处于" + fanwei + "分数段,你的成绩击败了班级中" + all_ratio_class + "%的同学，而在年级中，" + grade_rank_str + "\n" + advan_infer + "稍稍努力提升总分，就可以再提升不少名次。\n期待你通过分析报告的指导，认真安排学习计划喔，下次考试取得更大进步！";

		}
		TextView tv3=(TextView)view.findViewById(R.id.AnalysisAllText);
		tv3.setTextColor(color_radar_text);
		tv3.setText(format(fullytext));

	}

	public static SpannableStringBuilder format(String text)
	{
		String[] duanluo=text.split("\n");
		SpannableStringBuilder text_all =  new SpannableStringBuilder("");

		/*SpannableStringBuilder hangjianju = new SpannableStringBuilder("\n");  
		 hangjianju.setSpan(new AbsoluteSizeSpan(8), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE); */
		for (int i=0; i < duanluo.length; i++)
		{
			SpannableStringBuilder span = new SpannableStringBuilder("◎缩" + duanluo[i] + "\n");  
			span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 1, 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE); 
			text_all.append(span);
			SpannableStringBuilder hangjianju = new SpannableStringBuilder("\n");  
			hangjianju.setSpan(new AbsoluteSizeSpan(10), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE); 
			text_all.append(hangjianju);
		}
		return text_all;
	}
	/*
	 public void ONColorSettings(View v)
	 {

	 choice=0;
	 new AlertDialog.Builder(context)
	 .setTitle("请选择想要自定义的部分")
	 .setSingleChoiceItems(getResources().getString(R.string.color_part).split(";"), 0, new DialogInterface.OnClickListener(){

	 @Override
	 public void onClick(DialogInterface p1, int p2)
	 {
	 choice = p2;
	 }
	 })
	 .setPositiveButton("选择", new DialogInterface.OnClickListener(){

	 @Override
	 public void onClick(DialogInterface p1, int p2)
	 {
	 switch (choice)
	 {
	 case 1:
	 primaryPreselect = color_radar;
	 break;
	 case 2:
	 primaryPreselect = color_radar_bak;
	 break;
	 case 0:
	 primaryPreselect = color_radar_text;
	 break;
	 case 3:
	 primaryPreselect = color_bar_pre;
	 break;
	 case 4:
	 primaryPreselect = color_bar_now;
	 break;}
	 new ColorChooserDialog.Builder(Varinfo.mainactivity, R.string.color_picker)
	 //  .titleSub(R.string.colors)
	 .preselect(primaryPreselect)
	 .allowUserColorInput(true)
	 .allowUserColorInputAlpha(false)
	 .show();
	 }})
	 .setNegativeButton("取消", null)
	 .setNeutralButton("恢复默认", new DialogInterface.OnClickListener(){

	 @Override
	 public void onClick(DialogInterface p1, int p2)
	 {
	 SharedPreferences.Editor pre_settings_edit=pre_settings.edit();
	 String remove="";
	 switch (choice)
	 {
	 case 1:
	 remove = "color_radar";
	 break;
	 case 2:
	 remove = "color_radar_bak";
	 break;
	 case 0:
	 remove = "color_radar_text";
	 break;
	 case 3:
	 remove = "color_bar_pre";
	 break;
	 case 4:
	 remove = "color_bar_now";
	 break;
	 }
	 pre_settings_edit.remove(remove);
	 pre_settings_edit.commit();

	 //Toast.makeText(getContext(), "颜色已恢复默认，请重新打开界面！", 1).show();
	 //finish();
	 content();
	 }})

	 .setCancelable(false)
	 .create()
	 .show();
	 }
	 */
}
