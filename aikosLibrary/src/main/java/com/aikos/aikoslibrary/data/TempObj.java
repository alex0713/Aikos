package com.aikos.aikoslibrary.data;

import android.graphics.Color;


public class TempObj {
	
	public static String[] TempObjNames = {"21-23", "20-22", "19-21", "18-20", "hors gel", "Off"};
	public static int[] TempObjColors = {Color.parseColor("#EC3B3F"), Color.parseColor("#EDA745"), Color.parseColor("#EED54A")
		, Color.parseColor("#1587CB"), Color.parseColor("#441296") ,Color.parseColor("#5A5D5A")};
	
	private int tempCode = 2;
	
	public TempObj() {
		
	}
	
	public TempObj(TempObj oldTO) {
		tempCode = oldTO.getTempCode();
	}
	
	public TempObj(int tcode) {
		tempCode = tcode;
	}
	
	public String getTempString() {
		if (tempCode >= 0 && tempCode < TempObjNames.length) {
			return TempObjNames[tempCode];
		}
		return "On";
	}
	
	public boolean isEqual(TempObj other) {
		return tempCode == other.getTempCode();
	}
	
	public int getTempCode() {
		return tempCode;
	}
	public void setTempCode(int tcode) {
		tempCode = tcode;
	}

	public int getGraphColor() {
		if (tempCode >= 0 && tempCode < TempObjColors.length) {
			return TempObjColors[tempCode];
		}
		return Color.parseColor("#EDA745");
	}
	
}
