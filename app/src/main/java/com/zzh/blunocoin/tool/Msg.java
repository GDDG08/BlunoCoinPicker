package com.zzh.blunocoin.tool;
import android.content.*;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.*;

import com.zzh.blunocoin.R;
import com.zzh.blunocoin.ZZHApplication;

public class Msg
{
    public static void Snack(View view,CharSequence s)
    {
        Snackbar.make(view,s ,Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

}
