package com.zzh.hfs.plus.data;

import org.json.*;

public class PaperDetail
{
	public JSONObject jso;
	private Double score;
	private String shortName;
	private Double realscore;
	private int isWrong;
//总
	public Double getScore()
	{
		return score;
	}

	public String getShortName()
	{
		return shortName;
	}
//实际
	public Double getRealScore()
	{
		return realscore;
	}

	public int getisWrong()
	{
		return isWrong;
	}
	
	public void setjson(JSONObject jso1)
	{
		this.jso = jso1;
		try
		{
			shortName = jso.getString("shortName");
			score = jso.getDouble("score");
			realscore = jso.getDouble("realScore");
			isWrong = jso.getInt("isWrong");
		}
		catch (JSONException e)
		{}
	}

}
