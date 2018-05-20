package com.zzh.hfs.plus.charts;


import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.BarChart3D;
import org.xclcharts.chart.BarData;
import org.xclcharts.chart.StackBarChart;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.renderer.XEnum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import org.json.*;
import com.zzh.hfs.plus.*;
import android.widget.*;
import com.zzh.hfs.plus.data.*;
import com.zzh.hfs.plus.fragment.*;

public class BarChart2 extends DemoView
{


    private BarChart mChart = null;
    //标签轴
    private List<String> chartLabels = new LinkedList<String>();
    private List<BarData> chartData = new LinkedList<BarData>();

    private Double[] papers_ratio2=AnalysisFragment.papers_ratio2;

    private Double[] papers_ratio=AnalysisFragment.papers_ratio;

    private String[] papers_name=AnalysisFragment.papers_name;

    //不是最早的考试
    Boolean lastexam=AnalysisFragment.lastexam;

    public BarChart2(Context context)
    {
        super(context);
        chartLabels();
        chartDataSet();
        chartRender();
    }

    private void initChart()
    {
        //竖向柱形图
       // mChart = new BarChart();
        //mChart.getAxisTitle().setLeftTitle("百分比");                      
    }

    @Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {  
        super.onSizeChanged(w, h, oldw, oldh);  
        //图所占范围大小
        //mChart.setChartRange(w,h);
    }       

    public void chartRender()
    {
        try
        {

            initChart();

            //设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....     
            int [] ltrb = getBarLnDefaultSpadding();
            mChart.setPadding(DensityUtil.dip2px(getContext(), 30), ltrb[1], ltrb[2], ltrb[3]);  


            //数据源
            mChart.setDataSource(chartData);
            mChart.setCategories(chartLabels);  

            //数据轴
            mChart.getDataAxis().setAxisMax(104);
            mChart.getDataAxis().setAxisMin(0);
            mChart.getDataAxis().setAxisSteps(5);

            //指隔多少个轴刻度(即细刻度)后为主刻度
            mChart.getDataAxis().setDetailModeSteps(4);

            //背景网格
            mChart.getPlotGrid().showHorizontalLines();

            //定义数据轴标签显示格式
            mChart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){

                    @Override
                    public String textFormatter(String value)
                    {    
                        Double tmp = Double.parseDouble(value);
                        DecimalFormat df = new DecimalFormat("#0");
                        String label = df.format(tmp).toString();               
                        return label;//+"%";
                    }

                });     
            //定义柱形上标签显示格式
            mChart.getBar().setItemLabelVisible(true);
            mChart.getBar().getItemLabelPaint().setColor(Color.rgb(72, 61, 139)); 
            mChart.getBar().getItemLabelPaint().setFakeBoldText(true);
            mChart.getBar().getItemLabelPaint().setTextSize(20);

            mChart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
                    @Override
                    public String doubleFormatter(Double value)
                    {                                   
                        DecimalFormat df=new DecimalFormat("#0");
                        String label = df.format(value).toString();             
                        return label;//+"%";
                    }});           
            mChart.DeactiveListenItemClick();
            // mChart.getCategoryAxis().getAxisPaint().setTextSize(35);
        }
        catch (Exception e)
        {//  Log.e(TAG, e.toString());
        }
    }
    private void chartDataSet()
    {
        
        if (lastexam)
        {
            List<Double> dataSeriesB= new LinkedList<Double>(); 
            dataSeriesB.add(Varinfo.exam_ratio2);
            for (int i=0; i < papers_ratio.length; i++)
            {
                dataSeriesB.add(papers_ratio2[i]);
            }
            BarData BarDataB = new BarData("上一次考试", dataSeriesB, AnalysisFragment.color_bar_pre);
            chartData.add(BarDataB);
        }

        List<Double> dataSeriesC= new LinkedList<Double>(); 
        dataSeriesC.add(Varinfo.exam_ratio);
        for (int i=0; i < papers_ratio.length; i++)
        {
            dataSeriesC.add(papers_ratio[i]);
        }
        BarData BarDataC = new BarData("本次考试", dataSeriesC, AnalysisFragment.color_bar_now);



        chartData.add(BarDataC);
    }

    private void chartLabels()
    {
        chartLabels.add("总成绩");
        for (int i=0; i < papers_name.length; i++)
        {
            chartLabels.add(papers_name[i]);
        }
    }   

    @Override
    public void render(Canvas canvas)
    {
        try
        {
            mChart.setChartRange(this.getMeasuredWidth(), this.getMeasuredHeight());
            mChart.render(canvas);
        }
        catch (Exception e)
        {
            //Log.e(TAG, e.toString());
        }
    }


}
