package com.zzh.blunocoin.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.zzh.blunocoin.R;
import com.zzh.blunocoin.bluno.data.Message;
import com.zzh.blunocoin.data.Varinfo;
import com.zzh.blunocoin.tool.MyFragment;
import com.zzh.blunocoin.tool.StepView.XiaoMiSportView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class LightFragment extends MyFragment {

    private View view;
    private static Context context;

    private Unbinder mButterKnife;


    @Override
    public void onResume() {
        Varinfo.page = 4;
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            Varinfo.page = 4;
        }
        super.onHiddenChanged(hidden);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Varinfo.page = 4;
        view = inflater.inflate(R.layout.content_coin, container, false);
        context = getActivity();
        mButterKnife = ButterKnife.bind(this, view);
        content();
        onCreateProcess(context);                                                        //onCreate Process by BlunoLibrary
        serialBegin(115200);
        return view;
    }

    void content() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mButterKnife.unbind();
    }


}
