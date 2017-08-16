package com.example.a.party_booker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SearchActivity extends AppCompatActivity {
    AppPref appPref;
    LinearLayout noenquirylayout, layout_listview,whole_layout;
    ListView listView;
    VenueAdapter venueAdapter;
    ArrayList<BeanVenue> beanVenues = new ArrayList<>();
    ImageView img_notfound;
    String cityid="",areaid="";
    EditText edit_search;
    ImageView img_search;
    String str_search="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        edit_search=(EditText)findViewById(R.id.edit_search);
        img_search=(ImageView)findViewById(R.id.img_search);

        noenquirylayout=(LinearLayout)findViewById(R.id.noenquirylayout);
        layout_listview=(LinearLayout)findViewById(R.id.layout_listview);
        img_notfound=(ImageView)findViewById(R.id.img_notfound);

        listView = (ListView) findViewById(R.id.listview_venues);
        venueAdapter = new VenueAdapter(getApplicationContext(), beanVenues, this);
        listView.setAdapter(venueAdapter);
        listView.setDivider(null);

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edit_search.getText().toString().equalsIgnoreCase(""))
                {
                    //getAllVenuesList();
                }
                else
                {
                    str_search=edit_search.getText().toString().trim();
                    getAllVenuesSearch();
                }

            }});

    }

    public void getAllVenuesSearch() {
        appPref=new AppPref(SearchActivity.this);
        final Custom_ProgressDialog loadingView = new Custom_ProgressDialog(SearchActivity.this, "");
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

                                beanVenues.clear();



                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject subObject = dataArray.getJSONObject(i);
                                    BeanVenue callList = new BeanVenue();
                                    callList.setVenueid(subObject.getString("venueid"));
                                    callList.setVenuename(subObject.getString("venuename"));
                                    //callList.setLname(subObject.getString("owneruserid"));
                                    callList.setDescription(subObject.getString("description"));
                                    callList.setPlotsize(subObject.getString("plotsize"));
                                    callList.setCapacity(subObject.getString("capacity"));
                                    callList.setCity(subObject.getString("cityname"));
                                    callList.setArea(subObject.getString("areaname"));
                                    callList.setBookingprice(subObject.getString("bookingprice"));
                                    callList.setRooms(subObject.getString("rooms"));
                                    callList.setHalls(subObject.getString("halls"));
                                    callList.setRatingid(subObject.getString("ratingid"));
                                    callList.setVenueaddress1(subObject.getString("venueaddress1"));
                                    callList.setLandmark(subObject.getString("landmark"));
                                    callList.setHallimage(subObject.getString("venueimagepath"));

                                    JSONObject ownerobj=subObject.getJSONObject("Owner");
                                    callList.setOwnerid(ownerobj.getString("userid"));
                                    callList.setOwnerfname(ownerobj.getString("firstname"));
                                    callList.setOwnwerjname(ownerobj.getString("lastname"));
                                    callList.setOwnermobile(ownerobj.getString("mobileno"));
                                    callList.setOwneremail(ownerobj.getString("email"));

                                    callList.setOverallrating(subObject.getString("overall_rating"));


                                    beanVenues.add(callList);
                                }



                                venueAdapter.notifyDataSetChanged();
                                Log.e("Size",beanVenues.size()+"");

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


                params.put("searchVenueList", "");
                params.put("filter",str_search);

                Log.e("params", params.toString());
                return params;
            }
        };

        PartyBooker.getInstance().addToRequestQueue(request);
    }
}
