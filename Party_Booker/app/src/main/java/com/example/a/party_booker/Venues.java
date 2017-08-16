package com.example.a.party_booker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.a.party_booker.GlobalFile.noInternet;
import static com.example.a.party_booker.GlobalFile.serverError;

/**
 * Created by A on 19-03-2017.
 */

public class Venues extends Fragment implements Serializable {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //getContext().getTheme().applyStyle(R.style.NoActionBarForCalenderPage, true); //blue ripple color

        final View view=inflater.inflate(R.layout.venues, container, false);

//        Intent intent = getActivity().getIntent();
//        cityid = intent.getExtras().getString("cityid");
//        areaid=intent.getExtras().getString("areaid");

        appPref= new AppPref(getActivity());

//        edit_search=(EditText)view.findViewById(R.id.edit_search);
//        img_search=(ImageView)view.findViewById(R.id.img_search);

        cityid=appPref.getCityid();
        areaid=appPref.getAreaid();

        noenquirylayout=(LinearLayout)view.findViewById(R.id.noenquirylayout);
        layout_listview=(LinearLayout)view.findViewById(R.id.layout_listview);
        img_notfound=(ImageView)view.findViewById(R.id.img_notfound);

        listView = (ListView) view.findViewById(R.id.listview_venues);
        venueAdapter = new VenueAdapter(getActivity(), beanVenues, getActivity());
        listView.setAdapter(venueAdapter);
        listView.setDivider(null);

//        img_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(edit_search.getText().toString().equalsIgnoreCase(""))
//                {
//                    getAllVenuesList();
//                }
//                else
//                {
//                    str_search=edit_search.getText().toString().trim();
//                    getAllVenuesSearch();
//                }
//
//          }});

        getAllVenuesList();

        return view;}

    @Override
    public void onResume() {
        super.onResume();
        getAllVenuesList();
    }

    public void getAllVenuesList() {
        appPref=new AppPref(getActivity());
        final Custom_ProgressDialog loadingView = new Custom_ProgressDialog(getActivity(), "");
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
                            noInternet(getActivity());
                        } else {
                            serverError(getActivity());
                        }
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                    params.put("getVenueList", "");
                    params.put("city_id",cityid);
                params.put("area_id",areaid);

                Log.e("params", params.toString());
                return params;
            }
        };

        PartyBooker.getInstance().addToRequestQueue(request);
    }

    public void getAllVenuesSearch() {
        appPref=new AppPref(getActivity());
        final Custom_ProgressDialog loadingView = new Custom_ProgressDialog(getActivity(), "");
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
                            noInternet(getActivity());
                        } else {
                            serverError(getActivity());
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
