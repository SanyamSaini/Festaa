package com.example.a.party_booker;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by A on 06-03-2017.
 */

public class AppPref {

    private static final String USER_PREFS = "USER_PREFS";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;


    boolean loggedIn=false;
    String fName="";
    String lName="";
    String emailId="";
    String mobileNo="";
    String userid="";
    boolean selected=false;
    String cityid="";
    String areaid="";




    public AppPref(Context context) {

        this.appSharedPrefs = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();

        loggedIn=this.appSharedPrefs.getBoolean("loggedIn",false);
        fName=this.appSharedPrefs.getString("fName","");
        lName=this.appSharedPrefs.getString("lName","");
        mobileNo=this.appSharedPrefs.getString("mobileNo","");
        userid=this.appSharedPrefs.getString("userid","");
        emailId=this.appSharedPrefs.getString("emailId","");
        selected=this.appSharedPrefs.getBoolean("selected",false);
        cityid=this.appSharedPrefs.getString("cityid","");
        areaid=this.appSharedPrefs.getString("areaid","");


    }

    public boolean isLoggedIn() {
        return this.appSharedPrefs.getBoolean("loggedIn",false);
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
        prefsEditor.putBoolean("loggedIn",loggedIn).commit();
    }

    public String getfName() {
        return fName=this.appSharedPrefs.getString("fName","");
    }

    public void setfName(String fName) {
        this.fName = fName;
        prefsEditor.putString("fName",fName).commit();
    }

    public String getlName() {
        return lName=this.appSharedPrefs.getString("lName","");
    }

    public void setlName(String lName) {
        this.lName = lName;
        prefsEditor.putString("lName",lName).commit();
    }

    public String getEmailId() {
        return emailId=this.appSharedPrefs.getString("emailId","");
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
        prefsEditor.putString("emailId",emailId).commit();
    }

    public String getMobileNo() {
        return mobileNo=this.appSharedPrefs.getString("mobileNo","");
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        prefsEditor.putString("mobileNo",mobileNo).commit();
    }

    public String getUserid() {
        return userid=this.appSharedPrefs.getString("userid","");
    }

    public void setUserid(String userid) {
        this.userid = userid;
        prefsEditor.putString("userid",userid).commit();
    }

    public boolean isSelected()  {
        return this.appSharedPrefs.getBoolean("selected",false);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        prefsEditor.putBoolean("selected",selected).commit();
    }

    public String getAreaid() {
        return areaid=this.appSharedPrefs.getString("areaid","");
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
        prefsEditor.putString("areaid",areaid).commit();
    }

    public String getCityid() {
        return cityid=this.appSharedPrefs.getString("cityid","");
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
        prefsEditor.putString("cityid",cityid).commit();
    }
}
