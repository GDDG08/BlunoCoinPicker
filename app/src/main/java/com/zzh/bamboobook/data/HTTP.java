package com.zzh.bamboobook.data;

import android.content.*;
import android.view.*;
import android.widget.*;
import com.zzh.bamboobook.fragment.*;
import com.zzh.bamboobook.tool.*;
import java.io.*;
import java.util.*;
import org.json.*;
import org.xutils.*;
import org.xutils.common.*;
import org.xutils.common.util.*;
import org.xutils.http.*;
import android.os.*;

public class HTTP
{
    public static String  re;

	public static String mode;
	static int mode_n;

	public static View view;

	private static String url;

//	private static HTTPCallBack cb;

    public static void get(String url, boolean usecookie, final int param, final String offline_name,final HTTPCallBack callback)
    {
        RequestParams params2 = new RequestParams(url); // 网址(请替换成实际的网址)

        if (usecookie == true)
        {
            params2.addHeader("Cookie", "hfs-session-id=" + Varinfo.cookie_value + ";Max-Age=31536000;Domain=.yunxiao.com;Path=/;Secure=");
            params2.setUseCookie(false);
        }
		params2.addHeader("User-Agent", Varinfo.agent);
		params2.addHeader("Content-Type", "application/json; charset=utf-8");
		params2.addHeader("Referer", url);

        x.http().get(params2, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result)
                {
                    re = result;
                    //callback.onHTTPEnd(result);
					Return(result, param, offline_name, callback);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback)
                {
                    //Toast.makeText(x.app(), "网络错误！\n"+ex.getMessage(), Toast.LENGTH_LONG).show();
					/*正常*/offline("网络连接失败！", param, offline_name, callback);
					/*调试*/offline(offline_name+"\n"+ex.getMessage(), param, offline_name, callback);
				}

                @Override
                public void onCancelled(CancelledException cex)
                {
                    Toast.makeText(x.app(), "请求已被取消", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFinished()
                {

                }
            });
    }

/*
    public static void post(String url, final HTTPCallBack callback)
    {

        RequestParams params2 = new RequestParams(url);
		//params2.addHeader("
        params2.addHeader("Cookie", "hfs-session-id=" + Varinfo.cookie_value + ";Max-Age=31536000;Domain=.yunxiao.com;Path=/;Secure=");
        params2.addHeader("User-Agent", Varinfo.agent);
        params2.setUseCookie(false);


        x.http().post(params2, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result)
                {
                    re = result;
                    //callback.onHTTPEnd(result);
					Return(result, -1, ,""callback);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback)
                {
                    Toast.makeText(x.app(), "网络错误！\n" + ex.getMessage(), Toast.LENGTH_LONG).show();

                }

                @Override
                public void onCancelled(CancelledException cex)
                {
                    Toast.makeText(x.app(), "请求已被取消", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFinished()
                {

                }
            });
    }*/
    public static RequestParams httpparam(String url, boolean cookie)
	{
        RequestParams params2 = new RequestParams(url); // 网址(请替换成实际的网址)

        if (cookie == true)
        {
            params2.addHeader("Cookie", "hfs-session-id=" + Varinfo.cookie_value);
            params2.addHeader("User-Agent", Varinfo.agent);
            params2.setUseCookie(false);
        }
        return params2;
    }

	/*public static void downloadImagesByUrl(final Context context, String url, final List<String> filesPath) {
	 /*
	 获取保存路径：手机SD卡1存储 storage/sdcard/Android/data/应用的包名/files
	 Genymotion模拟器的路径：/storage/emulated/0/Android/data/com.atguigu.zhuatutu/files
	 */
	/*     File filesDir = context.getExternalFilesDir(null);
	 //获取文件名:/february_2016-001.jpg
	 String fileName = url.substring(url.lastIndexOf("/"));
	 //存到本地的绝对路径
	 final String filePath = filesDir + "/piccache" + fileName;
	 File file = new File(filesDir + "/piccache");
	 //如果不存在
	 if (!file.exists()) {
	 //创建
	 file.mkdirs();
	 }

	 RequestParams entity = new RequestParams(url);
	 entity.setSaveFilePath(filePath);
	 x.http().get(entity, new Callback.CommonCallback<File>() {
	 @Override
	 public void onSuccess(File result) {
	 filesPath.add(result.getAbsolutePath());
	 LogUtil.e("onSuccess：" + result.getAbsolutePath());
	 }

	 @Override
	 public void onError(Throwable ex, boolean isOnCallback) {
	 LogUtil.e("onError ");
	 Toast.makeText(x.app(),"网络错误，下载失败",Toast.LENGTH_SHORT).show();
	 }

	 @Override
	 public void onCancelled(CancelledException cex) {
	 LogUtil.e("onCancelled ");
	 }

	 @Override
	 public void onFinished() {
	 LogUtil.e("onFinished ");
	 Toast.makeText(x.app(), "下载成功,保存到：" + filesPath.get(filesPath.size() - 1), Toast.LENGTH_SHORT).show();
	 }
	 });

	 }*/
	public static void request(int mod, View vie, HTTPCallBack callback)
	{
		request(mod, vie, -1, callback);
	}
	//static int param=-1;
	final public static int trends=10;
	final public static int question_detail=11;
	final public static int examoverview=12;
	final public static int answer_picture=13;
	final public static int paper_detail=14;

    public static void request(int mod, View vie, int param, HTTPCallBack callback)
	{
		mode_n = mod;

		switch (mod)
		{
			case trends:
				mode = "trends";
				break;
			case question_detail:
				mode = "paper_detail";
				break;
			case examoverview:
				mode = "v2examoverview";
				break;
			case answer_picture:
				mode = "v2answer-picture";
				break;
			case paper_detail:
				mode = "v2paper_detail";
				break;

		}
		view = vie;

		url = "https://hfs-be.yunxiao.com/";
		//cb=callback;

		//int sleep=100;
		switch (mod)
		{
				/*case "v1score":
				 url+="v1/score";
				 //sleep=300;
				 break;*/
			case trends:
				url += "v2/exam/trends-overview";
				break;
				/*case "substat":
				 url+="v1/score/stat/" + AnaSFragment.paperId + "?paperId=" + AnaSFragment.paperId;
				 break;*/
			case question_detail:
				url += "v2/exam/" + Varinfo.examId + "/" + Varinfo.paperId + "/question-detail";
				//sleep=400;
				break;
			case examoverview:
				url += "v2/exam/" + Varinfo.examId + "/overview";
				break;
			case answer_picture:
				url += "v2/exam/" + Varinfo.examId + "/papers/" + Varinfo.paperId + "/answer-picture";
				break;
			case paper_detail:
				url += "v2/exam/" + Varinfo.examId + "/" + Varinfo.paperId + "/detail";
				break;
		}
		/*new Handler().postDelayed(new Runnable(){

		 @Override
		 public void run()
		 {*/
		get(url, true, param, offline_name(),callback);
		//}}, sleep);

	}
	private static void Return(String result, int param, String offline_name,HTTPCallBack callback)
	{
		int code=-1;
		String msg="错误";
		try
		{
			JSONObject jSONObject = new JSONObject(result);
			code = jSONObject.getInt("code");
			msg = jSONObject.getString("msg");
		}
		catch (JSONException e)
		{}
		if (code == 0 || msg.equals("获取学生考试信息成功"))
		{
			callback.onHTTPEnd(result, param);
			Varinfo.pre_offline.edit().putString(offline_name, result).commit();
		}
		else
		{
			offline(msg, param, offline_name,callback);
		}
	}

	private static void offline(String msg, int param, String offline_name,HTTPCallBack callback)
	{
		String offlinejson=Varinfo.pre_offline.getString(offline_name, null);
		if (offlinejson == null)
		{
			String a="";
			a+="数据出错，无法使用( •̥́ ˍ •̀ू )\n";
			Msg.Snack(view,  a+ msg);
		}
		else
		{
			Msg.Snack(view, "网络错误，开启离线模式！" +/*\n考试信息并非最新！！！\nn考试信息并非最新！！！\nn考试信息并非最新！！！*/"\n错误日志：" + msg);
			callback.onHTTPEnd(offlinejson, param);
		}
	}

	static String offline_name()
	{
		String out=Varinfo.active_user + "-" + mode;
		switch (mode_n)
		{
			case question_detail:
			case paper_detail:
			case answer_picture:
				out+="-"+Varinfo.examId+"-"+Varinfo.paperId;
				break;
			case examoverview:
				out+="-"+Varinfo.examId;
				break;
		}
		return out;
	}

}
 
