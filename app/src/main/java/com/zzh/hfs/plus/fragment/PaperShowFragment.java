package com.zzh.hfs.plus.fragment;
import android.content.*;
import android.graphics.drawable.*;
import android.os.*;
import android.support.annotation.*;
import android.view.*;
import android.widget.*;
import com.zzh.hfs.plus.*;
import com.zzh.hfs.plus.data.*;
import com.zzh.hfs.plus.tool.*;
import org.json.*;
import org.xutils.*;
import org.xutils.image.*;
import com.zzh.hfs.plus.fragment.PaperShowFragment.*;
import android.text.*;
import android.text.style.*;
import android.graphics.*;
import android.widget.FrameLayout.*;
import android.util.*;
import java.math.*;
import android.support.v7.widget.*;

public class PaperShowFragment extends MyFragment
{   private View view;
    private Context context;

	private JSONObject json;

	private ImageView i_m;

	private ImageView i_p;

	private View.OnClickListener onClick=new View.OnClickListener(){

		@Override
		public void onClick(View p1)
		{
			//Msg.Snack(view,"点了一下1");
			ImageView imv=(ImageView) p1;
			Drawable da=imv.getDrawable();
			Varinfo.pic_view = da;

			/*Fragment f=new PhotoViewFragment(da);
			 Varinfo.mainactivity.Page(f);*/

			 Varinfo.hasservice=true;
			Intent i= new Intent(context, PhotoViewActivity.class);
			try
			{
				i.putExtra("examName", json.getString("name"));
			}
			catch (JSONException e)
			{
				i.putExtra("examName","noname");
			}
			startActivity(i);
		}
	};

	private Double score;
	private Double realScore;
	

	/*public PaperShowFragment()
	{}

    public PaperShowFragment(JSONObject json)
	{
		this.json = json;

    }*/
    @Override
    public void onResume()
    {
        Varinfo.page = 9;
        //getActivity().setTitle(R.string.app_name);
        super.onResume();
    }

	@Override
	public void onHiddenChanged(boolean hidden)
	{
		if (!hidden)
		{
			Varinfo.page = 9;}
		super.onHiddenChanged(hidden);
	}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Varinfo.page = 9;
        view = inflater.inflate(R.layout.content_papershow, container, false);
        context = getActivity();
		
        /*try
		{*/
			/*new Handler().postDelayed(new Runnable(){

					@Override
					public void run()
					{*/
						try
						{
							json=new JSONObject(getArguments().getString("json"));
							content();
						}
						catch (JSONException e)
						{}//}}, 2000);
		/*}
		catch (JSONException e)
		{
			//Log.e("HFSPLUS", "error");
			Toast.makeText(context, e.getMessage() + "\n" + e.toString() , 1).show();

		}*/
        return view;
    }
    void content() throws JSONException
    {

		int type=1;
		type = json.getInt("type");

		i_m = (ImageView) view.findViewById(R.id.paperImage_m);
		i_p = (ImageView) view.findViewById(R.id.paperImage_p);

		image(type);

		i_m.setOnClickListener(onClick);
		i_p.setOnClickListener(onClick);

		int isWrong=json.getInt("isWrong");

		TextView tv_n=(TextView) view.findViewById(R.id.ps_name);

		int index=json.getString("examName").length();
		String string = json.getString("name");
		String name=string;
		try
		{
			name = string.substring(index);
		}
		catch (StringIndexOutOfBoundsException e)
		{}
		tv_n.setText(name);

		TextView tv_s=(TextView) view.findViewById(R.id.ps_score);
		score=json.getDouble("score");
		realScore=json.getDouble("realScore");
		tv_s.setText(color(isWrong, realScore + "分/共" + score + "分"));

		if (type == 2)
		{
			TextView tv_a_c=(TextView) view.findViewById(R.id.ps_answer_choice);
			String answer=json.getString("answer");
			String myAnswer=json.getString("myAnswer");
			SpannableStringBuilder s=color(2, answer)
				.append("，考试作答是")
				.append(color(isWrong, myAnswer))
				.insert(0, "正确答案是");
			tv_a_c.setText(s);
		}
		if(type!=3){
		analysis();
		}else{
			((CardView)view.findViewById(R.id.ps_card_paper)).setVisibility(View.GONE);
			((CardView)view.findViewById(R.id.ps_card_star)).setVisibility(View.GONE);
			((CardView)view.findViewById(R.id.ps_card_ana)).setVisibility(View.GONE);
			
		}
	}
	public static SpannableStringBuilder color(int type, String text)
	{
		int color=Color.parseColor("#4CAF50");
		if (type == 1)
		{
			color = Color.parseColor("#F44336");
		}
		SpannableStringBuilder spannableString = new SpannableStringBuilder(text);
		ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
		spannableString.setSpan(colorSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	void image(int type) throws JSONException
	{
		ImageOptions options=new ImageOptions.Builder()
//设置加载过程中的图片
			.setLoadingDrawableId(R.drawable.pic_null)
//设置加载失败后的图片
			.setFailureDrawableId(R.drawable.pic_null)
//设置使用缓存
			.setUseMemCache(true)
//设置显示圆形图片
			//.setCircular(true)
//设置支持gif
			//.setIgnoreGif(false)    //以及其他方法
			.setFadeIn(true)
			.build();

		JSONArray ja0 = json.getJSONArray("pictures");
		int l0=ja0.length();
		if (l0 >= 1)
		{
			String image_p_url=ja0.getString(0);
			x.image().bind(i_p, image_p_url, options);

			if (l0 >= 2)
			{
				LinearLayout li_p=(LinearLayout) view.findViewById(R.id.ps_li_p);
				for (int i0=1;i0 < ja0.length();i0++)
				{
					String image_p_url2=ja0.getString(i0);
					ImageView i_p2=new ImageView(context);
					i_p2.setOnClickListener(onClick);
					i_p2.setAdjustViewBounds(true);
					LayoutParams lp0=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
					lp0.setMargins(0, 10, 0, 0);
					li_p.addView(i_p2, lp0);
					x.image().bind(i_p2, image_p_url2, options);
				}
			}
		}else{
			TextView tv_n=(TextView)view.findViewById(R.id.ps_tv_nopaper);
			tv_n.setVisibility(View.VISIBLE);
		}
		
		if (type != 2)
		{
			JSONArray ja=json.getJSONArray("myAnswers");
			String image_url=ja.getString(0);
			x.image().bind(i_m, image_url, options);

			int l=ja.length();
			if (l >= 2)
			{
				LinearLayout li_m=(LinearLayout) view.findViewById(R.id.ps_li_m);
				for (int i=1;i < ja.length();i++)
				{
					String image_url2=ja.getString(i);
					ImageView i_m2=new ImageView(context);
					i_m2.setOnClickListener(onClick);
					i_m2.setAdjustViewBounds(true);
					LayoutParams lp=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
					lp.setMargins(0, 10, 0, 0);
					li_m.addView(i_m2, lp);
					x.image().bind(i_m2, image_url2, options);
				}
			}
		}
	}
	
	private int classNum;
	private double classScore;
	private int classManfen;
	private int gradeNum;
	private double gradeScore;
	private int gradeManfen;
	
	void analysis() throws JSONException{
		classNum=json.getInt("classNum");
        classScore=json.getDouble("classScore"); 
        classManfen=json.getInt("classManfen");
        gradeNum=json.getInt("gradeNum");
        gradeScore=json.getDouble("gradeScore");
        gradeManfen=json.getInt("gradeManfen");
		
		Double 难度=1-((gradeScore/gradeNum)/score);
		//ImageView star1=(ImageView)view.findViewById(R.id.ps_hard_star1);
		ImageView star2=(ImageView)view.findViewById(R.id.ps_hard_star2);
		ImageView star3=(ImageView)view.findViewById(R.id.ps_hard_star3);
		//star1.setImageResource(R.drawable.star_yellow);
		if(难度>0.25) star2.setImageResource(R.drawable.star_yellow);
		if(难度>0.55) star3.setImageResource(R.drawable.star_yellow);
		//Toast.makeText(context,难度.toString(),1).show();
		
		((TextView)view.findViewById(R.id.ps_ana_ca)).setText(format(classScore/classNum));
		((TextView)view.findViewById(R.id.ps_ana_ga)).setText(format(gradeScore/gradeNum));
		((TextView)view.findViewById(R.id.ps_ana_cm)).setText(classManfen+"人");
		((TextView)view.findViewById(R.id.ps_ana_gm)).setText(gradeManfen+"人");
		((TextView)view.findViewById(R.id.ps_ana_hard)).setText(format(难度));
		
	}
	
	String format(Double d){
		return new BigDecimal(d).setScale(3,RoundingMode.HALF_UP).toString();
	}
}
