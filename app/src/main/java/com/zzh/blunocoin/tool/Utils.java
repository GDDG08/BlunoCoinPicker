package com.zzh.blunocoin.tool;
import android.app.*;
import com.zzh.blunocoin.fragment.*;
import java.util.*;

public class Utils
{
	public static int Frag2int(Fragment frag){
		int f = 0;
		String i=frag.getClass().toString();
		switch(i){
			case "MainFragment":
				f=1;
				break;
			case "ConnectFragment":
				f=2;
				break;

		}
		return f;
	}
	public static Fragment int2Frag(int i){
		Fragment f = null;
		switch(i){
			case 1:
				f=new MainFragment();
				break;
			case 2:
				f=new ConnectFragment();
				break;
		}
		return f;
	}
	public static int[] Frag2int(ArrayList<Fragment> alf){
		int[] ali=new int[alf.toArray().length];
		for (int i=0;i<alf.toArray().length-1;i++){
			ali[i]=Frag2int(alf.get(i));
		}
		return ali;
	}
	
	public static ArrayList<Fragment> int2Frag(int[] ali){
		ArrayList<Fragment>alf=new ArrayList<Fragment>();
		for (int i=0;i<ali.length-1;i++){
			alf.add(int2Frag(ali[i]));
		}
		return alf;
	}
	public static int[] Array2int(ArrayList<Integer> ali){
		int[] it=new int[ali.toArray().length];
		for (int i=0;i<ali.toArray().length-1;i++){
			it[i]=ali.get(i);
		}
		return it;
	}
	public static ArrayList<Integer> int2Array(int[] it){
		ArrayList<Integer>ali=new ArrayList<Integer>();
		for (int i=0;i<it.length-1;i++){
			ali.add(it[i]);
		}
		return ali;
	}
}
