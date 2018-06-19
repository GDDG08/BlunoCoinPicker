package com.zzh.bamboobook.tool;
import android.content.*;
import android.support.design.widget.*;
import android.view.*;

public class Msg
{
    public static void Snack(View view,CharSequence s)
    {
        Snackbar.make(view,s ,Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
