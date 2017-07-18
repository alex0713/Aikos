package com.aikos.aikoslibrary.data;

import org.json.JSONException;
import org.json.JSONObject;

public class TempObjsInADay {
	
	private int defaultTempCode = 2;
	
	public static final int TOTAL_TEMPOBJS_PER_DAY = 24 * 6;
	
	private TempObj[] temperatures = new TempObj[TOTAL_TEMPOBJS_PER_DAY];
	
	public TempObjsInADay() {
		for (int k = 0; k < TOTAL_TEMPOBJS_PER_DAY; k++) {
			temperatures[k] = new TempObj(defaultTempCode);
		}
	}
	
	public TempObjsInADay(int defaultcode) {
		defaultTempCode = defaultcode;
		for (int k = 0; k < TOTAL_TEMPOBJS_PER_DAY; k++) {
			temperatures[k] = new TempObj(defaultTempCode);
		}
	}
	
	public TempObjsInADay(String jsonString) {
		// TODO JSON parse
		try {
			JSONObject jdata = new JSONObject(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public TempObj getDefaultTemp() {
		return new TempObj(defaultTempCode);
	}
	
	public void setDefault(int defaultCode) {
		defaultTempCode = defaultCode;
	}
	
	public void resetDefault() {
		//TODO ...
		int[] cnts = new int[TempObj.TempObjNames.length];
		for (int i = 0; i < cnts.length; i++) {
			cnts[i] = 0;
		}
		for (TempObj t : temperatures) {
			if (t.getTempCode() < cnts.length) {
				cnts[t.getTempCode()]++;
			}
		}
		int dnum = 0;
		int max = cnts[0];
		for (int i = 1; i < cnts.length; i++) {
			if (cnts[i] > max) {
				dnum = i;
				max = cnts[i];
			}
		}
		defaultTempCode = dnum;
	}
	
	public int getDefaultCode() {
		return defaultTempCode;
	}
	
	public String getDefaultCodeString() {
		if (defaultTempCode >= 0 && defaultTempCode < TempObj.TempObjNames.length) {
			return TempObj.TempObjNames[defaultTempCode];
		}
		return "";
	}
	
	public boolean isDefault(TempObj temp) {
		return temp.getTempCode() == defaultTempCode;
	}
	
	public boolean isDefault(int index) {
		return temperatures[index].getTempCode() == defaultTempCode;
	}

	public void setTemps(TempObj[] temps) {
		for (int i = 0; i < temps.length; i++) {
			if (i < this.temperatures.length) {
				this.temperatures[i] = new TempObj(temps[i]);
			}
		}
	}
	
	public void setTemps(TempObjsInADay tIAD) {
		setTemps(tIAD.getAllTemps());
		defaultTempCode = tIAD.getDefaultCode();
	}
	
	public void setTemp(TempObj temp, int index) {
		this.temperatures[index] = new TempObj(temp);
	}
	
	public void setTemp(TempObj temp, int hour24, int min60) {
		setTemp(temp, (hour24 % 24) * 6 + ((min60 % 60) / 10));
	}
	
	public void setTemps(TempObj temp, int from, int to) {
        if (from == -1) from = 0;
        if (to == -1) to = temperatures.length - 1;
		for (int i = from; i <= to; i++) {
            if (i >= temperatures.length) {
                break;
            }
			this.temperatures[i] = new TempObj(temp);
		}
	}
	
	public void setTemps(TempObj temp, int fromHour24, int fromMin60, int toHour24, int toMin60) {
		int from = (fromHour24 % 24) * 6 + ((fromMin60 % 60) / 10);
		int to = (toHour24 % 24) * 6 + ((toMin60 % 60) / 10) - 1 ;
		setTemps(temp, from, to);
	}
	
	public TempObj[] getAllTemps() {
		return temperatures;
	}
	
	public TempObj getTemp(int index) {
		if (temperatures.length <= index) {
			return null;
		}
		return temperatures[index];
	}
	
	public TempObj getTemp(int hour24, int min60) {
		int idx = (hour24 % 24) * 6 + ((min60 % 60) / 10);
		return getTemp(idx);
	}
	
	public void removeTempAt(int index) {
		this.temperatures[index] = new TempObj(defaultTempCode);
	}
	
	public void removeTempAt(int hour24, int min60) {
		int idx = (hour24 % 24) * 6 + ((min60 % 60) / 10);
		removeTempAt(idx);
	}
	
	public void removeAreaTempsAt(int index) {
		if (index < 0 || index >= TOTAL_TEMPOBJS_PER_DAY) {
			return;
		}
		
		TempObj tempObj = new TempObj(temperatures[index]);
		
		for (int i = index; i < TOTAL_TEMPOBJS_PER_DAY; i++) {
			if (tempObj.isEqual(temperatures[i])) {
				removeTempAt(i);
			} else {
				break;
			}
		}
		
		for (int i = index - 1; i >= 0; i--) {
			if (tempObj.isEqual(temperatures[i])) {
				removeTempAt(i);
			} else {
				break;
			}
		}
	}
	
	public void removeAreaTempsAt(int hour24, int min60) {
		int idx = (hour24 % 24) * 6 + ((min60 % 60) / 10);
		removeAreaTempsAt(idx);
	}
	
	
	public TimeTempObj getTimeTempObj(int index) {
		if (index < 0 || index >= TOTAL_TEMPOBJS_PER_DAY) {
			return null;
		}
		
		TimeTempObj tto = new TimeTempObj();
		
		tto.tempObj = new TempObj(temperatures[index]);
		tto.from = index;
		tto.to = index;
		
		for (int i = index + 1; i < TOTAL_TEMPOBJS_PER_DAY; i++) {
			if (tto.tempObj.isEqual(temperatures[i])) {
				tto.to = i;
			} else {
				break;
			}
		}
		
//		tto.to++;
		
		for (int i = index - 1; i >= 0; i--) {
			if (tto.tempObj.isEqual(temperatures[i])) {
				tto.from = i;
			} else {
				break;
			}
		}
		
		return tto;
	}
	
	public TimeTempObj getTimeTempObj(int hour24, int min60) {
		int idx = (hour24 % 24) * 6 + ((min60 % 60) / 10);
		return getTimeTempObj(idx);
	}
	
	public class TimeTempObj {
		public int from = 0;
		public int to = TOTAL_TEMPOBJS_PER_DAY - 1;
		public TempObj tempObj = new TempObj();
		
		public int getStartHour() {
			return from / 6;
		}
		public int getStartMin() {
			return from % 6 * 10;
		}
		public int getEndHour() {
			return (to + 1) / 6;
		}
		public int getEndMin() {
			return (to + 1) % 6 * 10;
		}

        public String getStartTimeString() {
            return String.format("%02d:%02d", getStartHour(), getStartMin());
        }
        public String getEndTimeString() {
            return String.format("%02d:%02d", getEndHour(), getEndMin());
        }
	}
	
}
