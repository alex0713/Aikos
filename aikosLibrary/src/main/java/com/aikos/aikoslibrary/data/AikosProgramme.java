package com.aikos.aikoslibrary.data;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class AikosProgramme {

	private String _name;
	private boolean _isAuto = false;

    private boolean _isContinue = false;

    public ArrayList<AikosZone> zones = new ArrayList<AikosZone>();

    public static ArrayList<AikosProgramme> createFromJSONData(JSONArray jsondata) {

        ArrayList<AikosProgramme> ccs = new ArrayList<AikosProgramme>();

        for (int i = 0; i < jsondata.length(); i++) {
            try {
                String dd = jsondata.getString(i);
                ccs.add(new AikosProgramme(dd, i == 0, i == 1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return ccs;
    }
	
	public static AikosProgramme createFromJSONString(String jsonstr) {
		AikosProgramme az = new AikosProgramme("", false, false);
		
		return az;
	}
	
	public AikosProgramme(String pname, boolean isAuto, boolean isCont) {
		_name = pname;
		this._isAuto = isAuto;
        this._isContinue = isCont;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}

	public boolean isAuto() {
		return _isAuto;
	}

	public void setAuto(boolean isAuto) {
		this._isAuto = isAuto;
	}

    public boolean isContinue() {
        return _isContinue;
    }

    public void setContinue(boolean _isContinue) {
        this._isContinue = _isContinue;
    }
}
