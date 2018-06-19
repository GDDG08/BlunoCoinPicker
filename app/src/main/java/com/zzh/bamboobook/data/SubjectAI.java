package com.zzh.bamboobook.data;
import com.zzh.bamboobook.*;
import java.security.*;
import java.math.*;

public class SubjectAI
{


    public static int advantage(Double[] ratio)
    {
        int n=getMax(ratio);
        if (n == 999)
        {
            return 999;
        }
        else
        {
            if ((ratio[n] - getAverage(ratio)) >= 5)
            {
                return n;
            }
            else
            {
                return 999;}
        }
    }
    public static int inferiority(Double[] ratio)
    {
        int n=getMin(ratio);
        if (n == 999)
        {
            return 999;
        }
        else
        {
            if ((getAverage(ratio) - ratio[n]) >= 5)
            {
                return n;
            }
            else
            {
                return 999;}
        }
    }

    public static int getMax(Double[] arr)
    {
        Double max = Double.MIN_VALUE;
        int n=999;
        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] > max)
            {
                max = arr[i];
                n = i;}
        }

        return n;
    }

    public static int getMin(Double[] arr)
    {
        Double min = Double.MAX_VALUE;
        int n=999;
        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] < min)
            {
                min = arr[i];
                n = i;}
        }

        return n;
    }
    public static Double getAverage(Double[] arr)
    {
        /*   Double all=0d;
         Double a=arr[getMin(arr)];
         for (int i = 0; i < arr.length; i++)
         {
         if (arr[i]!=a){
         all += arr[i];}
         }
         return all / (arr.length-1);*/
        return Varinfo.exam_ratio;
    }

    private static int m=3;
    public static int getProLevel(Double e)
    {
        int re = 0;
        if (-m > e)
        {
            re = -1;
        }
        else if (e >= -m && m >= e)
        {
            re = 0;
        }
        else if (e > m && 2 * m >= e)
        {
            re = 1;
        }
        else if (e > 2 * m && 3 * m >= e)
        {
            re = 2;
        }
        else if (e > 3 * m)
        {
            re = 3;
        }
        return re;
    }
    public static String encryptionMD5(byte[] byteStr)
    {
        MessageDigest messageDigest = null;
        StringBuffer md5StrBuff = new StringBuffer();
        try
        {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(byteStr);
            byte[] byteArray = messageDigest.digest();
            for (int i = 0; i < byteArray.length; i++)
            {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                }
                else
                {
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return md5StrBuff.toString();
    }
    
    public static Double GetRatio(int rank, int num)
    {
        BigDecimal a = new BigDecimal(rank - 1);
        BigDecimal b = new BigDecimal(num - 1);
        Double r=a.divide(b, 2, RoundingMode.HALF_UP).doubleValue();
        //System.out.println(r);
        r = 1 - r;

        r=r*100;
        Double aa=Double.parseDouble(String.format("%.2f",r));
        return /*Double.parseDouble(String.format("%.2f", */aa;   
	}
	
	public static String getGradeDrift(Double[] ratio, int pos){
		String text="";
		switch (pos){
			case 0:
				text+= "第一次考";
				break;
			case 1:
				text+= "第二次考";
				break;
			default:
				text+= "这是你第" + (pos+1) +"次考本科，";
				if (ratio[pos]<ratio[pos-1]&ratio[pos-1]<ratio[pos-2]){
					text+="你的成绩连续下滑！";
					}
				else if(ratio[pos]>ratio[pos-1]&ratio[pos-1]>ratio[pos-2]){
					text+="你的成绩保持了进步！";
					}
					else{ 
					text+="你近期成绩比较波动！";
					}
				
				break;
		}
		return text;
	}
	public static int CulRank(double rati, int num){
		double ratio=rati * 0.01;
		double rank=ratio + num - ratio*num;
		return (int)rank;
	}
}
