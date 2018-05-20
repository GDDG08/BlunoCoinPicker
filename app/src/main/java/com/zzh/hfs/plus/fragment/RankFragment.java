package com.zzh.hfs.plus.fragment;
import android.a.k.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v7.app.*;
//import android.support.widget.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;
import com.umeng.onlineconfig.*;
import com.zzh.hfs.plus.*;
import com.zzh.hfs.plus.data.*;
import com.zzh.hfs.plus.tool.*;
import java.math.*;
import org.json.*;
//import xa.xa.ff.os.*;
import android.support.v7.app.AlertDialog;
public class RankFragment extends MyFragment
{
    private JSONObject data;
    private int position;
    private JSONArray details;
    private String [] details_name;
    private String detailName;
    private String score;
    private String realScore;
    private String classAvg;
    private String classRank;
    private String gradeAvg;
    private String gradeRank;
    private JSONArray papers;
    private String[] papers_name;
    private String p_subject;
    private Double p_score;
    private Double p_realScore;
    private Double p_classAvg;
    private int p_classRank;
    private Double p_gradeAvg;
    private int p_gradeRank;
    private Double p_classMaxScore;
    private Double p_gradeMaxScore;
    //private JSONObject exam;
    String active;
    private View view;
    private Context context;
    private int model;

	private OnItemClickListener itlistener1;
	private OnItemClickListener itlistener2;

	private int listitem;

	private String[] loading;

	private ProgressBar progressBar;

    @Override
    public void onResume()
    {
        Varinfo.page = 4;
        super.onResume();
    }

	@Override
	public void onHiddenChanged(boolean hidden)
	{
		if (!hidden)
		{
			Varinfo.page = 4;
			done();
		}
		super.onHiddenChanged(hidden);
	}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Varinfo.page = 4;
        view = inflater.inflate(R.layout.content_rank, container, false);
        context = getActivity();
		active = Varinfo.preferences_login.getString("active", null);
        content();
		// MT.finish();
        // dialog("测试",model+"");
        return view;
    }
    void content()
    {
        data = Varinfo.trends_data;
        position = Varinfo.trends_position;
        model = Varinfo.model_rank;

		progressBar = (ProgressBar)view.findViewById(R.id.rankProgressBar);

        json();
        //Varinfo.mainactivity.StudentName(context);
        //Varinfo.mainactivity.Data(context,view,R.id.ll_banner_rank);
    }
    void json()
    {
        try
        {  
            /*exam = data.getJSONObject(position);
			 details = exam.getJSONArray("details"); 
			 int length ;
			 if (model == 2)
			 {
			 length = 1;
			 }
			 else
			 {
			 length = details.length();
			 }
			 details_name = new String[length];
			 for (int i=0; i < length; i++)
			 {
			 JSONObject detail = (JSONObject)details.get(i);
			 details_name[i] = detail.getString("detailName");
			 if (model==2){
			 details_name[i] += "  ✔✔";
			 }
			 }*/
			details_name = new String[]{"全科"};
			if (model == 2)
			{
				details_name[0] += "  ✔✔";
			}
            TextView examname = (TextView) view.findViewById(R.id.rankexamname);
            examname.setText(Varinfo.trends_examName);
            String gradenum_t = "";
            String classnum_t="";
            switch (model)
            {
                case 3:
					((TextView) view.findViewById(R.id.rank_detail)).setVisibility(View.GONE);
					((MyListView) view.findViewById(R.id.rankExamList)).setVisibility(View.GONE);
					//case 1:
					//  gradenum_t = "年级人数：" + exam.getString("gradeStudentNum");
					// classnum_t = "班级人数：" + exam.getString("classStudentNum");

                    break;
                case 2:
                    gradenum_t = OnlineConfigAgent.getInstance().getConfigParams(context, "analysis-tip");
                    TextView rank_detail = (TextView) view.findViewById(R.id.rank_detail);
                    rank_detail.setText("全科分析报告");
                    rank_detail.setTextSize(20);
                    TextView rank_paper = (TextView) view.findViewById(R.id.rank_paper);
                    rank_paper.setText("单科分析报告");
                    rank_paper.setPadding(0, 20, 10, 0);
					rank_paper.setTextSize(18);

					//Button bt=(Button.
                    break;
            }
			gradenum_t = "您正在使用预览版本，可能会出现卡顿，稍候即可！";
            TextView gradenum = (TextView) view.findViewById(R.id.rankgradenum);
            gradenum.setText(gradenum_t);
            TextView classnum = (TextView) view.findViewById(R.id.rankclassnum);
            classnum.setText(classnum_t);
            papers = data.getJSONArray("papers"); 
            papers_name = new String[papers.length()];
            for (int ii=0; ii < papers.length(); ii++)
            {
                JSONObject paper = (JSONObject)papers.get(ii);
                papers_name[ii] = paper.getString("subject");
				if (model == 2)
				{
                    papers_name[ii] += "  ✔✔";
                }
            }
        }
        catch (JSONException e)
        {}

        if (model == 2)
        {
            listitem = R.layout.my_listview_items;}
        else
        {
            listitem = android.R.layout.simple_list_item_1;
        }
		itlistener1 = new OnItemClickListener() {
			private String ID;

			private JSONObject paper;
			@Override
			public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
			{
				progressBar.setVisibility(View.VISIBLE);
				try
				{
					paper = (JSONObject)papers.get(p3);
					p_subject = paper.getString("subject");
					p_score = paper.getDouble("manfen");
					p_realScore = paper.getDouble("score");
					/*  p_classAvg = new BigDecimal(paper.getDouble("classAvg")).setScale(2,RoundingMode.HALF_UP).doubleValue();
					 p_classRank = paper.getInt("classRank");
					 p_gradeAvg = new BigDecimal(paper.getDouble("gradeAvg")).setScale(2,RoundingMode.HALF_UP).doubleValue();
					 p_gradeRank = paper.getInt("gradeRank");
					 p_classMaxScore = paper.getDouble("classMaxScore");
					 p_gradeMaxScore = paper.getDouble("gradeMaxScore");*/

					ID = paper.getString("paperId");
				}
				catch (JSONException e)
				{}
				Varinfo.paperId = ID;
				Varinfo.examId=Varinfo.examId_o;
				switch (model)
				{
					case 2:
						//**load();
						//**//
						Msg.Snack(view,"单科分析报告暂时搞不定呢……\n(｡•́︿•̀｡)敬请期待！");
						break;
						//**//
					case 1:
						HTTP.request(HTTP.paper_detail, view, new HTTPCallBack(){

								@Override
								public void onHTTPEnd(String result, int param)
								{
									try
									{
										JSONObject jSONObject = new JSONObject(result).getJSONObject("data");
										p_classMaxScore = jSONObject.getDouble("classHighest");
										p_gradeMaxScore = jSONObject.getDouble("gradeHighest");
									}
									catch (JSONException e)
									{}
									HTTP.request(HTTP.question_detail, view, new HTTPCallBack(){

											@Override
											public void onHTTPEnd(String result_p, int param)
											{
												try
												{
													JSONObject jSONObject = new JSONObject(result_p).getJSONObject("data");
													JSONObject jSONObject2 = jSONObject.getJSONArray("questionDetail").getJSONObject(0);
													int gradeNum=jSONObject2.getInt("gradeNum");
													int classNum=jSONObject2.getInt("classNum");
													double graderatio=jSONObject.getDouble("paperBeatRate");
													p_gradeRank = SubjectAI.CulRank(graderatio, gradeNum);

													switch (model)
													{

														case 1:
															dialog(p_subject, "班级人数：" + classNum + "\n年级人数：" + gradeNum + "\n满分：" + p_score + "\n实际得分：" + p_realScore + "\n班级排名：/(ㄒoㄒ)/~~"  + "\n年级排名：" + p_gradeRank + "\n班级最高分：" + p_classMaxScore + "\n年级最高分：" + p_gradeMaxScore);
															break;
														case 2:
															//dialog("查看单科分析报告", "更多功能敬请期待");
															// Msg.Snack(view,"单科分析报告暂时搞不定呢……\n(｡•́︿•̀｡)敬请期待！");
															//load();
															Fragment frag=new AnaSFragment(p_subject,
																						   p_realScore,
																						   p_classRank,
																						   p_gradeRank,
																						   p_classMaxScore,
																						   p_gradeMaxScore);
															Varinfo.paper_pos = p3;
															Varinfo.rank_qd_graderatio=graderatio;

															intent(frag);
															break;
													}

												}
												catch (JSONException e)
												{}
											}});
								}});
						break;

					case 3:
						load();
						Varinfo.paper_pos = p3;
						HTTP.request(HTTP.answer_picture, view, new HTTPCallBack(){

								@Override
								public void onHTTPEnd(String result_a, int param)
								{
									try
									{
										paper.put("type", 3);
										paper.put("examName", "");
										paper.put("shortName", "整体试卷");
										JSONArray pic= new JSONObject(result_a).getJSONObject("data").getJSONArray("url");
										paper.put("myAnswers", pic);
										paper.remove("pictures");
										paper.put("pictures", new JSONArray());
										paper.remove("manfen");
										paper.remove("score");
										paper.put("score", p_score);
										paper.put("realScore", p_realScore);
										if (p_score <= p_realScore)
										{
											paper.put("isWrong", 2);
										}
										else
										{
											paper.put("isWrong", 1);
										}
									}
									catch (JSONException e)
									{}
									Fragment frag2=new PaperFragment(ID,
																	 p_subject,
																	 p_score,
																	 p_realScore,
																	 paper);



									intent(frag2);
								}});
						break;
				}
			}
		};
		itlistener2 = new OnItemClickListener() {
			private String ID;
			@Override
			public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
			{
				progressBar.setVisibility(View.VISIBLE);
				//JSONObject detail = (JSONObject)details.get(p3);
				
				Varinfo.examId=Varinfo.examId_o;
				switch (model)
				{
					case 1:
						try
						{
							Varinfo.paperId = papers.getJSONObject(0).getString("paperId");
						}
						catch (JSONException e)
						{}
						HTTP.request(HTTP.question_detail, view, new HTTPCallBack(){

								@Override
								public void onHTTPEnd(String result_p, int param)
								{
									try
									{
										JSONObject jSONObject = new JSONObject(result_p).getJSONObject("data").getJSONArray("questionDetail").getJSONObject(0);
										int gradeNum=jSONObject.getInt("gradeNum");
										int classNum=jSONObject.getInt("classNum");


										//detailName = detail.getString("detailName");
										score = data.getString("manfen");
										realScore = data.getString("score");
										gradeRank = SubjectAI.CulRank(Varinfo.exam_ratio, gradeNum) + "";
										classRank = Varinfo.trends_examClassRank + "";

										//dialog(detailName, Varinfo.exam_ratio+"\n"+gradeNum);
										dialog(detailName, "班级人数：" + classNum + "\n年级人数：" + gradeNum + "\n满分：" + score + "\n实际得分：" + realScore + "\n班级排名：" + classRank + "\n年级排名：" + gradeRank);//"\n班级平均分：" + classAvg +  "\n年级平均分：" + gradeAvg);

									}
									catch (JSONException e)
									{
										//Toast.makeText(context,e.toString(),1).show();
									}
									/*classAvg = detail.getString("classAvg");
									 classRank = detail.getString("classRank");
									 gradeAvg = detail.getString("gradeAvg");
									 gradeRank = detail.getString("gradeRank");*/

									//去广告Varinfo.mainactivity.StudentName(context);
								}});
						break;
					case 2:
						load();
						ID = Varinfo.examId;
						intent(new AnalysisFragment());
						break;
						/*case 3:
						 Msg.Snack(view,"整体答卷预览暂未开放！");
						 break;*/
				}
			}

			/*void checkanas(){

			 new MaterialDialog.Builder(context)
			 .title("标题")
			 .items()
			 .itemsCallbackMultiChoice(new Integer[]{1}, new MaterialDialog.ListCallbackMultiChoice(){
			 @Override
			 public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text)
			 {

			 boolean allowSelectionChange = which.length >= 1; // selection count must stay above 1, the new (un)selection is included in the which array
			 if (!allowSelectionChange) {
			 Toast.makeText(context,"最少3科
			 }
			 return allowSelectionChange;
			 }})
			 .positiveText(R.string.dismiss)
			 .alwaysCallMultiChoiceCallback() // the callback will always be called, to check if (un)selection is still allowed
			 .show();
			 }*/

		};
		done();

		new Handler().postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					Varinfo.mainactivity.web(context);
					o0oo.Application(context);
				}}, 500);
		loading = new String[1];
		loading[0] = "加载中……";
	}
	private void item(int id, String[] s, OnItemClickListener listener)
	{
		ListAdapter adapter = new ArrayAdapter<String>(context, listitem, s);
		MyListView listView = (MyListView) view.findViewById(id);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(listener);
	}
	void load()
	{
		/*OnItemClickListener nul=new OnItemClickListener(){

		 @Override
		 public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
		 {
		 // TODO: Implement this method
		 }
		 };*/
		item(R.id.rankExamList, loading, null);
		item(R.id.rankPaperList, loading, null);
	}
	void done()
	{
		progressBar.setVisibility(View.INVISIBLE);
		item(R.id.rankExamList, details_name, itlistener2);
		item(R.id.rankPaperList, papers_name, itlistener1);
	}
	private void dialog(String title, String msg)
	{
		progressBar.setVisibility(View.INVISIBLE);
		new AlertDialog.Builder(context)
			.setTitle(title)
			.setMessage(msg)
			.setPositiveButton("确定", null)
			.setCancelable(true).show();
	}
	private void intent(Fragment f)
	{
		Varinfo.mainactivity.Page(f);
		/*FragmentTransaction transaction = getFragmentManager().beginTransaction();
		 transaction.replace(R.id.container, f);
		 transaction.addToBackStack(null);
		 transaction.commit();*/
	}
}


