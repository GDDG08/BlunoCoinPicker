package com.zzh.bamboobook.tool;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.support.v7.app.*;
import com.zzh.bamboobook.*;
import android.content.*;
import com.zzh.bamboobook.data.*;
import android.app.Fragment;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ChangelogDialog// extends DialogFragment
{

    private static int version;

    private static Context context;

    private Fragment frag;

    private boolean darkTheme;

    private int accentColor;

    public ChangelogDialog create(boolean darkThem, int accentColo,Context con, int ver)
    {
        ChangelogDialog dialog = new ChangelogDialog();
        /*Bundle args = new Bundle();
        args.putBoolean("dark_theme", darkTheme);
        args.putInt("accent_color", accentColor);
        dialog.setArguments(args);*/
        version = ver;
        context=con;
        darkTheme=darkThem;
        accentColor=accentColo;
        oCreateDialog();
        return dialog;
    }

    /*@SuppressLint("InflateParams")
    @NonNull
    @Override*/
    public Dialog oCreateDialog(/*Bundle savedInstanceState*/)
    {
        final View customView;
        try
        {
            customView = LayoutInflater.from(context).inflate(R.layout.dialog_webview, null);
        }
        catch (InflateException e)
        {
            throw new IllegalStateException("This device does not support Web Views.");
        }
        AlertDialog dialog = new AlertDialog.Builder(context)
            // .theme(getArguments().getBoolean("dark_theme") ? Theme.DARK : Theme.LIGHT)
            .setTitle("更新日志")
            .setView(customView)
            .setPositiveButton("知道了呢", null)
            .setNeutralButton("不再提示", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog,
                                    int which)
                {
                    Varinfo.preferences_setting_edit.putBoolean("changelog-" + version, false);
                    Varinfo.preferences_setting_edit.commit();
                }

            })
            .setCancelable(false)
            .show();

        final WebView webView = (WebView) customView.findViewById(R.id.webview);
        try
        {
            // Load from changelog.html in the assets folder
            StringBuilder buf = new StringBuilder();
            InputStream json = context.getAssets().open("changelog.html");
            BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;
            while ((str = in.readLine()) != null){
                buf.append(str);}
            in.close();

            // Inject color values for WebView body background and links
            //final int accentColor = accentColor;
            webView.loadData(buf.toString()
                             .replace("{style-placeholder}", darkTheme ?
                                      "body { background-color: #444444; color: #fff; }" :
                                      "body { background-color: #fff; color: #000; }")
                             .replace("{link-color}", colorToHex(shiftColor(accentColor, true)))
                             .replace("{link-color-active}", colorToHex(accentColor))
                             .replace("vers",Varinfo.versionname)
                             ,  "text/html; charset=UTF-8", null);
        }
        catch (Throwable e)
        {
            webView.loadData("<h1>加载失败(｡•́︿•̀｡)</h1><p>" + e.getLocalizedMessage() + "</p>",  "text/html; charset=UTF-8", null);
        }
        return dialog;
    }

    private String colorToHex(int color)
    {
        return Integer.toHexString(color).substring(2);
    }

    private int shiftColor(int color, boolean up)
    {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= (up ? 1.1f : 0.9f); // value component
        return Color.HSVToColor(hsv);
    }
}
