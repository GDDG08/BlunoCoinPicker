package com.zzh.blunocoin.data;
import android.widget.*;
import java.util.*;
import android.app.*;
import android.os.*;
import com.zzh.blunocoin.tool.*;
import com.zzh.blunocoin.*;

public class Varinfo
{

	public static MainActivity mainactivity;

	public static FrameLayout page_container;
	public static ProgressBar page_progress;
	public static ScrollView page_scroll;
	//fragment标签
	public static Fragment nowfrag;
	public static ArrayList<Fragment> hiddenfrag;

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



	public static Bundle save2(Bundle b)
	{
		Bundle bb=b;
		nowfrag_i = Utils.Frag2int(nowfrag);
		hiddenfrag_i = Utils.Frag2int(hiddenfrag);

		/**/bb.putInt ("nowfrag_i",nowfrag_i);
		if(hiddenfrag_i!=null)bb.putIntArray("hiddenfrag_i", hiddenfrag_i);
		/**/bb.putInt ("page",page);
		return bb;
	}
	public static Bundle save(Bundle b){return  b;}
	public static void load(Bundle b)
	{
		nowfrag_i=b.getInt ("nowfrag_i",nowfrag_i);
		hiddenfrag_i=b.getIntArray("hiddenfrag_i");
		page=b.getInt ("page",page);

		if(nowfrag_i!=0)nowfrag = Utils.int2Frag(nowfrag_i);
		if(hiddenfrag_i!=null)hiddenfrag = Utils.int2Frag(hiddenfrag_i);
		
	}
	
}
