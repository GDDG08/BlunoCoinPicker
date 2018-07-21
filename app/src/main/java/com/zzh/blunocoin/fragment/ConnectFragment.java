package com.zzh.blunocoin.fragment;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.skyfishjy.library.RippleBackground;
import com.zzh.blunocoin.R;
import com.zzh.blunocoin.data.Varinfo;
import com.zzh.blunocoin.tool.Msg;
import com.zzh.blunocoin.tool.MyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ConnectFragment extends MyFragment {


    @BindView(R.id.imageView_animate)
    ImageView imageViewAnimate;
    @BindView(R.id.centerImage)
    ImageView centerImage;
    @BindView(R.id.content)
    RippleBackground content;

    private View view;
    private static Context context;

    private Unbinder mButterKnife;
    private AnimationDrawable animationDrawable;

    @Override
    public void onResume() {
        Varinfo.page = 2;
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            Varinfo.page = 2;
        }
        super.onHiddenChanged(hidden);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Varinfo.page = 2;
        view = inflater.inflate(R.layout.content_connect, container, false);
        context = getActivity();
        mButterKnife = ButterKnife.bind(this, view);
        content();
        onCreateProcess(context);                                                        //onCreate Process by BlunoLibrary
        serialBegin(115200);
        return view;
    }


    @OnClick(R.id.imageView_animate)
    public void onViewClicked() {


    }

    void content() {
       /* animationDrawable = (AnimationDrawable) getResources().getDrawable(
                R.drawable.frame_appearance);
        imageViewAnimate.setBackground(animationDrawable);*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mButterKnife.unbind();
    }

    @Override
    public void onConectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
        switch (theConnectionState) {                                            //Four connection state
            case isConnected:
               // buttonScan.setText("Connected");
                Varinfo.connected = true;
                content.stopRippleAnimation();
                Msg.Snack(view,"Bluebit连接成功！");
                break;
            case isConnecting:
               // buttonScan.setText("Connecting");
                content.startRippleAnimation();
                break;
            case isToScan:
               // buttonScan.setText("Scan");
                Varinfo.connected = false;
                content.stopRippleAnimation();
                break;
            case isScanning:
               // buttonScan.setText("Scanning");
                break;
            case isDisconnecting:
              //  buttonScan.setText("isDisconnecting");
                Varinfo.connected = false;
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.centerImage, R.id.content})
    public void onViewClicked(View view) {
        //switch (view.getId()) {
            /*case R.id.centerImage:
                //content.startRippleAnimation();
                break;
            case R.id.content:*/

                if (Varinfo.connected) {

                    buttonScanOnClickProcess();
                    //Varinfo.connected=false;
                } else {

                    String mDeviceName = "GDDG Bluno";
                    String mDeviceAddress = "88:33:14:DC:6F:F8";

                    if (mBluetoothLeService.connect(mDeviceAddress)) {
//                    Log.d(TAG, "Connect request success");
                        mConnectionState = connectionStateEnum.isConnecting;
                        onConectionStateChange(mConnectionState);
                        mHandler.postDelayed(mConnectingOverTimeRunnable, 10000);
                    } else {
//                    Log.d(TAG, "Connect request fail");
                        mConnectionState = connectionStateEnum.isToScan;
                        onConectionStateChange(mConnectionState);
                    }                                    //Alert Dialog for selecting the BLE device

                }
//                break;
//        }
    }


/*
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
*/

}
