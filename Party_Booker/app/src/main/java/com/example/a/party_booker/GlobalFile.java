package com.example.a.party_booker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.Request;


/**
 * Created by prince on 9/5/2016.
 */
public class GlobalFile {


    public static final int POST= Request.Method.POST;

    //https://chintakpatel.in/urvi/webservice.php

    public static String server_link ="http://urvi.chintakpatel.in/webservice.php";
    public static String image_link ="http://urvi.chintakpatel.in/";



    public static String share ="https://play.google.com/store/apps/details?id=com.example.a.party_booker&hl=en";




    public static boolean isOnline(Context con) {
        ConnectivityManager connectivity = (ConnectivityManager) con
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static void noInternet(Context context){
        Toast.makeText(context, "No Internet Connection Found\nPlease Check your Connection First!", Toast.LENGTH_SHORT).show();
    }

    public static void serverError(Context context){
        Toast.makeText(context, "Server Error Occured\nPlease Try After Sometime!", Toast.LENGTH_SHORT).show();
    }

}
