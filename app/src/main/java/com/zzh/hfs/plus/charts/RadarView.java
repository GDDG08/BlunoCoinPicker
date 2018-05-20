package com.zzh.hfs.plus.charts;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import org.xclcharts.chart.RadarChart;
import org.xclcharts.chart.RadarData;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.event.click.PointPosition;
import org.xclcharts.renderer.XEnum;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import com.zzh.hfs.plus.*;
import com.zzh.hfs.plus.data.*;
import com.zzh.hfs.plus.fragment.*;

public class RadarView extends DemoView
{
	private RadarChart chart = new RadarChart();
	//标签集合
	private List<String> labels = new LinkedList<String>();
	private List<RadarData> chartData = new LinkedList<RadarData>();
	private Paint mPaintTooltips = new Paint(Paint.ANTI_ALIAS_FLAG);

	public RadarView(Context context)
    {
		super(context);
        initView();
	}

	public RadarView(Context context, AttributeSet attrs)
    {   
        super(context, attrs);   
        initView();
    }

    public RadarView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView()
    {
        chartLabels();
        chartDataSet();	
        chartRender();
        //綁定手势滑动事件
        this.bindTouch(this, chart);
    }

	@Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {  
        super.onSizeChanged(w, h, oldw, oldh);  
        //图所占范围大小
        chart.setChartRange(w, h);
    }  	

	private void chartRender()
	{
		try
        {				
			//设置绘图区默认缩进px值
			int [] ltrb = getPieDefaultSpadding();
			chart.setPadding(ltrb[0] * 2, ltrb[1] / 2.2f, ltrb[2] * 2, ltrb[3] * 1.2f);

			//chart.showBorder();
			//chart.setTitle("蜘蛛雷达图");
			//chart.addSubtitle("(XCL-Charts Demo)");


			//设定数据源
			chart.setCategories(labels);								
			chart.setDataSource(chartData);

			//点击事件处理
			chart.ActiveListenItemClick();
			chart.extPointClickRange(50);
			chart.showClikedFocus();

			//数据轴最大值
			chart.getDataAxis().setAxisMax(100);
			//数据轴刻度间隔
			chart.getDataAxis().setAxisSteps(10);
			//主轴标签偏移50，以便留出空间用于显示点和标签
			chart.getDataAxis().setTickLabelMargin(30);

			chart.getDataAxis().hideAxisLabels();
			//定义数据轴标签显示格式
			chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){

                    @Override
                    public String textFormatter(String value)
                    {
                        Double tmp = Double.parseDouble(value);
                        DecimalFormat df=new DecimalFormat("#0");
                        String label = df.format(tmp).toString();				
						return (label);
                    }

                });

			//定义数据点标签显示格式

			chart.setDotLabelFormatter(new IFormatterDoubleCallBack() {
                    @Override
                    public String doubleFormatter(Double value)
                    {
                        DecimalFormat df= new DecimalFormat("#0");					 
                        String label = "[" + df.format(value).toString() + "]";
                        return label;
                    }});

		}
        catch (Exception e)
        {}

	}

	private void chartDataSet()
	{
        LinkedList<Double> dataSeriesL= new LinkedList<Double>();	
		LinkedList<Double> dataSeriesS= new LinkedList<Double>();	

		for (int i=0; i < AnalysisFragment.papers_ratio.length; i++)
        {
            dataSeriesL.add(Varinfo.exam_ratio);
            dataSeriesS.add(AnalysisFragment.papers_ratio[i]);
        }

        RadarData lineDataL = new RadarData("总成绩击败率：" + Varinfo.exam_ratio + "%", dataSeriesL, AnalysisFragment.color_radar_bak, XEnum.DataAreaStyle.FILL);
        lineDataL.setLineStyle(XEnum.LineStyle.DASH);   
		lineDataL.getPlotLine().setDotStyle(XEnum.DotStyle.HIDE);

		RadarData lineDataS = new RadarData("单科击败率", dataSeriesS, AnalysisFragment.color_radar, XEnum.DataAreaStyle.FILL);
		lineDataS.setLineStyle(XEnum.LineStyle.DASH);	
		lineDataS.getPlotLine().setDotStyle(XEnum.DotStyle.RING);

        chartData.add(lineDataL);
		chartData.add(lineDataS);
	}

	private void chartLabels()
	{
		for (int i=0; i < AnalysisFragment.papers_name.length; i++)
        {
			labels.add(AnalysisFragment.papers_name[i]);
		}
	}

	@Override
    public void render(Canvas canvas)
    {
        try
        {
            chart.render(canvas);
        }
        catch (Exception e)
        {}
    }

	@Override
	public boolean onTouchEvent(MotionEvent event)
    {

		if (event.getAction() == MotionEvent.ACTION_UP) 
		{			
			triggerClick(event.getX(), event.getY());			
		}
		return true;
	}

	//触发监听
	private void triggerClick(float x, float y)
	{
		PointPosition record = chart.getPositionRecord(x, y);			
		if (null == record) return;

		if (record.getDataID() < chartData.size())
		{
			int aaa=record.getDataChildID();
			RadarData lData = chartData.get(record.getDataID());
			Double lValue = lData.getLinePoint().get(aaa);

			float r = record.getRadius();
			chart.showFocusPointF(record.getPosition(), r + r * 0.5f);		
			chart.getFocusPaint().setStyle(Style.STROKE);
			chart.getFocusPaint().setStrokeWidth(1);		
			chart.getFocusPaint().setColor(Color.rgb(254, 214, 39));	
			chart.getFocusPaint().setAlpha(210);

			//在点击处显示tooltip
			mPaintTooltips.setColor(Color.BLACK);			
			mPaintTooltips.setTextSize(30);
			chart.getToolTip().setCurrentXY(x, y);		
			chart.getToolTip().addToolTip(Double.toString(lValue) + "%", mPaintTooltips);

			this.invalidate();

		}
	}

}
