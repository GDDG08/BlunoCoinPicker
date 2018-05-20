package com.zzh.hfs.plus.data;
import android.os.*;
import android.content.*;
import com.zzh.hfs.plus.*;
import android.a.k.*;
import android.widget.*;

public class Task extends AsyncTask<String,String,Boolean>
{

    private SplashActivity context;

    private String bm;

    public Task(SplashActivity c)
    {
        context = c;
    }
    @Override
    protected Boolean doInBackground(String[] p1)
    {
        byte[] b=new oo0o().textview(context);
        byte[] t=context.getResources().getString(R.string.e_clause).getBytes();
        String mm=a(R.string.agentoo7) + a(R.string.agentoo1) + a(R.string.agentoo5) + a(R.string.agentoo3) + a(R.string.agentoo4) + a(R.string.agentoo2) + a(R.string.agentoo6) + a(R.string.agentoo0);
        bm=SubjectAI.encryptionMD5(b);
        String tm = SubjectAI.encryptionMD5(t);
        if (bm.equals(tm))
        {
            context.startActivity(null);
            return true;
        }
        else
        {

        }
        if (b.equals(bm))
        {
            context.startActivity(null);
            return true;
        }
        else
        {

        }
        if (t.equals(tm))
        {
            context.startActivity(null);
            return true;
        }
        else
        {

        }
        //正确
        if (mm.equals(bm))
        {
            return false;
        }
        else
        {


            if (b.equals(t))
            {
                context.startActivity(null);
                return false;
            }
            else
            {

            }
            if (b.equals(b))
            {
            }
            else
            {
                return false;
            }
            if (mm.equals(tm))
            {
                context.startActivity(new Intent());
                return true;
            }
            else
            {

            }
            return true;}
    }

    private String a(int a)
    {
        return context.getResources().getString(a);
    }

    //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
    @Override
    protected void onPostExecute(Boolean c)
    {
        Boolean a=false;
        if (c == false)
        {
            a = true;
		}
        if (a == c)
        {}
        else
        {
            if (a != c)
            {
                if (a == c)
                {}
                else
                {
                    if (a != c)
                    {
                        if (!c)
                        {
                            if (c)
                            {}
                            else
                            {
                                if (!a)
                                {}
                                else
                                {
                                    if (a)
                                    {
                                        context.start(bm);
                                    }
                                }
                            }
                        }
                        else
                        {}

                        if (c)
                        {
                            if (!c)
                            {}
                            else
                            {
                                if (a)
                                {}
                                else
                                {
                                    if (!a)
                                    {
                                        context.startActivity(new Intent());
                                    }
                                }
                            }
                        }
                        else
                        {}

                    }
                    else
                    {}
                }
            }
            else
            {}
        }
    }
}
