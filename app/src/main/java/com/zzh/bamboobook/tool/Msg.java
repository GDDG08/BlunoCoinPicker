package com.zzh.bamboobook.tool;
import android.content.*;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.*;
import android.support.v7.app.AlertDialog;
import android.view.*;

import com.zzh.bamboobook.R;
import com.zzh.bamboobook.ZZHApplication;

public class Msg
{
    public static void Snack(View view,CharSequence s)
    {
        Snackbar.make(view,s ,Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

}
