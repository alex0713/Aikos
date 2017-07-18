package com.devel.aikos.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.aikos.aikoslibrary.data.AikosCircuit;
import com.aikos.aikoslibrary.data.AikosProgramme;
import com.aikos.aikoslibrary.data.AikosZone;
import com.aikos.aikoslibrary.data.TempObj;
import com.aikos.aikoslibrary.data.TempObjsInADay;
import com.devel.aikos.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class AikosGlobals {

    static final String PREFS_NAME = "Aikos config";

	public static boolean isLoggedin = false;
	
	public static int currentActiveProgramme = 0;
	
	public static int[] graphColors;

	private static String[][] consumecases = null;
	
//	private static ArrayList<AikosZone> zones = new ArrayList<AikosZone>();
	
	private static ArrayList<AikosProgramme> programmes = new ArrayList<AikosProgramme>();
	
	private static ArrayList<AikosCircuit> circuits = new ArrayList<AikosCircuit>();


    public static UserProfile userProfile = new UserProfile();
    public static ArrayList<Contract> contracts = new ArrayList<Contract>();

	
	
	public static void initData(Context context) {

        parse_user_config(context);

        graphColors = new int[] {
                context.getResources().getColor(R.color._Festival),
                context.getResources().getColor(R.color._Summer_Sky),
                context.getResources().getColor(R.color._Purple_Heart),
                context.getResources().getColor(R.color._Deep_Carmin_Pink),
                context.getResources().getColor(R.color._Lima),
                context.getResources().getColor(R.color._Pale_Cerulean)
        };

		consumecases = new String[][] {
				{"heating", "cooling", "lighting", "plugs", "hotwater", "other"},
				{"electricity", "circuits", "gas", "water"}
			};
		
		for (int j = 0; j < 7; j++) {
			int mmx = TempObjsInADay.TOTAL_TEMPOBJS_PER_DAY - 1;
			int s1 = (int)(mmx * Math.random());
			int e1 = (int)(s1 + ((mmx - s1) * Math.random()));
			int s2 = (int)(24 * Math.random());
			int e2 = (int)(s2 + ((mmx - s2) * Math.random()));
	        
			for (int k = s1; k < e1; k++) {
                programmes.get(2).zones.get(0).getZoneTempObjsADay(j).setTemp(new TempObj(1), k);
			}
			for (int k = s2; k < e2; k++) {
                programmes.get(2).zones.get(0).getZoneTempObjsADay(j).setTemp(new TempObj(3), k);
			}

            programmes.get(2).zones.get(0).getZoneTempObjsADay(j).resetDefault();
		}
	}

    static void parse_user_config(Context context) {
        String data =loadData(context, "user_config");
        try {
            JSONObject jdata = new JSONObject(data);

            userProfile = new UserProfile(jdata.getJSONObject("userProfile"));

            JSONObject userNames = jdata.getJSONObject("userNames");
            circuits = AikosCircuit.createFromJSONData(userNames.getJSONArray("circuits"));
            programmes = AikosProgramme.createFromJSONData(userNames.getJSONArray("programs"));
            for (int i = 0; i < programmes.size(); i++) {
                programmes.get(i).zones = AikosZone.createFromJSONData(userNames.getJSONArray("zones"));
            }
//            zones = AikosZone.createFromJSONData(userNames.getJSONArray("zones"));

            contracts = Contract.createFromJSONData(jdata.getJSONArray("contracts"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    static void saveData(Context context, String key, String data) {
//        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString(key, data);
//        editor.commit();
    }

    static String loadData(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        String ret =  settings.getString(key, "");
        if (ret.length() == 0) {
            ret = readAssetData(context, key);
            saveData(context, key, ret);
        }
        return ret;
    }

    static String readAssetData(Context context, String dataname) {
        StringBuilder buf = new StringBuilder();
        InputStream json = null;
        try {
            json = context.getAssets().open("json/" + dataname + ".json");
            BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                buf.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }







	public static String[] getConsumeItems(int whatitem) {
		if (whatitem < 0 || whatitem > 1) {
			return new String[] {""};
		}
		return consumecases[whatitem];
	}
	
	public static ArrayList<AikosZone> getZones(int pIndex) {
		return programmes.get(pIndex).zones;
	}
	
	//Program public functions
	
	public static ArrayList<AikosCircuit> getCircuits() {
		return circuits;
	}
	
	
	//Program public functions
	public static AikosProgramme getProgram(int index) {
		if (index < 0 || index >= programmes.size()) {
			return null;
		}
		return programmes.get(index);
	}
	
	public static ArrayList<AikosProgramme> getProgramsArray() {
		return programmes;
	}
	
	public static String getActiveProgramName() {
		if (currentActiveProgramme < 0 || currentActiveProgramme >= programmes.size()) {
			return "";
		} else {
			return programmes.get(currentActiveProgramme).getName();
		}
	}


	//TempObj public functions
	public static TempObjsInADay[] getZoneTempObjsAWeek(int zIndex, int pIndex) {
		if (zIndex < 0 || zIndex >= programmes.get(pIndex).zones.size()) {
			return null;
		}
		
		return programmes.get(pIndex).zones.get(zIndex).getZoneTempObjsAWeek();
	}
	
	public static TempObjsInADay getZoneTempObjsOfDay(int zIndex, int day, int pIndex) {
		if (day < 0 || day > 6) {
			return null;
		}
		if (zIndex < 0 || zIndex >= programmes.get(pIndex).zones.size()) {
			return null;
		}
		return programmes.get(pIndex).zones.get(zIndex).getZoneTempObjsADay(day);
	}


	public static void setZoneTempObjs(TempObjsInADay tempObjs[], int zIndex, int pIndex) {
		if (zIndex < 0 || zIndex >= programmes.get(pIndex).zones.size()) {
			return;
		}
		int day = 0;
		for (TempObjsInADay tempObjsInADay : tempObjs) {
            programmes.get(pIndex).zones.get(zIndex).setZoneTempObjsADay(tempObjsInADay, day++);
		}		
	}
	
	public static void setZoneTempObjsOfDay(TempObjsInADay tempObjs, int zIndex, int day, int pIndex) {
		if (day < 0 || day > 6) {
			return;
		}
		if (zIndex < 0 || zIndex >= programmes.get(pIndex).zones.size()) {
			return;
		}
        programmes.get(pIndex).zones.get(zIndex).setZoneTempObjsADay(tempObjs, day);
	}


}
