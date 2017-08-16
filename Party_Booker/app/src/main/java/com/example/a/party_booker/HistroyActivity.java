package com.example.a.party_booker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

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

public class HistroyActivity extends AppCompatActivity {
    AppPref appPref;
    LinearLayout noenquirylayout, layout_listview,whole_layout;
    ListView listView;
    HistroyAdapter histroyAdapter;
    ArrayList<BeanHistroy> beanHistroys = new ArrayList<>();
    ImageView img_notfound;
    String cityid="",areaid="";
    EditText edit_search;
    ImageView img_search;
    String str_search="";
    String userid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histroy);
        appPref=new AppPref(getApplicationContext());
        userid=appPref.getUserid();

        setToolbar();

        noenquirylayout=(LinearLayout)findViewById(R.id.noenquirylayout);
        layout_listview=(LinearLayout)findViewById(R.id.layout_listview);
        img_notfound=(ImageView)findViewById(R.id.img_notfound);

        listView = (ListView) findViewById(R.id.listview_histroy);
        histroyAdapter = new HistroyAdapter(getApplicationContext(), beanHistroys, this);
        listView.setAdapter(histroyAdapter);
        listView.setDivider(null);

        getAllHistroy();

    }

    public void getAllHistroy() {
        appPref=new AppPref(HistroyActivity.this);
        final Custom_ProgressDialog loadingView = new Custom_ProgressDialog(HistroyActivity.this, "");
        loadingView.setCancelable(false);
        loadingView.show();
        //layout_login.setVisibility(View.GONE);
        StringRequest request = new StringRequest(GlobalFile.POST, GlobalFile.server_link ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("JSON", response);

                        try {
                            JSONObject jObj = new JSONObject(response);


                            boolean date = jObj.getBoolean("status");
                            if (date==false) {
                                String Message = jObj.getString("message");
                                //Toast.makeText(getActivity(), "" + Message, Toast.LENGTH_LONG).show();
                                img_notfound.setImageResource(R.drawable.noitem);
                                noenquirylayout.setVisibility(View.VISIBLE);
                                layout_listview.setVisibility(View.GONE);

                                loadingView.dismiss();
                                //layout_login.setVisibility(View.VISIBLE);
                                //avi.hide();
                            }
                            else {
                                Log.e("ffff","fffff");

                                String Message = jObj.getString("message");
                                noenquirylayout.setVisibility(View.GONE);
                                layout_listview.setVisibility(View.VISIBLE);
                                //Globals.CustomToast(MainActivity.this,"Available Laminate Categories", getLayoutInflater());
                                loadingView.dismiss();

                                JSONArray dataArray=jObj.getJSONArray("data");

                                beanHistroys.clear();

                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject subObject = dataArray.getJSONObject(i);
                                    BeanHistroy callList = new BeanHistroy();
                                    callList.setStatus(subObject.getString("status"));
                                    callList.setBookingdate(subObject.getString("bookingdate"));
                                    callList.setPrice(subObject.getString("price"));
                                    callList.setLeavingdate(subObject.getString("leavingdate"));
                                    callList.setBookingfor(subObject.getString("bookingfor"));


                                    JSONObject ownerobj=subObject.getJSONObject("Venue");
                                    callList.setVenuename(ownerobj.getString("venuename"));
                                    callList.setAddress(ownerobj.getString("venueaddress1"));


                                    beanHistroys.add(callList);
                                }



                                histroyAdapter.notifyDataSetChanged();
                                Log.e("Size",beanHistroys.size()+"");

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
                        // Log.e("ERROR",error.getMessage());
                        if (error instanceof NetworkError) {
                            noInternet(getApplicationContext());
                        } else {
                            serverError(getApplicationContext());
                        }
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("bookingHistory", "");
                params.put("user_id",userid);

                Log.e("params", params.toString());
                return params;
            }
        };

        PartyBooker.getInstance().addToRequestQueue(request);
    }

    private void setToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
