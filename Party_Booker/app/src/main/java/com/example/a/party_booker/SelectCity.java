package com.example.a.party_booker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.a.party_booker.GlobalFile.noInternet;
import static com.example.a.party_booker.GlobalFile.serverError;

public class SelectCity extends AppCompatActivity {
    ArrayList<String> countryid = new ArrayList<String>();
    ArrayList<String> countryname = new ArrayList<String>();
    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();
    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();
    ArrayList<String> areaid = new ArrayList<String>();
    ArrayList<String> areaname = new ArrayList<String>();

    String position_country = new String();
    String position_state = new String();
    String position_city = new String();
    String position_area = new String();

    String position_countryname = new String();
    String position_statename = new String();
    String position_cityname = new String();
    String position_areaname = new String();

    String position_country_selected = "";
    ArrayList<String> country_id = new ArrayList<>();

    String position_state_selected = "";
    ArrayList<String> state_id = new ArrayList<>();

    String position_city_selected = "";
    ArrayList<String> city_id = new ArrayList<>();

    String position_area_selected = "";
    ArrayList<String> area_id = new ArrayList<>();

    Spinner spncity,spnarea;
    Button submit,logout;
    AppPref appPref;
    String cityID="",areaID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        appPref=new AppPref(getApplicationContext());

       spncity=(Spinner)findViewById(R.id.city);
        spnarea=(Spinner)findViewById(R.id.area);

        spncity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_city = cityid.get(position);
                position_cityname = cityname.get(position);
                if (position_city.equalsIgnoreCase("0")) {
                    //Globals.CustomToast(Add_Newuser_Address.this,"Please Select State",getLayoutInflater());
                } else if (spncity.getSelectedItemPosition() == 0) {
                    //Do nothing
                } else {

                    //position_city_selected = city_id.get(position - 1);
                    //cityID= String.valueOf(spncity.getSelectedItem());
                    Log.e("city id",""+position_city);
                    getArea();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        spnarea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_area = areaid.get(position);
                position_areaname = areaname.get(position);
                if (position_area.equalsIgnoreCase("0")) {

                } else if (spnarea.getSelectedItemPosition() == 0) {
                    //Do nothing
                } else {
                    //position_area_selected = area_id.get(position - 1);
                    Log.e("area id",""+position_area);

                    // new send_readymade_area_Data().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        submit=(Button)findViewById(R.id.submit);
        //logout=(Button)findViewById(R.id.logout);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ii=new Intent(getApplicationContext(),MainActivity.class);
                ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                ii.putExtra("cityid",position_city);
//                ii.putExtra("areaid",position_area);
                appPref=new AppPref(getApplicationContext());
                appPref.setSelected(true);
                appPref.setCityid(position_city);
                appPref.setAreaid(position_area);
                startActivity(ii);

            }
        });

        if (GlobalFile.isOnline(getApplicationContext())) {

            // new get_Otp().execute();
            getCity();
            hideSoftKeyboard();
        } else
        {
            Toast.makeText(SelectCity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void getCity() {
        appPref=new AppPref(getApplicationContext());
        final Custom_ProgressDialog loadingView = new Custom_ProgressDialog(SelectCity.this, "");
        loadingView.setCancelable(false);
        loadingView.show();
        //layout_login.setVisibility(View.GONE);
        StringRequest request = new StringRequest(GlobalFile.POST, GlobalFile.server_link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("JSON", response);

                        try {
                            JSONObject jObj = new JSONObject(response);

                            boolean date = jObj.getBoolean("status");
                            if (date==false) {
                                String Message = jObj.getString("message");
                                Toast.makeText(SelectCity.this, "" + Message, Toast.LENGTH_LONG).show();
                                // GlobalFile.CustomToast(Activity_Login.this,""+Message, getLayoutInflater());
                                loadingView.dismiss();
                                //layout_login.setVisibility(View.VISIBLE);
                                //avi.hide();
                            } else {
                                //JSONObject jobj = new JSONObject(result_1);
                                cityname.clear();
                                cityid.clear();

                               // if (response != null) {
                                    JSONArray categories = jObj.getJSONArray("data");
                                    cityname.add("Select City");
                                    cityid.add("0");
                                    for (int i = 0; i < categories.length(); i++) {
                                        JSONObject catObj = (JSONObject) categories.get(i);
                                        String cId = catObj.getString("cityid");
                                        String cname = catObj.getString("cityname");

                                        city_id.add(cId);

                                        cityname.add(cname);
                                        cityid.add(cId);


                                    }
                                    ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(SelectCity.this, R.layout.custom_text, cityname);
                                    adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spncity.setAdapter(adapter_city);
                               // }
                                loadingView.dismiss();
                            }
                        } catch (JSONException j) {
                            j.printStackTrace();
                            loadingView.dismiss();
                            Log.e("Exception",""+j.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Log.e("ERROR",error.getMessage());
                        if (error instanceof NetworkError) {
                            noInternet(getApplicationContext());
                        } else
                        {
                            serverError(getApplicationContext());
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("allCities", "");


                Log.e("params", params.toString());
                return params;
            }
        };
        PartyBooker.getInstance().addToRequestQueue(request);
    }

    public void getArea() {
        appPref=new AppPref(getApplicationContext());
        final Custom_ProgressDialog loadingView = new Custom_ProgressDialog(SelectCity.this, "");
        loadingView.setCancelable(false);
        loadingView.show();
        //layout_login.setVisibility(View.GONE);
        StringRequest request = new StringRequest(GlobalFile.POST, GlobalFile.server_link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("JSON", response);

                        try {
                            JSONObject jObj = new JSONObject(response);

                            boolean date = jObj.getBoolean("status");
                            if (date==false) {
                                String Message = jObj.getString("message");
                                Toast.makeText(SelectCity.this, "" + Message, Toast.LENGTH_LONG).show();
                                // GlobalFile.CustomToast(Activity_Login.this,""+Message, getLayoutInflater());
                                loadingView.dismiss();
                                //layout_login.setVisibility(View.VISIBLE);
                                //avi.hide();
                            } else {
                                //JSONObject jobj = new JSONObject(result_1);
                                areaname.clear();
                                areaid.clear();
                                if (response != null) {
                                    JSONArray categories = jObj.getJSONArray("data");
                                    areaname.add("Select Area");
                                    areaid.add("0");
                                    for (int i = 0; i < categories.length(); i++) {
                                        JSONObject catObj = (JSONObject) categories.get(i);
                                        String aId = catObj.getString("areaid");
                                        String aname = catObj.getString("areaname");

                                        areaid.add(aId);

                                        areaname.add(aname);
                                        //cityid.add(cId);


                                    }
                                    ArrayAdapter<String> adapter_area = new ArrayAdapter<String>(SelectCity.this, R.layout.custom_text, areaname);
                                    adapter_area.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spnarea.setAdapter(adapter_area);
                                }
                                loadingView.dismiss();
                            }
                        } catch (JSONException j) {
                            j.printStackTrace();
                            loadingView.dismiss();
                            Log.e("Exception",""+j.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Log.e("ERROR",error.getMessage());
                        if (error instanceof NetworkError) {
                            noInternet(getApplicationContext());
                        } else
                        {
                            serverError(getApplicationContext());
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("areaByCity", "");
                params.put("city_id",""+position_city);


                Log.e("params", params.toString());
                return params;
            }
        };
        PartyBooker.getInstance().addToRequestQueue(request);
    }
}
