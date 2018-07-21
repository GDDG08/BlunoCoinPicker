package com.zzh.blunocoin.fragment;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.zzh.blunocoin.R;
import com.zzh.blunocoin.data.Varinfo;
import com.zzh.blunocoin.tool.MyFragment;

public class MainFragment extends MyFragment {

    //Unbinder unbinder;
    private View view;

    private Context context;


    @Override
    public void onResume() {
        Varinfo.page = 1;
        // onResumeProcess();
        // getActivity().setTitle(R.string.app_name);
        //unbinder = ButterKnife.bind(this, getView());
        //onResumeProcess();

        //onshowanima();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //myVideoView.stopPlayback();
    }

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

    void content() {

        if (!Varinfo.vid_showed) {
            System.out.println("showed=False");
            onshowanima();
        }else{
            myVideoView = view.findViewById(R.id.main_videoView);
            myImageView = view .findViewById(R.id.main_imageView);
            myImageView.setVisibility(View.VISIBLE);
            myVideoView.setVisibility(View.GONE);
        }
    }

    VideoView myVideoView;
    ImageView myImageView;

    public void onshowanima() {

        myVideoView = view.findViewById(R.id.main_videoView);
        myImageView = view .findViewById(R.id.main_imageView);
       /* videoView.setVideoPath(new File("/storage/emulated/0/vysor/main2.mp44").getAbsolutePath());
        videoView.start();*/

        //final String videoPath = new File("/storage/emulated/0/vysor/main2.mp44").getAbsolutePath();
        final String videoPath = Uri.parse("android.resource://"+context.getPackageName()+"/" + R.raw.main).toString();
        //播放
         myVideoView.setVideoPath(videoPath);
        myVideoView.start();
        Varinfo.vid_showed=true;
        myVideoView.setOnPreparedListener(mp -> {
            //mp.start();
            mp.setLooping(false);
            mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                        myVideoView.setBackgroundColor(Color.TRANSPARENT);
                    return true;
                }
            });
        });
        myVideoView.setOnCompletionListener(mp -> {
            // myVideoView.setVideoPath(videoPath);
            //myVideoView.start();
            myVideoView.stopPlayback();
//            myImageView.setVisibility(View.VISIBLE);
//            myVideoView.setVisibility(View.GONE);

        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myVideoView.stopPlayback();
        //unbinder.unbind();
    }


}
