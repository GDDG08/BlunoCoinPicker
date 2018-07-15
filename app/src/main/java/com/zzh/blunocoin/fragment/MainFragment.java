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
import android.widget.TextView;

import com.zzh.blunocoin.R;
import com.zzh.blunocoin.bluno.BlunoLibrary;
import com.zzh.blunocoin.data.Varinfo;
import com.zzh.blunocoin.tool.MyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainFragment extends MyFragment {

//    @BindView(R.id.main666)
//    TextView main666;
    Unbinder unbinder;
    private View view;

    private Context context;

    @Override
    public void onResume() {
        Varinfo.page = 1;
       // onResumeProcess();
        // getActivity().setTitle(R.string.app_name);
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
        content();
        // MT.finish();
        Varinfo.page_container.setVisibility(View.VISIBLE);
        Varinfo.page_progress.setVisibility(View.GONE);
        unbinder = ButterKnife.bind(this, getView());
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
            //TODO
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void content() {

//        main666.setText("666");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
