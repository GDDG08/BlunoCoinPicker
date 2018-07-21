package com.zzh.blunocoin.data;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.widget.*;
import java.util.*;
import android.app.*;
import android.os.*;

import com.zzh.blunocoin.bluno.BluetoothLeService;
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
     2:Connect
     3:Coin
     4:Light
     5:Game
     6:DevDebug

     */
//Bluno
	public static BluetoothGatt mBluetoothGatt;
    public static BluetoothLeService mBluetoothLeService;
	public static BluetoothAdapter mBluetoothAdapter;
    public static boolean connected=false;

    public static boolean paused=false;

    public static boolean onFreshing=false;

    /*
    灯光
    2单色小板
    3Bluebit模式一
    4Bluebit模式二
     */
    public static boolean Light2=false;
	public static boolean Light3=false;
	public static boolean Light4=false;

	//硬币
    public static int C1=0;
    public static int C5=0;
    public static int C10=0;

    //首页动画
	public static boolean vid_showed=false;


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
