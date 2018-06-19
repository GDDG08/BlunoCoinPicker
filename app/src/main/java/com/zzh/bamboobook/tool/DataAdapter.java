package com.zzh.bamboobook.tool;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import org.json.*;
import java.text.*;
import android.support.v7.widget.*;
import android.graphics.*;
import com.zzh.bamboobook.data.*;
import com.zzh.bamboobook.*;
import java.util.*;
import android.widget.*;
import com.zzh.bamboobook.fragment.*;
import org.xutils.*;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>{ //implements Filterable {
    private JSONArray mArrayList;
    private JSONArray mFilteredList;

	private DataAdapter.MyItemOnClickListener mMyItemOnClickListener;

    public DataAdapter(JSONArray arrayList,boolean b) {
		mArrayList = arrayList;
		if(!b){

			mFilteredList = arrayList;
		}else{
			JSONArray filteredList = new JSONArray();

			ArrayList<Integer> li=new ArrayList<Integer>();
	//		PaperDetail details = new PaperDetail();
			for (int i=0;i<mArrayList.length();i++) {

				/*try
				{
			//		details.setjson(mArrayList.getJSONObject(i));
				}
				catch (JSONException e)
				{}*/
			//	if (!details.getScore().equals(details.getRealScore())){

			///		filteredList.put(details.jso);
					li.add(i);
			//	}
			}
		//	mFilteredList=filteredList;
		//	Varinfo.mistake_pos=li;
		}

    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.paper_card_row, viewGroup, false);
        return new ViewHolder(view,mMyItemOnClickListener);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {
	//	PaperDetail details=new PaperDetail();
		JSONObject jso=null;
		try
		{
			jso=mFilteredList.getJSONObject((i));
		}
		catch (JSONException e)
		{}
		if(jso!=null){
	//	details.setjson(jso);
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
       // viewHolder.tv_name.setText(details.getShortName());
      //  String score = decimalFormat.format(details.getScore());
		//viewHolder.tv_version.setText("满分："+score+"分");
		//Toast.makeText(x.app(),details.getRealScore()+"",1).show();
      //  String realscore = decimalFormat.format(details.getRealScore());
		//viewHolder.tv_api_level.setText("得分："+realscore+"分");

	//	int isWrong=details.getisWrong();
		/*if(i==0){
			viewHolder.cd_bk.setBackgroundColor(Color.parseColor("#FDD835"));
		}else{
		if(isWrong==2){
			viewHolder.cd_bk.setBackgroundColor(Color.parseColor("#4CAF50"));
		}else{
			viewHolder.cd_bk.setBackgroundColor(Color.parseColor("#F44336"));
		}
		}*/
		}else{
		 viewHolder.tv_name.setText("暂无错题");
		 }
    }

    @Override
    public int getItemCount() {
        return mFilteredList.length();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_name,tv_version,tv_api_level;
		private CardView cd_bk;
		private CardView card;

		MyItemOnClickListener mListener;
        public ViewHolder(View view,MyItemOnClickListener myItemOnClickListener) {
            super(view);

            tv_name = (TextView)view.findViewById(R.id.tv_name);
            tv_version = (TextView)view.findViewById(R.id.tv_version);
            tv_api_level = (TextView)view.findViewById(R.id.tv_api_level);

			cd_bk=(CardView)view.findViewById(R.id.card_back);
			card=(CardView)view.findViewById(R.id.card);

			this.mListener=myItemOnClickListener;

            view.setOnClickListener(this);
        } 
		@Override
        public void onClick(View view) {
            if(mListener!=null){
                mListener.onItemOnClick(view,getPosition());
			}}}

	public interface MyItemOnClickListener {
		public void onItemOnClick(View view,int postion);
	}

	public void setItemOnClickListener(MyItemOnClickListener listener){
        mMyItemOnClickListener=listener;
    }
}
