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
import com.zzh.blunocoin.tool.ScrollNumber.ScrollNumberView;
import com.zzh.blunocoin.tool.StepView.XiaoMiSportView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class CoinFragment extends MyFragment {
    @BindView(R.id.coin_StepView)
    XiaoMiSportView coinStepView;
    @BindView(R.id.coin_Scrollnum_1)
    ScrollNumberView coinScrollnum1;
    @BindView(R.id.coin_Scrollnum_5)
    ScrollNumberView coinScrollnum5;
    @BindView(R.id.coin_Scrollnum_10)
    ScrollNumberView coinScrollnum10;
    private View view;
    private static Context context;

    private Unbinder mButterKnife;

    Handler freshanim = new Handler();
    Runnable freshanim_run = new Runnable() {
        @Override
        public void run() {
            coinStepView.setIsNeedFresh(false);
            Varinfo.onFreshing = false;
        }
    };


    @Override
    public void onResume() {
        Varinfo.page = 3;
        serialSend("V");
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
//        serialBegin(115200);
        new Handler().postDelayed(() -> {
            serialSend("V");
        },500);
        return view;
    }

    void content() {
        coinStepView.setMaxNum(1000);
//        xiaoMiSportView.setCurrentNumAnim(4000);
        coinStepView.setCurrentNum(Varinfo.C1 + Varinfo.C5 * 5 + Varinfo.C10 * 10);
        coinStepView.setIsNeedFresh(false);
        Varinfo.onFreshing = false;

        coinScrollnum1.setNumber(Varinfo.C1);
        coinScrollnum5.setNumber(Varinfo.C5);
        coinScrollnum10.setNumber(Varinfo.C10);


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
    //    private String onreceive0 = "";
    private String received = "";
    private String received0 = "";
    private boolean coinmode = false;
//    private boolean coinmode0 = false;

    @Override
    public void onSerialReceived(String theString) {                            //Once connection data received, this function will be called
        // TODO Auto-generated method stub
        //The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.
        if (theString.contains("$")) {
            coinmode = true;
            if (!theString.substring(0, 1).equals("$")) {
                theString = theString.split("$")[1];//接受混乱没有容错
            }
            onreceive += theString;
        } else if (coinmode) {
            onreceive += theString;
            if (theString.contains("@")) {
                received = onreceive.substring(1, onreceive.length() - 3);
                onreceive = "";
                coinmode = false;
                System.out.print(received);
                testgson(received);
                freshanim.removeCallbacks(freshanim_run);
                freshanim.postDelayed(freshanim_run, 1500);

            }
        } else if (theString.contains("%")) {
            onreceive = "";
            received0 = theString.substring(1, 1);
            coinStepView.setIsNeedFresh(true);
            Varinfo.onFreshing = true;
            System.out.print(received0);
        }




        /*else if (theString.contains("%")) {
            coinmode0 = true;
            if (!theString.substring(0, 1).equals("%")) {
                theString = theString.split("%")[1];//接受混乱没有容错
            }
            onreceive0 += theString;
        } else if (coinmode0) {
            onreceive0 += theString;
            if (theString.contains("#")) {
                received0 = onreceive.substring(1, 1);
                onreceive0 = "";
                coinmode0 = false;
                coinStepView.setIsNeedFresh(true);
                System.out.print(received0);
            }
        }*/
    }

    public void testgson(String message) throws IllegalStateException {
        Gson gson = new Gson();
        Message mess = gson.fromJson(message, Message.class);

        Varinfo.C1 = mess.getC1();
        Varinfo.C5 = mess.getC5();
        Varinfo.C10 = mess.getC10();
        int sum = Varinfo.C1 + Varinfo.C5 * 5 + Varinfo.C10 * 10;

        coinStepView.setCurrentNum(sum);


        coinScrollnum1.setNumber(Varinfo.C1);
        coinScrollnum5.setNumber(Varinfo.C5);
        coinScrollnum10.setNumber(Varinfo.C10);


        switch (mess.getIM()){
            case 1:
                coinScrollnum1.setNumber(Varinfo.C1-1);
                coinScrollnum1.increment();
                break;
            case 2:
                coinScrollnum5.setNumber(Varinfo.C5-1);
                coinScrollnum5.increment();
                break;
            case 3:
                coinScrollnum10.setNumber(Varinfo.C10-1);
                coinScrollnum10.increment();
                break;
        }

        //Toast.makeText(this,mess.getC10()+mess.getC5()+mess.getC1()+"",Toast.LENGTH_LONG).show();
           /* Coin1.setText(mess.getC1() + "");
            Coin5.setText(mess.getC5() + "");
            Coin10.setText(mess.getC10() + "");*/


    }


}
