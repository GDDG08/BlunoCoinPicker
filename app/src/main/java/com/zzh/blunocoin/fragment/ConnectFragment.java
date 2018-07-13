package com.zzh.blunocoin.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.zzh.blunocoin.R;
import com.zzh.blunocoin.bluno.BlunoLibrary;
import com.zzh.blunocoin.bluno.data.Message;
import com.zzh.blunocoin.data.MathAI;
import com.zzh.blunocoin.data.Varinfo;
import com.zzh.blunocoin.tool.MyFragment;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ConnectFragment extends MyFragment {
    @BindView(R.id.Coin10)
    TextView Coin10;
    @BindView(R.id.Coin5)
    TextView Coin5;
    @BindView(R.id.Coin1)
    TextView Coin1;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.picker)
    com.larswerkman.holocolorpicker.ColorPicker picker;
    @BindView(R.id.TestLayout)
    LinearLayout TestLayout;
    @BindView(R.id.serialReveicedText)
    TextView serialReceivedText;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.buttonSerialSend)
    Button buttonSerialSend;
    @BindView(R.id.buttonScan)
    Button buttonScan;
    @BindView(R.id.serialSendText)
    EditText serialSendText;
    @BindView(R.id.editText2)
    TextView editText2;
    private View view;
    private static Context context;

    private BlunoLibrary bluno;
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

        content();
        mButterKnife=ButterKnife.bind(this, view);
        return view;
    }

    void content() {
        bluno=new BlunoLibrary() {
            @Override
            public void onConectionStateChange(connectionStateEnum theconnectionStateEnum) {

            }

            @Override
            public void onSerialReceived(String theString) {

            }
        };


        buttonSerialSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                bluno.serialSend(serialSendText.getText().toString());                //send the data to the BLUNO
            }
        });

        buttonScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                bluno.buttonScanOnClickProcess();                                        //Alert Dialog for selecting the BLE device
            }
        });

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        bluno.serialSend("M"+progress+"@");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }

        );
        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                double R=(picker.getColor() & 0x00ff0000)>>16;
                double G=(picker.getColor() & 0x0000ff00)>>8 ;
                double B=(picker.getColor() & 0x000000ff)>>0;
                /*int an=0;
                if(R==255&&B==0){
                    an=G;
                }
                if(G==255&&B==0){
                    an=511 - R;
                }
                if(B==255&&R==0){
                    an=B + 511;
                }
                if(B==255&&R==0){
                    an=1022 - G;
                }
                if(R==255&&B==0){
                    an=R + 1022;
                }
                if(R==255&&G==0){
                    an=1533 - B;
                }
                int angle=an*120/511;*/

                Double[] i=new Double[]{R,G,B};
                double max= MathAI.getMax(i);
                double min= MathAI.getMin(i);
                Coin1.setText(max+"");
                Coin10.setText(min+"");
                double H=0;
                if (R == max){
                    H = (G-B)/(max-min);
                }
                if (G == max){
                    H = 2 + (B-R)/(max-min);
                }
                if (B == max){ H = 4 + (R-G)/(max-min);}

                H = H * 60;
                if (H < 0){
                    H = H + 360;
                }
                int angle=360-(int)H;
                //Toast.makeText(MainActivity.this,color+"\n"+R+"\n"+G+"\n"+B,Toast.LENGTH_LONG).show();
                Coin5.setText(angle+"");
                bluno.serialSend("M"+angle+"@");

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mButterKnife.unbind();
    }

    public void onConectionStateChange(BlunoLibrary.connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
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

    private String onreceive = "";
    private String received = "";


    public void onSerialReceived(String theString) {                            //Once connection data received, this function will be called
        // TODO Auto-generated method stub
        serialReceivedText.append(theString);                            //append the text into the EditText
        //The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.
        ((ScrollView) serialReceivedText.getParent()).fullScroll(View.FOCUS_DOWN);
        onreceive += theString;
        if (theString.contains("@")) {
            received = onreceive.substring(0,onreceive.length()-3);
            //received = onreceive.deleteCharAt(buf.length()-1);
            onreceive = "";
            Toast.makeText(context,received,Toast.LENGTH_LONG).show();
            testgson(received);
        }

    }
    public void testgson(String message) {
        Gson gson = new Gson();
        Message mess=gson.fromJson(message, Message.class);

        //Toast.makeText(this,mess.getC10()+mess.getC5()+mess.getC1()+"",Toast.LENGTH_LONG).show();
        Coin1.setText(mess.getC1()+"");
        Coin5.setText(mess.getC5()+"");
        Coin10.setText(mess.getC10()+"");

    }
}
