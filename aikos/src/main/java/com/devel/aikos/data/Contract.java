package com.devel.aikos.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Venus on 1/21/15.
 */
public class Contract {

    public String name = "";
    public String lastModifier = null;
    public String lastModified = null;
    public String provider = null;
    public String contract = null;
    public String currentConsoTariff = null;
    public String currentSubscriptionTariff = null;
    public String contractName = null;

    private ArrayList<String> keys = null;
    private ArrayList<String> mdatas = null;

    public static ArrayList<Contract> createFromJSONData(JSONArray jsondata) {

        ArrayList<Contract> ccs = new ArrayList<Contract>();

        for (int i = 0; i < jsondata.length(); i++) {
            try {
                JSONObject dd = jsondata.getJSONObject(i);
                ccs.add(new Contract(dd));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return ccs;
    }
    public Contract(JSONObject jdata) {

        keys = new ArrayList<String>();
        mdatas = new ArrayList<String>();

        try {
            name = jdata.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            provider = jdata.getString("provider");
            keys.add("provider");
            mdatas.add(provider);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contractName = jdata.getString("contractName");
            keys.add("contractName");
            mdatas.add(contractName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contract = jdata.getString("contract");
            keys.add("contract");
            mdatas.add(contract);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            currentConsoTariff = jdata.getString("currentConsoTariff");
            keys.add("currentConsoTariff");
            mdatas.add(currentConsoTariff);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            currentSubscriptionTariff = jdata.getString("currentSubscriptionTariff");
            keys.add("currentSubscriptionTariff");
            mdatas.add(currentSubscriptionTariff);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            lastModifier = jdata.getString("lastModifier");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            lastModified = jdata.getString("lastModified");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getDataValue(int index) {
        if (mdatas == null || index >= mdatas.size())
            return "";

        return mdatas.get(index);
    }

    public String getDataKey(int index) {
        if (keys == null || index >= keys.size())
            return "";

        return keys.get(index);
    }

    public int getDataCount() {
        if (keys == null)
            return 0;
        return keys.size();
    }
}
