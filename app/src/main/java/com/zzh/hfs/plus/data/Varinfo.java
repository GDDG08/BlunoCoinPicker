package com.zzh.hfs.plus.data;
import android.content.*;
import com.umeng.onlineconfig.*;
import com.zzh.hfs.plus.*;
import org.json.*;
import android.support.v7.widget.*;
import com.zzh.hfs.plus.fragment.*;
import android.widget.*;
import java.util.*;
import android.app.*;
import android.graphics.drawable.*;
import android.support.design.widget.*;
import android.os.*;
import com.zzh.hfs.plus.tool.*;

public class Varinfo
{
	//不需要保存
	public static String versionname;
    public static int versioncode;
    public static SharedPreferences preferences_login;
    public static SharedPreferences preferences_user;

    public static SharedPreferences.Editor preferences_login_edit;
    public static SharedPreferences.Editor preferences_user_edit;
    public static SharedPreferences preferences_setting;

    public static SharedPreferences.Editor preferences_setting_edit;
    public static OnlineConfigAgent onconfig=OnlineConfigAgent.getInstance();

	public static String agent;

	public static String active_user;
	public static FrameLayout page_container;
	public static ProgressBar page_progress;
	public static ScrollView page_scroll;

	//离线模式配置文件
	public static SharedPreferences pre_offline;
	//图片预览传递
	public static Drawable pic_view;
	//用户SESSION值
    public static String cookie_value;

	public static MainActivity mainactivity;

	//fragment标签
	public static Fragment nowfrag;
	public static ArrayList<Fragment> hiddenfrag;

	public static boolean hasservice=false;

	//需要保存

    //统计图表相关
    public static Double exam_ratio;
    public static Double exam_ratio2;
    //模式
    public static int model_rank;
    //祸根
    public static JSONObject trends_data;
	//Trends的选择值
    public static int trends_position;
	public static String trends_examName;
	public static int trends_examClassRank;
	public static JSONArray trends_org;
	public static String examId_o;
	//Rank
	public static double rank_qd_graderatio;
    //fragment标签数字版
	public static int nowfrag_i;
	public static int[] hiddenfrag_i;

    public static int page=0;
    /****type一览表*********/
    /*
     1:Main
     2:Login
     3:Trends
     4:Rank
     5:Member
     6:Analysis
	 7.Anas
	 8:Paper
	 9:PaperShow
	 10:PhotoView

     */

	//安卓SDK版本
    public static int osver=0;
    //Rank的选择值
    public static int paper_pos=0;

	//是否显示捐赠
	public static boolean showjz =true;
	//相关考试信息
	public static String examId;
	public static String paperId;
	//错题查看序号辅助
	public static ArrayList<Integer> mistake_pos;

	//不再使用：
	//public static AnalysisFragment analysisfrag;
	//public static int adopen=0;
    //广告显示状态
    //public static Boolean online=false;
	//public static List<Integer> hiddenfrag_scroll;
	//public static Fragment lastfrag;
	//public static Toolbar toolbar;
	//public static int model=0;
	
	//测试
	public static JSONArray trends_datat;
	
	

	public static Bundle save(Bundle b)
	{
		Bundle bb=b;
		nowfrag_i = Utils.Frag2int(nowfrag);
		hiddenfrag_i = Utils.Frag2int(hiddenfrag);

		if(exam_ratio!=null)bb.putDouble("exam_ratio", exam_ratio);
		if(exam_ratio2!=null)bb.putDouble("exam_ratio2", exam_ratio2);
		/**/bb.putInt("model_rank", model_rank);
		if(trends_data!=null)bb.putString("trends_data", trends_data.toString());
		/**/bb.putInt("trends_position", trends_position);
		/**/bb.putString("trends_examName", trends_examName);
		/**/bb.putInt("trends_examClassRank", trends_examClassRank);
		if(trends_org!=null)bb.putString("trends_org", trends_data.toString());
		/**/bb.putString("examId_o",examId_o);
		/**/b.putDouble("rank_qd_graderatio",rank_qd_graderatio);
		/**/bb.putInt ("nowfrag_i",nowfrag_i);
		if(hiddenfrag_i!=null)bb.putIntArray("hiddenfrag_i", hiddenfrag_i);
		/**/bb.putInt ("page",page);
		/**/bb.putInt ("osver",osver);
		/**/bb.putInt ("paper_pos",paper_pos);
		/**/bb.putBoolean ("showjz",showjz);
		/**/bb.putString ("examId",examId);
		/**/bb.putString ("paperId",paperId);
		if(mistake_pos!=null)bb.putIntArray("mistake_pos",Utils.Array2int(mistake_pos));
		return bb;
	}
	
	public static void load(Bundle b)
	{
		exam_ratio=b.getDouble("exam_ratio");
		exam_ratio2=b.getDouble("exam_ratio2");
		model_rank=b.getInt("model_rank",model_rank);
		try
		{
			String s_td = b.getString("trends_data",null);
			if(s_td!=null)trends_data = new JSONObject(s_td);
		}
		catch (JSONException e)
		{}
		trends_position = b.getInt("trends_position", trends_position);
		trends_examName=b.getString("trends_examName", trends_examName);
		trends_examClassRank=b.getInt("trends_examClassRank", trends_examClassRank);
		try
		{
			String s_td2 = b.getString("trends_org",null);
			if(s_td2!=null)trends_org = new JSONArray(s_td2);
		}
		catch (JSONException e)
		{}
		examId_o=b.getString("examId_o",examId_o);
		rank_qd_graderatio=b.getDouble("rank_qd_graderatio",rank_qd_graderatio);
		nowfrag_i=b.getInt ("nowfrag_i",nowfrag_i);
		hiddenfrag_i=b.getIntArray("hiddenfrag_i");
		page=b.getInt ("page",page);
		osver=b.getInt ("osver",osver);
		paper_pos=b.getInt ("paper_pos",paper_pos);
		showjz=b.getBoolean ("showjz",showjz);
		examId=b.getString ("examId",examId);
		paperId=b.getString ("paperId",paperId);
		int[] intArray = b.getIntArray("mistake_pos");
		if(intArray!=null)mistake_pos = Utils.int2Array(intArray);

		if(nowfrag_i!=0)nowfrag = Utils.int2Frag(nowfrag_i);
		if(hiddenfrag_i!=null)hiddenfrag = Utils.int2Frag(hiddenfrag_i);
		
	}
	
}
