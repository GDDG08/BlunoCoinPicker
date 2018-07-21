package com.zzh.blunocoin.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zzh.blunocoin.R;
import com.zzh.blunocoin.data.Varinfo;
import com.zzh.blunocoin.tool.MyFragment;

import org.limlee.hiframeanimationlib.FrameAnimationView;
import org.limlee.hiframeanimationlib.FrameDrawable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainFragment extends MyFragment {

    //    @BindView(R.id.main666)
//    TextView main666;
    //Unbinder unbinder;
    //@BindView(R.id.frame_animation)
    FrameAnimationView mFrameAnimationView;
    private View view;

    private Context context;

    private static final String FRAME_NAME = "coinaer";

    @Override
    public void onResume() {
        Varinfo.page = 1;
        // onResumeProcess();
        // getActivity().setTitle(R.string.app_name);
        //unbinder = ButterKnife.bind(this, getView());
        //onResumeProcess();
        super.onResume();
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        onPauseProcess();
//        //if(!Varinfo.hasservice) finish();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        onStopProcess();
//        // finish();
//    }
//
//    @Override
//    public void onDestroy() {
//        //finish();
//        super.onDestroy();
//        onDestroyProcess();
//    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            Varinfo.page = 1;
            getActivity().setTitle(R.string.app_name);
        }
        super.onHiddenChanged(hidden);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Varinfo.page = 1;
        view = inflater.inflate(R.layout.content_main, container, false);
        //context = getContext();
        context = getActivity();
        setHasOptionsMenu(true);
        Varinfo.page_container.setVisibility(View.VISIBLE);
        Varinfo.page_progress.setVisibility(View.GONE);
        //unbinder = ButterKnife.bind(context, view);
        mFrameAnimationView=(FrameAnimationView)view.findViewById(R.id.frame_animation);
        content();
        // MT.finish();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
//            Varinfo.C1=0;
//            Varinfo.C5=0;
//            Varinfo.C10=0;

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void content() {/*
        List<String> frameList = null;
        try {
            final String[] frames = context.getAssets().list(FRAME_NAME);
            if (null != frames) {
                frameList = Arrays.asList(frames);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //按帧图片的序列号排序
        if (null != frameList) {
            Collections.sort(frameList, new Comparator<String>() {

                private final String MATCH_FRAME_NUM = String.format("(?<=%s_).*(?=.jpg)", FRAME_NAME);
                private final Pattern p = Pattern.compile(MATCH_FRAME_NUM);

                @Override
                public int compare(String lhs, String rhs) {
                    try {
                        final Matcher lhsMatcher = p.matcher(lhs);
                        final Matcher rhsMatcher = p.matcher(rhs);
                        if (lhsMatcher.find()
                                && rhsMatcher.find()) {
                            return Integer.valueOf(lhsMatcher.group()) - Integer.valueOf(rhsMatcher.group());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
            //添加序列帧
            List<FrameDrawable> frameDrawables = new ArrayList<>();
            for (String framePath : frameList) {
                FrameDrawable frameDrawable = new FrameDrawable(FRAME_NAME + "/" + framePath, 5);
                frameDrawables.add(frameDrawable);
            }

            mFrameAnimationView.setOneShot(false); //循环播放帧动画
            mFrameAnimationView.addFrameDrawable(frameDrawables); //添加序列帧
            mFrameAnimationView.setOnFrameListener(new FrameAnimationView.OnFrameListener() { //添加监听器
                @Override
                public void onFrameStart() {
                    //Log.d(TAG, "帧动画播放开始！");
                }

                @Override
                public void onFrameEnd() {
                    //Log.d(TAG, "帧动画播放结束！");
                }
            });
            mFrameAnimationView.start(); //开始播放
        }*/

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();/*
        mFrameAnimationView.stop(); //停止播放
        mFrameAnimationView.setOnFrameListener(null); //移除监听器*/
        //unbinder.unbind();
    }


}
