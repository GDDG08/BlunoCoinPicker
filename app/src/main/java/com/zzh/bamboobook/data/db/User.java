package com.zzh.bamboobook.data.db;

/**
 * Created on 2018/5/26-13-23.
 */
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 数据原型
 * 这里要添加数据的注解，就可以对应数据库中的表和字段，
 * 注解也是xUtils中的工具类完成的
 */
@Table(name = "Chinese")//注释表名
public class User {

public User(){
	
}
    @Override
    public String toString() {
        return "User{" +
			"\"ALLID\":\"" + ALLID + "\"" +
			", \"SID\":" + SID +
			", \"AddTime\":" + AddTime +
			", \"TITLE\":\"" + TITLE + "\"" +
			", \"Picnum\":\"" + Picnum + "\"" +
			", \"Keypoint\":\"" + Keypoint + "\"" +
			", \"text\":\"" + text + "\"" +
			", \"star\":" + star +
			", \"ReviewTime\":" + ReviewTime +
			", \"ReviewPre\":" + ReviewPre +

			"}";
     }

    @Column(name = "ALLID")//注释列名
    private String ALLID;

    @Column(name = "SID", isId = true, autoGen = true)//注释列名主键，主动增长
    private long SID;

    @Column(name = "AddTime")//注释列名
    private long AddTime;

    @Column(name = "TITLE")//注释列名
    private String TITLE;

    @Column(name = "Picnum")//注释列名
    private String Picnum;
    
    @Column(name = "Keypoint")//注释列名
    private String Keypoint;
    
    @Column(name = "text")//注释列名
    private String text;
    
    @Column(name = "star")//注释列名
    private long star;
    
    @Column(name = "ReviewTime")//注释列名
    private long  ReviewTime;
    
    @Column(name = "ReviewPre")//注释列名
    private long ReviewPre;
    
    //@Column(name = "")//注释列名
    //private String ;


    //必须有空参的构造方法和set与get
	public void setALLID(String ALLID) {
		this.ALLID = ALLID;
	}
	public String getALLID() {
		return ALLID;
	}

    public void setSID(int SID) {
		this.SID = SID;
	}
	public long getSID() {
		return SID;
	}

    public void setAddTime(long AddTime) {
		this.AddTime = AddTime;
	}
	public long getAddTime() {
		return AddTime;
	}

    public void setTITLE(String TITLE) {
		this.TITLE = TITLE;
	}
	public String getTITLE() {
		return TITLE;
	}

    public void setPicnum(String Picnum) {
		this.Picnum = Picnum;
	}
	public String getPicnum() {
		return Picnum;
	}

    public void setKeypoint(String Keypoint) {
		this.Keypoint = Keypoint;
	}
	public String getKeypoint() {
		return Keypoint;
	}

    public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}

    public void setStar(long star) {
		this.star = star;
	}
	public long getStar() {
		return star;
	}

    public void setReviewTime(long ReviewTime) {
		this.ReviewTime = ReviewTime;
	}
	public long getReviewTime() {
		return ReviewTime;
	}

    public void setReviewPre(long ReviewPre) {
		this.ReviewPre = ReviewPre;
	}
	public long getReviewPre() {
		return ReviewPre;
	}

    
}
