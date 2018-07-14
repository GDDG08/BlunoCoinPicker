package com.zzh.blunocoin.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zzh.blunocoin.R;
import com.zzh.blunocoin.data.Varinfo;
import com.zzh.blunocoin.tool.MyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ConnectFragment extends MyFragment {
    @BindView(R.id.serialReveicedText)
    TextView serialReveicedText;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.buttonScan)
    Button buttonScan;
    private View view;
    private static Context context;

    private Unbinder mButterKnife;

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

    @OnClick(R.id.buttonScan)
    void finishA(View view) {
        //buttonScanOnClickProcess();

                String mDeviceName="GDDG Bluno";
                String mDeviceAddress="88:33:14:DC:6F:F8";

                if (mBluetoothLeService.connect(mDeviceAddress)) {
//                    Log.d(TAG, "Connect request success");
                    mConnectionState= connectionStateEnum.isConnecting;
                    onConectionStateChange(mConnectionState);
                    mHandler.postDelayed(mConnectingOverTimeRunnable, 10000);
                }
                else {
//                    Log.d(TAG, "Connect request fail");
                    mConnectionState= connectionStateEnum.isToScan;
                    onConectionStateChange(mConnectionState);
                }                                    //Alert Dialog for selecting the BLE device
    }
    void content() {

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
                buttonScan.setText("Connected");
                break;
            case isConnecting:
                buttonScan.setText("Connecting");
                break;
            case isToScan:
                buttonScan.setText("Scan");
                break;
            case isScanning:
                buttonScan.setText("Scanning");
                break;
            case isDisconnecting:
                buttonScan.setText("isDisconnecting");
                break;
            default:
                break;
        }
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
