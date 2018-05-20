package android.a.k;
import android.content.pm.*;
import android.content.*;

public class oo0o
{
    public byte[] textview(Context c)
    {
        try
        {
            PackageInfo packageInfo = new o0oo().method(c);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            byte[] signStr = sign.toByteArray();
            return signStr;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return null;
        }
    }

}
