package com.aikos.aikoslibrary.data;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AikosZone {
	
	private String name;

    public int getZoneNumber() {
        return zNumber;
    }

    public void setZoneNumber(int zNumber) {
        this.zNumber = zNumber;
    }

    private int zNumber;

    private ArrayList<ArrayList<Integer>> pddata = new ArrayList<ArrayList<Integer>>();

    public static ArrayList<AikosZone> createFromJSONData(JSONArray jsondata) {

        ArrayList<AikosZone> ccs = new ArrayList<AikosZone>();

        for (int i = 0; i < jsondata.length(); i++) {
            try {
                String dd = jsondata.getString(i);
                ccs.add(new AikosZone(dd, i + 1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return ccs;
    }
	
	private TempObjsInADay zoneTempObjsAWeek[] = {
			new TempObjsInADay(),
			new TempObjsInADay(),
			new TempObjsInADay(),
			new TempObjsInADay(),
			new TempObjsInADay(),
			new TempObjsInADay(),
			new TempObjsInADay(),
		};
	
	public static AikosZone createFromJSONString(String jsonstr, int zNum) {
		AikosZone az = new AikosZone("", zNum);
		return az;
	}
	
	public AikosZone(String zname, int zNum) {
		name = zname;
		zNumber = zNum;
		for (int j = 0; j < 7; j++) {
			ArrayList<Integer> a = new ArrayList<Integer>();
			a.add(j);
			pddata.add(a);
		}
	}
	
	//merge functions
	public int getDDCount() {
		return pddata.size();
	}
	
	public ArrayList<Integer> getDDDays(int index) {
		if (index < 0 || index >= pddata.size()) {
			return null;
		}
		return pddata.get(index);
	}
	
	public void mergeTwoDD(final int srcIndex, final int dstIndex) {

		if (srcIndex == dstIndex ||
			srcIndex >= pddata.size() ||
			dstIndex >= pddata.size()) {
			return;
		}
		
		int si = pddata.get(srcIndex).get(0);
		for (int i = 0; i < pddata.get(dstIndex).size(); i++) {
			int di = pddata.get(dstIndex).get(i);
			setZoneTempObjsADay(getZoneTempObjsADay(si), di);
		}	
		pddata.get(dstIndex).addAll(pddata.get(srcIndex));
		pddata.remove(srcIndex);
		
		ArrayList<ArrayList<Integer>> tmp = new ArrayList<ArrayList<Integer>>();
		for (ArrayList<Integer> dd : pddata) {
			if (dd.size() == 0) {
				tmp.add(dd);
			}
		}
		pddata.removeAll(tmp);
		
		sortpddata();
	}
	
	public void splitDD(int dindex) {
		
		if (dindex >= pddata.size()) {
			return;
		}
			
		ArrayList<Integer> dd = pddata.get(dindex);
		
		for (Integer integer : dd) {
			ArrayList<Integer> nd = new ArrayList<Integer>();
			nd.add(integer);
			pddata.add(nd);
		}
		
		pddata.remove(dd);
		
		sortpddata();
	}
	
	private void sortpddata() {
		for (ArrayList<Integer> dd : pddata) {
			Collections.sort(dd, new Comparator<Integer>() {
		        @Override
		        public int compare(Integer s1, Integer s2) {
		            return s1 - s2;
		        }
		    });
		}
		
		Collections.sort(pddata, new Comparator<ArrayList<Integer>>() {
	        @Override
	        public int compare(ArrayList<Integer> s1, ArrayList<Integer> s2) {
	        	if (s1 == null || s2 == null || s1.size() == 0 || s2.size() == 0) {
					return 0;
				}
	            return s1.get(0) - s2.get(0);
	        }
	    });
	}

	//-------------
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TempObjsInADay[] getZoneTempObjsAWeek() {
		return zoneTempObjsAWeek;
	}
	
	public TempObjsInADay getZoneTempObjsADay(int day) {
		if (day < 0 || day > 6) {
			return null;
		}
		return zoneTempObjsAWeek[day];
	}

//	public void setZoneTempObjsAWeek(TempObjsInADay zoneTempObjsAWeek[]) {
//		this.zoneTempObjsAWeek = zoneTempObjsAWeek;
//	}
	
	public void setZoneTempObjsADay(TempObjsInADay TempObjs, int day) {
		if (day < 0 || day > 6) {
			return;
		}
		this.zoneTempObjsAWeek[day].setTemps(TempObjs);
	}

    public ArrayList<ArrayList<Integer>> getMergeInfo() {
        return pddata;
    }

    public void setMergeInfo(ArrayList<ArrayList<Integer>> pddata) {
        this.pddata.clear();
        for (ArrayList<Integer> dd : pddata) {
            ArrayList<Integer> ddn = new ArrayList<Integer>();
            for (Integer ii : dd) {
                ddn.add(ii);
            }
            this.pddata.add(ddn);
        }
    }
}
