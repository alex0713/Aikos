package com.devel.aikos.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Venus on 1/21/15.
 */
public class UserProfile {

    public String firstName = null;
    public String lastName = null;
    public String email = null;
    public String password = null;
    public String language = null;

    public String longId = null;
    public String shortId = null;

    public UserProfile() {

    }


    public UserProfile(JSONObject jdata) {
        try {
            firstName = jdata.getString("firstName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            lastName = jdata.getString("lastName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            email = jdata.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            password = jdata.getString("password");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            language = jdata.getString("language");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
