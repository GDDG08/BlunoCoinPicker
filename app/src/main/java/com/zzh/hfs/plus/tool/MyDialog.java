package com.zzh.hfs.plus.tool;
import android.view.*;
import android.widget.*;
import android.widget.RelativeLayout.*;
import com.zzh.hfs.plus.*;
import org.xclcharts.common.*;
import android.content.*;
import android.text.*;
import android.support.v7.app.*;
import org.xutils.*;
public class MyDialog
{
	public static Button button_edit;
    public static EditText et;

    public static View InputDialog(Context mContext){
        FrameLayout content = new FrameLayout(mContext);    
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        et=new EditText(mContext);
        et.setInputType(InputType.TYPE_CLASS_TEXT);

        et.addTextChangedListener(new TextWatcher(){
                @Override
                public void beforeTextChanged(CharSequence s,int start,int count,int after)
                {
                    // TODO: Implement this method
                }
                @Override
                public void onTextChanged(CharSequence s, int stsrt, int before, int count)
                {
					if(s.length()!=0){
						button_edit.setEnabled(true);
					}else{
						button_edit.setEnabled(false);
					}
                }
                @Override
                public void afterTextChanged(Editable s){

                }
			});


        final RelativeLayout chartLayout = new RelativeLayout(mContext);  
        chartLayout.addView( et,layoutParams);
        int width=DensityUtil.getWidth(chartLayout);
        layoutParams.leftMargin=width;
        layoutParams.rightMargin=width;
        layoutParams.topMargin=DensityUtil.getHeight(chartLayout)/2;
        chartLayout.setLayoutParams(layoutParams);
        ((ViewGroup) content).addView(chartLayout);    
        return content;
    }

}
