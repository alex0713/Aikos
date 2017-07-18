package com.aikos.aikoslibrary.data;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class AikosCircuit {
	
	private String name;

    public static ArrayList<AikosCircuit> createFromJSONData(JSONArray jsondata) {

        ArrayList<AikosCircuit> ccs = new ArrayList<AikosCircuit>();

        for (int i = 0; i < jsondata.length(); i++) {
            try {
                String dd = jsondata.getString(i);
                ccs.add(new AikosCircuit(dd));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return ccs;
    }
	
	public static AikosCircuit createFromJSONString(String jsonstr) {
		AikosCircuit az = new AikosCircuit("");
		
		return az;
	}

	public AikosCircuit(String cname) {
		name = cname;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
