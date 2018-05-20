package com.zzh.hfs.plus.charts;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.AreaChart;
import org.xclcharts.chart.AreaData;
import org.xclcharts.chart.CustomLineData;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.event.click.PointPosition;
import org.xclcharts.renderer.XEnum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;
import android.widget.*;

/**
 * @ClassName AreaChart02View
 * @Description  平滑面积图例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */

public class AreaView extends DemoView {

   // private String TAG = "AreaChart02View";

    private AreaChart chart = new AreaChart();  
    //标签集合
    private LinkedList<String> mLabels = new LinkedList<String>();
    //数据集合
    private LinkedList<AreaData> mDataset = new LinkedList<AreaData>();

    //private List<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();

    private String[] eventTime;
    private Double[] ratio;

    private int color;


    public AreaView(Context context,String[] eventTim,Double[] rati,int colo) {
        super(context);
        eventTime=eventTim;
        ratio=rati;
        color=colo;
        initView();
    }    

    public AreaView(Context context){   
        super(context);   
        initView();
    }
    
    public AreaView(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
    }

    public AreaView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView()
    {
        chartLabels();
        chartDataSet();     
        chartRender();

        //綁定手势滑动事件
        //this.bindTouch(this,chart);
    }

    @Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
        //图所占范围大小
        chart.setChartRange(w ,h);
    }  

    private void chartRender()
    {
        try{                                                 
            //设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....     
            int [] ltrb = getBarLnDefaultSpadding();
            chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);

            //轴数据源                      
            //标签轴
            chart.setCategories(mLabels);
            //数据轴
            chart.setDataSource(mDataset);
            //仅横向平移
            chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);

            //数据轴最大值
            chart.getDataAxis().setAxisMax(110);
            //数据轴刻度间隔
            chart.getDataAxis().setAxisSteps(10);

            //网格
            chart.getPlotGrid().showHorizontalLines();
            chart.getPlotGrid().showVerticalLines();    
            //把顶轴和右轴隐藏
            //chart.hideTopAxis();
            //chart.hideRightAxis();
            //把轴线和刻度线给隐藏起来
            chart.getDataAxis().hideAxisLine();
            chart.getDataAxis().hideTickMarks();        
            chart.getCategoryAxis().hideAxisLine();
            chart.getCategoryAxis().hideTickMarks();                

            //标题
           //chart.setTitle("平滑区域图");
            //chart.addSubtitle("(XCL-Charts Demo)"); 
            //轴标题
            chart.getAxisTitle().setLowerTitle("考试日期");

            //透明度
            chart.setAreaAlpha(180);
            //显示图例
            chart.getPlotLegend().show();

            //激活点击监听
          //  chart.ActiveListenItemClick();
            //为了让触发更灵敏，可以扩大5px的点击监听范围
          //  chart.extPointClickRange(5);

            //定义数据轴标签显示格式
            chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){

                    @Override
                    public String textFormatter(String value) {
                        // TODO Auto-generated method stub      
                        Double tmp = Double.parseDouble(value);
                        DecimalFormat df=new DecimalFormat("#0");
                        String label = df.format(tmp).toString();               
                        return (label);
                    }

                });

            //设定交叉点标签显示格式
            chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
                    @Override
                    public String doubleFormatter(Double value) {
                        // TODO Auto-generated method stub
                        DecimalFormat df=new DecimalFormat("#0");                    
                        String label = df.format(value).toString();
                        return label;
                    }});

            //扩大显示宽度
            //chart.getPlotArea().extWidth(300f);
            

            //chart.disablePanMode(); //test

            /*CustomLineData line1 = new CustomLineData("平均",60d,Color.RED,7);
            line1.setCustomLineCap(XEnum.DotStyle.CROSS);       
            line1.setLabelHorizontalPostion(Align.CENTER);
            line1.setLabelOffset(15);   
            line1.getLineLabelPaint().setColor(Color.RED);
            mCustomLineDataset.add(line1);
            chart.setCustomLines(mCustomLineDataset);*/

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
           // Log.e(TAG, e.toString());
        }
    }

    private void chartDataSet()
    {
        //将标签与对应的数据集分别绑定
        //标签对应的数据集
        List<Double> dataSeries = new LinkedList<Double>();
        for (int i=0; i < ratio.length; i++){
        dataSeries.add(ratio[i]);
        }
        AreaData line2 = new AreaData("历次考试",dataSeries,
                                      color,    
                                      color
                                      );
        //设置线上每点对应标签的颜色
        line2.getDotLabelPaint().setColor(color);
        //设置点标签
        line2.setLabelVisible(true);
        line2.getDotLabelPaint().setTextAlign(Align.CENTER);    
        line2.getLabelOptions().setLabelBoxStyle(XEnum.LabelBoxStyle.CIRCLE);
        line2.getLabelOptions().getBox().getBackgroundPaint().setColor(Color.parseColor("#FBD627"));
        line2.getLabelOptions().getBox().setRoundRadius(10);
        line2.getLabelOptions().setOffsetY(30.f);
        line2.setApplayGradient(true);
        line2.setGradientDirection(XEnum.Direction.HORIZONTAL);
        line2.setAreaBeginColor(Color.WHITE);
        line2.setAreaEndColor(color);

        //line2.setApplayGradient(true);
        //line2.setGradientMode(Shader.TileMode.MIRROR);        

        //Color.RED,Color.WHITE  Color.WHITE,Color.RED


        mDataset.add(line2);    


    }

    private void chartLabels()
    {
        for (int i=0; i < eventTime.length; i++){
        mLabels.add(eventTime[i]);
        }
    }

    @Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
        } catch (Exception e){
           // Log.e(TAG, e.toString());
        }
       /* try{            
            //设置图表大小
            chart.setChartRange(0,0,
                                this.getLayoutParams().width -10,
                                this.getLayoutParams().height - 10);
            //设置绘图区内边距  (px),left,top,bottom保持与左边图要一致 
           // chart.setPadding( 0,0, 100,180 ); 

            chart.render(canvas);

        } catch (Exception e){
          //  Log.e(TAG, e.toString());
        }*/
    }
/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub      
        super.onTouchEvent(event);      
        if(event.getAction() == MotionEvent.ACTION_UP) 
        {           
            triggerClick(event.getX(),event.getY());
        }
        return true;
    }


    //触发监听
    private void triggerClick(float x,float y)
    {       
        PointPosition record = chart.getPositionRecord(x,y);            
        if( null == record) return;

        AreaData lData = mDataset.get(record.getDataID());
        Double lValue = lData.getLinePoint().get(record.getDataChildID());  

        Toast.makeText(this.getContext(), 
                       record.getPointInfo() +
                       " Key:"+lData.getLineKey() +
                       " Label:"+lData.getLabel() +                                
                       " Current Value:"+Double.toString(lValue), 
                       Toast.LENGTH_SHORT).show();         
    }       */
}

