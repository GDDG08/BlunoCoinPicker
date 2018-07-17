package com.zzh.blunocoin.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.zzh.blunocoin.R;
import com.zzh.blunocoin.bluno.data.Message;
import com.zzh.blunocoin.data.MathAI;
import com.zzh.blunocoin.data.Varinfo;
import com.zzh.blunocoin.tool.MyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class DeveloperFragment extends MyFragment {

    @BindView(R.id.Coin10)
    TextView Coin10;
    @BindView(R.id.Coin5)
    TextView Coin5;
    @BindView(R.id.Coin1)
    TextView Coin1;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.dev_switch1_light)
    Switch devSwitch1Light;
    @BindView(R.id.dev_switch2_light_single)
    Switch devSwitch2LightSingle;
    @BindView(R.id.dev_switch3_bluebit1)
    Switch devSwitch3Bluebit1;
    @BindView(R.id.dev_switch4_bluebit2)
    Switch devSwitch4Bluebit2;
    @BindView(R.id.dev_button_test)
    Button devButtonTest;
    @BindView(R.id.serialReceivedText)
    TextView serialReceivedText;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.buttonSerialSend)
    Button buttonSerialSend;
    @BindView(R.id.serialSendText)
    EditText serialSendText;

    private View view;
    private static Context context;
    //
//    private BlunoLibrary bluno;
    private Unbinder mButterKnife;

    @Override
    public void onResume() {
        Varinfo.page = 6;
        mButterKnife = ButterKnife.bind(this, getView());
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            Varinfo.page = 6;
        }
        super.onHiddenChanged(hidden);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Varinfo.page = 6;
        view = inflater.inflate(R.layout.content_devdebug, container, false);
        context = getActivity();
        mButterKnife = ButterKnife.bind(this, view);
        content();
        onCreateProcess(context);                                                        //onCreate Process by BlunoLibrary
        serialBegin(115200);
        return view;
    }

    void content() {

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        serialSend("M" + progress + "@");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                        devSwitch2LightSingle.setChecked(true);
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

        devSwitch1Light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    devSwitch2LightSingle.setChecked(false);
                    serialSend("L");
                }else{
                    serialSend("lll");
                }
            }
        });

        devSwitch2LightSingle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    devSwitch1Light.setChecked(false);
                    serialSend("F");
                }else{
                    serialSend("lll");
                }
            }
        });

        devSwitch3Bluebit1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    serialSend("B");
                }else{
                    serialSend("c");
                }
            }
        });

        devSwitch4Bluebit2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    serialSend("b");
                }else{
                    serialSend("c");
                }
            }
        });





        /*
        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                double R = (picker.getColor() & 0x00ff0000) >> 16;
                double G = (picker.getColor() & 0x0000ff00) >> 8;
                double B = (picker.getColor() & 0x000000ff) >> 0;


                Double[] i = new Double[]{R, G, B};
                double max = MathAI.getMax(i);
                double min = MathAI.getMin(i);
                Coin1.setText(max + "");
                Coin10.setText(min + "");
                double H = 0;
                if (R == max) {
                    H = (G - B) / (max - min);
                }
                if (G == max) {
                    H = 2 + (B - R) / (max - min);
                }
                if (B == max) {
                    H = 4 + (R - G) / (max - min);
                }

                H = H * 60;
                if (H < 0) {
                    H = H + 360;
                }
                int angle = 360 - (int) H;
                //Toast.makeText(MainActivity.this,color+"\n"+R+"\n"+G+"\n"+B,Toast.LENGTH_LONG).show();
                Coin5.setText(angle + "");
                serialSend("M" + angle + "@");

            }
        });*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mButterKnife.unbind();
    }


    private String onreceive = "";
    private String received = "";

    private Boolean coinmode=false;

    @Override
    public void onSerialReceived(String theString) {                            //Once connection data received, this function will be called
        // TODO Auto-generated method stub
        serialReceivedText.append(theString);                            //append the text into the EditText
        //The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.
        ((ScrollView) serialReceivedText.getParent()).fullScroll(View.FOCUS_DOWN);
        if (theString.contains("$")) {
            coinmode=true;
            if(!theString.substring(0,1).equals("$")){
                theString=theString.split("$")[1];//接受混乱没有容错
            }
            onreceive += theString;
        }else if (coinmode) {
                onreceive += theString;
                if (theString.contains("@")) {
                    received = onreceive.substring(1, onreceive.length() - 3);
                    //received = onreceive.deleteCharAt(buf.length()-1);
                    onreceive = "";
                    coinmode = false;
                   // Toast.makeText(context, received, Toast.LENGTH_LONG).show();
                    System.out.print(received);
                    testgson(received);
                }
        }

    }

    public void testgson(String message) throws IllegalStateException {
        Gson gson = new Gson();
        Message mess = gson.fromJson(message, Message.class);

        //Toast.makeText(this,mess.getC10()+mess.getC5()+mess.getC1()+"",Toast.LENGTH_LONG).show();
        Coin1.setText(mess.getC1() + "");
        Coin5.setText(mess.getC5() + "");
        Coin10.setText(mess.getC10() + "");

    }



    @OnClick(R.id.dev_button_test)
    public void onDevButtonTestClicked() {
        serialSend("T");
    }

    @OnClick(R.id.buttonSerialSend)
    public void onButtonSerialSendClicked() {
        serialSend(serialSendText.getText().toString());                //send the data to the BLUNO
    }
}
