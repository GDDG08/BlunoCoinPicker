package com.zzh.bamboobook.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ProgressBar;

import com.zzh.bamboobook.R;
import com.zzh.bamboobook.data.HTTP;
import com.zzh.bamboobook.data.HTTPCallBack;
import com.zzh.bamboobook.data.Varinfo;
import com.zzh.bamboobook.tool.Msg;
import com.zzh.bamboobook.tool.MyFragment;
import com.zzh.bamboobook.tool.MyListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import android.support.v4.app.*;

public class NewNoteFragment extends MyFragment
{


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
				//		item(exam_name,itlistener);
					}}, 400);
		
		}
		super.onHiddenChanged(hidden);
	}

	
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Varinfo.page = 3;
        View view = inflater.inflate(R.layout.content_newnote, container, false);
        context = getActivity();
       /* switch (Varinfo.model_rank)
		{
            case 1:
                getActivity().setTitle(R.string.title_rank);
				break;
            case 2:
                getActivity().setTitle(R.string.title_ana);
				break;
			case 3:
				getActivity().setTitle(R.string.title_pap);
        }*/
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
		

	}


}
