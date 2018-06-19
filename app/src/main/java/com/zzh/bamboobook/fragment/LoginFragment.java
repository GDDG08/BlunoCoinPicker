package com.zzh.bamboobook.fragment;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
//import android.support.v4.app.*;
import android.support.v7.app.*;
import android.text.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AdapterView.*;
import co.mobiwise.materialintro.prefs.*;
import co.mobiwise.materialintro.shape.*;
import co.mobiwise.materialintro.view.*;
//import com.afollestad.materialdialogs.*;
import com.umeng.analytics.*;
import com.zzh.bamboobook.*;
import com.zzh.bamboobook.data.*;
import com.zzh.bamboobook.tool.*;
import java.net.*;
import java.util.*;
import org.json.*;
import org.xutils.*;
import org.xutils.common.*;
import org.xutils.http.*;
import org.xutils.http.cookie.*;
import android.view.View.OnClickListener;
import com.zzh.bamboobook.R;
import android.app.Fragment;
import android.a.k.*;


public class LoginFragment extends MyFragment
{
    private String user;
    private String pass;
    private boolean first;

    private int mode;

    private String[] nick;
    private String[] zhanghao;

    private EditText useredit;
    private EditText passedit;

    Boolean useredit_change=false;
    Boolean passedit_change=false;

    private Spinner spinner;

    private View view;

    private Context context;

    private Button loginButton;

    private ProgressBar progressbar;

    @Override
    public void onResume()
    {
        Varinfo.page = 2;
        super.onResume();
    }

	@Override
	public void onHiddenChanged(boolean hidden)
	{
		if(!hidden){
			Varinfo.page = 2;}
		super.onHiddenChanged(hidden);
	}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Varinfo.page = 2;
        view = inflater.inflate(R.layout.content_login, container, false);
        context = getActivity();
        content();
        loginButton = (Button) view.findViewById(R.id.loginButton);
        progressbar = (ProgressBar) view.findViewById(R.id.ProgressBar);
        loginButton.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1)
                {OnLoginClick(null);
                }});
        Button nickb=(Button) view.findViewById(R.id.LoginNickButton);
        nickb.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1)
                {ONnicksettings(null);
                }});
       // MT.finish();
        return view;
    }

    void content()
    {
        spinner = (Spinner) view.findViewById(R.id.user);
        String string = Varinfo.preferences_user.getString("user", "没有已登陆账号");
        String string_nick= Varinfo.preferences_user.getString("nick", "没有已登陆账号");
        text_active(null);
        if (string_nick == "没有已登陆账号" && string != "没有已登陆账号")
        {
            Varinfo.preferences_user_edit.putString("nick", string);
            Varinfo.preferences_user_edit.commit();
            String active = Varinfo.preferences_login.getString("active", "未登录");
            Varinfo.preferences_login_edit.putString("active_nick", string);
            Varinfo.preferences_login_edit.commit();
            text_active(active);
            string_nick = string;
        }

        zhanghao = string.split(";");
        nick = string_nick.split(";");

        spinner();

        useredit = (EditText) view.findViewById(R.id.username);
        passedit = (EditText) view.findViewById(R.id.password);

        final Button loginbutton=(Button)view.findViewById(R.id.loginButton);
        loginbutton.setEnabled(false);

        useredit.addTextChangedListener(new TextWatcher(){

                @Override
                public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
                {}

                @Override
                public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
                {
                    if (p1.length() == 0)
                    {
                        useredit_change = false;
                    }
                    else
                    {
                        useredit_change = true;
                    }

                    if (useredit_change && passedit_change)
                    {
                        loginbutton.setEnabled(true);
                    }
                    else
                    {
                        loginbutton.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable p1)
                {}
            });

        passedit.addTextChangedListener(new TextWatcher(){

                @Override
                public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
                {}

                @Override
                public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
                {
                    if (p1.length() == 0)
                    {
                        passedit_change = false;
                    }
                    else
                    {
                        passedit_change = true;
                    }

                    if (useredit_change && passedit_change)
                    {
                        loginbutton.setEnabled(true);
                    }
                    else
                    {
                        loginbutton.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable p1)
                {}
            });

        if (!new PreferencesManager(context).isDisplayed("login_settings"))
        {
            Button set=(Button) view.findViewById(R.id.LoginNickButton);
            new MaterialIntroView.Builder(getActivity())
                .enableDotAnimation(true)
                .enableIcon(true)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                //.setDelayMillis(100)
                .enableFadeAnimation(true)
                .performClick(true)
                .setInfoText("点击设置按钮，即可修改用户昵称或删除无用账号")
                .setShape(ShapeType.CIRCLE)
                .setTarget(set)
                //.setListener(listener)
                .setUsageId("login_settings") //THIS SHOULD BE UNIQUE ID
                .show();
        }

        }
    private void text_active(String text)
    {
        TextView nowuser = (TextView) view.findViewById(R.id.nowuser);
        String Text="未登录";
        if (text != null)
        {
            Text = text;
        }
        else
        {
            Text = Varinfo.preferences_login.getString("active_nick", "未登录");
        }
        nowuser.setText(Text);
    }

    private void spinner()
    {
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, nick);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        first = true;
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
                {
                    if (first)
                    {first = false;}
                    else
                    {
                        user2(pos, zhanghao, nick);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {}
            });
    }

    private void user2(int pos, String[] zhanghao, String[] nick)
    {
        Varinfo.preferences_login_edit.putString("cookie-value", Varinfo.preferences_user.getString(zhanghao[pos] + "-value", ""));
        Varinfo.preferences_login_edit.putString("active_nick", nick[pos]);
        Varinfo.preferences_login_edit.putString("active", zhanghao[pos]);
        Varinfo.preferences_login_edit.commit();
        Varinfo.mainactivity.cookie();
        Msg.Snack(view , "已切换到账号:" + nick[pos]);

		Varinfo.active_user=nick[pos];
        /*Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);*/
        //finish();
        content();
        loginButton.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.INVISIBLE);
    }

    public void OnLoginClick(View v) 
    {
        loginButton.setVisibility(View.INVISIBLE);
        progressbar.setVisibility(View.VISIBLE);


        user = useredit.getText().toString();
        pass = passedit.getText().toString();

        final String[] aa=Varinfo.preferences_user.getString("user", "没有已登陆账号").split(";");
        final String[] nick=Varinfo.preferences_user.getString("nick", "没有已登陆账号").split(";");
        for (int i=0;i < aa.length;i++)
        {
            if (aa[i].equals(user))
            {
                user2(i, aa, nick);
                mode = 1;
            }
        }

        if (mode != 1)
        {
            RequestParams params = new RequestParams("https://hfs-be.yunxiao.com/v2/users/sessions"); 
            params.addBodyParameter("loginName", user);  
            params.addBodyParameter("password", pass);  
            params.addBodyParameter("roleType", "1");  
            params.addBodyParameter("rememberMe", "1");  
            params.addBodyParameter("deviceType", "1");

            x.http().post(params, new Callback.CommonCallback<String>() {

                    private String msg="test";
                    private String code;
                    private String old_nick;

                    private JSONObject res_json;

                    @Override
                    public void onSuccess(String result)
                    {/*
                        try
                        {
                            res_json = new JSONObject(result);
                            code = res_json.getString("code");
                        }
                        catch (JSONException e)
                        {}

                        switch (code)
                        {
                            case "0":
                                msg = "登陆成功！";
                                MobclickAgent.onProfileSignIn(user);

                                DbCookieStore instance = DbCookieStore.INSTANCE;
                                List cookies = instance.getCookies();
                                String value = null;
                                for (HttpCookie cookie:cookies)
                                {
                                    value = cookie.getValue();
                                }
                                Varinfo.preferences_login_edit.putString("cookie-value", value);
                                Varinfo.preferences_login_edit.commit();

                                Varinfo.mainactivity.cookie();

                                Varinfo.preferences_user_edit.putString(user + "-value", value);
                                String old=Varinfo.preferences_user.getString("user", null);
                                old_nick = Varinfo.preferences_user.getString("nick", null);
                                Varinfo.preferences_user_edit.putString("user", add(old, 0));

                                x.http().get(HTTP.httpparam("https://hfs-be.yunxiao.com/v2/user-center/user-snapshot", true), new Callback.CommonCallback<String>() {

                                        @Override
                                        public void onCancelled(Callback.CancelledException p1)
                                        {
                                            // TODO: Implement this method
                                        }

                                        @Override
                                        public void onFinished()
                                        {
                                            // TODO: Implement this method
                                        }

                                        @Override
                                        public void onError(Throwable p1, boolean p2)
                                        {
                                            // TODO: Implement this method
                                        }


                                        @Override
                                        public void onSuccess(String result2)
                                        {
                                            try
                                            {
                                                JSONObject a=new JSONObject(result2);
                                                write_name = a.getJSONObject("data").getJSONObject("linkedStudent").getString("studentName");
                                            }
                                            catch (JSONException e)
                                            {}

                                            Varinfo.preferences_login_edit.putString("active_nick", write_name);
                                            Varinfo.preferences_login_edit.putString("active", user);
                                            Varinfo.preferences_login_edit.commit();

                                            Varinfo.preferences_user_edit.putString("nick", add(old_nick, 1));
                                            Varinfo.preferences_user_edit.commit();

                                            // finish();
                                            content();
                                            loginButton.setVisibility(View.VISIBLE);
                                            progressbar.setVisibility(View.INVISIBLE);
											//登陆后来一波
											o0oo.Application(context);
                                        }
                                    });
                                break;

                            case "4046":
                                msg = "密码错误";
                                break;

                            case "4045":
                                msg = "用户名不存在";
                                break;

                            default:
                                msg = "登陆失败\n";
                                try
                                {
                                    msg += res_json.getString("msg");
                                }
                                catch (JSONException e)
                                {}
                                break;
                        }
                        Msg.Snack(view, msg);*/
                    }

                    private String write_name;
                    private String add(String old, int type)
                    {
                        String active=";";
                        if (type == 1)
                        {
                            try
                            {
                                JSONObject a=new JSONObject(write_name);
                                if (a.getInt("code") != 0)
                                {
                                    write_name = user;
                                }
                            }
                            catch (JSONException e)
                            {}
                        }
                        else
                        {
                            write_name = user;
                        }
                        if (old == null)
                        {
                            active = "点击选择;" + write_name + active;
                        }
                        else
                        {
                            active = old + write_name + active;
                        }
                        return active;
                    }


                    @Override
                    public void onError(Throwable ex, boolean isOnCallback)
                    {
                        Msg.Snack(view, ex.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException cex)
                    {
                        Msg.Snack(view, "cancelled");
                    }

                    @Override
                    public void onFinished()
                    {}
                });

            loginButton.setVisibility(View.VISIBLE);
            progressbar.setVisibility(View.INVISIBLE);
        }//if mode的下括号
    }

    String[] nick_list;
    int choice;
    
    
    public void ONnicksettings(View v)
    {
        choice = 0;
        if (nick.length > 1)
        {
            nick_list = new String[nick.length - 1];
            for (int i=0;i < nick_list.length;i++)
            {
                nick_list[i] = nick[i + 1];
            }

            new AlertDialog.Builder(context)
                .setTitle("请选择需要修改的用户")
                .setSingleChoiceItems(nick_list, 0, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface p1, int p2)
                    {
                        choice = p2;
                    }
                })
                .setPositiveButton("选择", new DialogInterface.OnClickListener(){
                    //private String name;
                    @Override
                    public void onClick(DialogInterface p1, int p2)
                    {
                        
                       AlertDialog dialog= new AlertDialog.Builder(context)
                            .setTitle("请输入新名称")
                            //.content("内容")
                            /*.inputType(InputType.TYPE_CLASS_TEXT |
                                       InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                                       InputType.TYPE_TEXT_FLAG_CAP_WORDS)*/
                            //.inputRange(12, 12)
                            .setView(MyDialog.InputDialog(context))
                            .setPositiveButton("修改", new DialogInterface.OnClickListener(){

								@Override
								public void onClick(DialogInterface p1, int p2)
								{
							        //name=input.toString();
                                   // Msg.Snack(view,"name");
                                    String text = MyDialog.et.getText().toString();
                                    nick[choice + 1] = text;
                                    String putout = "";
                                    for (int i=0;i < nick.length;i++)
                                    {
                                        putout += nick[i] + ";";
                                    }
                                    Varinfo.preferences_user_edit.putString("nick", putout);
                                    Varinfo.preferences_user_edit.commit();
                                    if (zhanghao[choice + 1].equals(Varinfo.preferences_login.getString("active", null)))
                                    {
                                        Varinfo.preferences_login_edit.putString("active_nick", text);
                                        Varinfo.preferences_login_edit.commit();
                                        text_active(null);
                                    }
                                    Msg.Snack(view, "账号" + nick_list[choice] + "已自定义为" + text);

                                    spinner();
                                }})
                            .setNegativeButton("取消",null)
                            .setCancelable(false)
							.create();
                          

                        dialog.show();
                        // MyDialog.dialog=dialog;
                        Button button_edit = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        button_edit.setEnabled(false);
                        MyDialog.button_edit = button_edit;
							
                                }})
                .setNegativeButton("取消", null)
                .setNeutralButton("删除账号", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface p1, int p2)
                    {
                        new AlertDialog.Builder(context)
                            .setTitle("确认删除")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface p1, int p2)
                                {
                                    if (nick.length - 1 != 0)
                                    {
                                        String[] nick_new=new String[nick.length - 1];
                                        String[] zhanghao_new=new String[zhanghao.length - 1];
                                        for (int i=0;i < nick.length;i++)
                                        {
                                            if (i < choice + 1)
                                            {
                                                nick_new[i] = nick[i];
                                                zhanghao_new[i] = zhanghao[i];
                                            }
                                            if (i > choice + 1)
                                            {
                                                nick_new[i - 1] = nick[i];
                                                zhanghao_new[i - 1] = zhanghao[i];
                                            }
                                        }

                                        if (zhanghao[choice + 1].equals(Varinfo.preferences_login.getString("active", null)))
                                        {
                                            clearlogin();
                                        }
                                        nick = nick_new;
                                        zhanghao = zhanghao_new;
                                        String putout_nick = "";
                                        String putout_zhanghao = "";
                                        for (int i=0;i < zhanghao_new.length;i++)
                                        {
                                            putout_nick += nick_new[i] + ";";
                                            putout_zhanghao += zhanghao_new[i] + ";";
                                        }
                                        Varinfo.preferences_user_edit.putString("user", putout_zhanghao);
                                        Varinfo.preferences_user_edit.putString("nick", putout_nick);
                                        Varinfo.preferences_user_edit.commit();

                                    }
                                    else
                                    {
                                        clearlogin();
                                        Varinfo.preferences_user_edit.clear().commit();
                                        /* Msg.Snack(LoginActivity.context,"账号已清空！",1).show();
                                         finish();*/
                                    }
                                    spinner();
                                }

                                private void clearlogin()
                                {
                                    Varinfo.preferences_login_edit.remove("cookie-value");
                                    Varinfo.preferences_login_edit.remove("active");
                                    Varinfo.preferences_login_edit.remove("active_nick");
                                    Varinfo.preferences_login_edit.commit();
                                    text_active(null);
                                }
                            })
                            .setMessage("确认删除账号：“" + nick[choice + 1] + "”?")
                            .setNegativeButton(android.R.string.cancel, null)
                            .show();/*确认窗口*/
                    }}
            )
                .setCancelable(false)
                .show();
        }
        else
        {
            Msg.Snack(view, "暂无账号");
        }

    }
}
