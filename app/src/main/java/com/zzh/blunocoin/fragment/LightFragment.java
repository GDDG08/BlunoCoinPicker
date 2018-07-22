package com.zzh.blunocoin.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.zzh.blunocoin.R;
import com.zzh.blunocoin.data.MathAI;
import com.zzh.blunocoin.data.Varinfo;
import com.zzh.blunocoin.tool.MyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class LightFragment extends MyFragment {

    @BindView(R.id.Light_picker)
    ColorPicker LightPicker;
    @BindView(R.id.light_switch2_light2)
    Switch lightSwitch2Light2;
    @BindView(R.id.light_switch3_bluebit1)
    Switch lightSwitch3Bluebit1;
    @BindView(R.id.light_switch4_bluebit2)
    Switch lightSwitch4Bluebit2;
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
        view = inflater.inflate(R.layout.content_light, container, false);
        context = getActivity();
        mButterKnife = ButterKnife.bind(this, view);
        content();
        onCreateProcess(context);                                                        //onCreate Process by BlunoLibrary
        serialBegin(115200);
        return view;
    }

    void content() {

        LightPicker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                double R = (LightPicker.getColor() & 0x00ff0000) >> 16;
                double G = (LightPicker.getColor() & 0x0000ff00) >> 8;
                double B = (LightPicker.getColor() & 0x000000ff) >> 0;


                Double[] i = new Double[]{R, G, B};
                double max = MathAI.getMax(i);
                double min = MathAI.getMin(i);
                /*C1.setText(max+"");
                C10.setText(min+"");*/
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
//                C5.setText(angle+"");
                serialSend("M" + angle + "@");

                lightoff(3);
                lightoff(4);
                lighton(2);

            }
        });

        LightPicker.setOnColorSelectedListener(new ColorPicker.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                LightPicker.setOldCenterColor(color);
            }
        });


        lightSwitch2Light2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lighton(2);
                    lightoff(3);
                } else {
                    lightoff(2);
                }
            }
        });

        lightSwitch3Bluebit1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lightoff(4);
                    lightoff(2);
                    lighton(3);
                } else {
                    lightoff(3);
                }
            }
        });

        lightSwitch4Bluebit2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lightoff(3);
                    lightoff(2);
                    lighton(4);
                } else {
                    lightoff(4);
                }
            }
        });

        if(Varinfo.Light2){
            lightSwitch2Light2.setChecked(true);
        }else {
            lightSwitch2Light2.setChecked(false);
        }
        if(Varinfo.Light3){
            lightSwitch3Bluebit1.setChecked(true);
        }else {
            lightSwitch3Bluebit1.setChecked(false);
        }
        if(Varinfo.Light4){
            lightSwitch4Bluebit2.setChecked(true);
        }else {
            lightSwitch4Bluebit2.setChecked(false);
        }

    }

    private void lighton(int light) {
        switch (light) {
            case 2:
                if (!Varinfo.Light2) {
                    Varinfo.Light2 = true;
                    serialSend("F");
                    lightSwitch2Light2.setChecked(true);
                }
                break;
            case 3:
                if (!Varinfo.Light3) {
                    Varinfo.Light3 = true;
                    serialSend("B");
                    lightSwitch3Bluebit1.setChecked(true);
                }
                break;
            case 4:
                if (!Varinfo.Light4) {
                    Varinfo.Light4 = true;
                    serialSend("b");
                    lightSwitch4Bluebit2.setChecked(true);
                }
                break;
        }
    }

    private void lightoff(int light) {
        switch (light) {
            case 2:
                if (Varinfo.Light2) {
                    Varinfo.Light2 = false;
                    serialSend("l");
                    lightSwitch2Light2.setChecked(false);
                }
                break;
            case 3:
                if (Varinfo.Light3) {
                    Varinfo.Light3 = false;
                    serialSend("c");
                    lightSwitch3Bluebit1.setChecked(false);
                }
            case 4:
                if (Varinfo.Light4) {
                    Varinfo.Light4 = false;
                    serialSend("c");
                    lightSwitch4Bluebit2.setChecked(false);

                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mButterKnife.unbind();
    }


}
