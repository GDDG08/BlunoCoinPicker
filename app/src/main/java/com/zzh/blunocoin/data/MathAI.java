package com.zzh.blunocoin.data;

/**
 * Created on 2018/7/11-18-03.
 */
public class MathAI {
    public static double getMax(Double[] arr)
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

        return max;
    }

    public static double getMin(Double[] arr)
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

        return min;
    }
}
