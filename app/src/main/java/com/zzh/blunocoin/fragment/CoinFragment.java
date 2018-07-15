package com.zzh.blunocoin.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zzh.blunocoin.R;
import com.zzh.blunocoin.data.Varinfo;
import com.zzh.blunocoin.tool.MyFragment;
import com.zzh.blunocoin.tool.StepView.MySportView;
import com.zzh.blunocoin.tool.StepView.XiaoMiSportView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class CoinFragment extends MyFragment {
    @BindView(R.id.coin_StepView)
    XiaoMiSportView coinStepView;
    private View view;
    private static Context context;

    private Unbinder mButterKnife;

    @Override
    public void onResume() {
        Varinfo.page = 3;
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            Varinfo.page = 3;
        }
        super.onHiddenChanged(hidden);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Varinfo.page = 3;
        view = inflater.inflate(R.layout.content_coin, container, false);
        context = getActivity();
        mButterKnife = ButterKnife.bind(this, view);
        content();
        onCreateProcess(context);                                                        //onCreate Process by BlunoLibrary
        serialBegin(115200);
        return view;
    }

    void content() {
        coinStepView.setMaxNum(8000);
//        xiaoMiSportView.setCurrentNumAnim(4000);
        coinStepView.setCurrentNum(4000);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mButterKnife.unbind();
    }

    @OnClick(R.id.coin_StepView)
    public void onViewClicked() {
    }


    private String onreceive = "";
    private String received = "";
    @Override
    public void onSerialReceived(String theString) {                            //Once connection data received, this function will be called
        // TODO Auto-generated method stub
        //The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.
        onreceive += theString;
        if (theString.contains("@")) {
            received = onreceive.substring(0,onreceive.length()-3);
            //received = onreceive.deleteCharAt(buf.length()-1);
            onreceive = "";
            Toast.makeText(context,received,Toast.LENGTH_LONG).show();

        }

    }


}
