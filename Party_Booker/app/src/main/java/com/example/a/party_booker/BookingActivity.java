package com.example.a.party_booker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.a.party_booker.GlobalFile.noInternet;
import static com.example.a.party_booker.GlobalFile.serverError;

public class BookingActivity extends AppCompatActivity implements Serializable {
    EditText ed_fromdate,ed_todate;
    String fromdate_to_be_send="",todate_to_be_send="";
    int mYear, mMonth, mDay;
    DatePickerDialog datePickerDialog;
    EditText edt_booking,edt_address;
    LinearLayout btn_booking;
    AppPref appPref;
    String str_booking="",userid="",venueid="",price="",str_address="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        appPref=new AppPref(getApplicationContext());
        setToolbar();
        userid=appPref.getUserid();
        Intent intent = getIntent();
        venueid = intent.getExtras().getString("venueid");
        price=intent.getExtras().getString("price");
        ed_fromdate=(EditText)findViewById(R.id.ed_fromdate);
        ed_todate=(EditText)findViewById(R.id.ed_todate);
        edt_booking=(EditText)findViewById(R.id.edit_bookingfor);
        edt_address=(EditText)findViewById(R.id.edit_address);
        btn_booking=(LinearLayout)findViewById(R.id.btn_booknow);
        ed_fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromdate();
            }
        });

        ed_todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ed_fromdate.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getApplicationContext(), "Please Select From Date First" , Toast.LENGTH_LONG).show();
                }

                else
                {
                    todate();
                }
            }
        });

        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ed_fromdate.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(BookingActivity.this, "Please Enter From Date" , Toast.LENGTH_LONG).show();
                    ed_fromdate.requestFocus();

                }
                else if(ed_todate.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(BookingActivity.this, "Please Enter To Date" , Toast.LENGTH_LONG).show();
                    ed_todate.requestFocus();
                }
                else if(edt_booking.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(BookingActivity.this, "Please Enter For what you are booking hall" , Toast.LENGTH_LONG).show();
                    edt_booking.requestFocus();
                }
                else if(edt_address.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(BookingActivity.this, "Please Enter your address" , Toast.LENGTH_LONG).show();
                    edt_address.requestFocus();
                }
                else
                {
                    str_booking = edt_booking.getText().toString().trim();
                    str_address=edt_address.getText().toString().trim();

                    if (GlobalFile.isOnline(getApplicationContext())) {

                        // new get_Otp().execute();
                        getBooked();

                    } else
                    {
                        Toast.makeText(BookingActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

    }

    private void fromdate() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        final String mYear2=   String.valueOf(mYear);
        final String mMonth2=   String.valueOf(mMonth);
        final String mDay2=   String.valueOf(mDay);

        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        fromdate_to_be_send = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;


                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar calendar1 = Calendar.getInstance();
                        Calendar calendar2 = Calendar.getInstance();

                        Log.e("todate", "" + todate_to_be_send);
                        Log.e("fromdate", "" + fromdate_to_be_send);


                        if (!todate_to_be_send.equalsIgnoreCase("")) {
                            // new GetOrderHistory(DistSalesRetailerOrderHistActivity.this).execute();
                            try {


                                Date fromDate1 = null;
                                try {
                                    fromDate1 = sdf.parse(fromdate_to_be_send);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Date toDate1 = sdf.parse(todate_to_be_send);
                                Log.e("todate", "" + fromDate1);
                                Log.e("todate", "" + toDate1);

                                Log.e("todate", "" + todate_to_be_send);
                                Log.e("todate", "" + fromdate_to_be_send);


//                                if (fromDate1.after(toDate1)) {
//                                    //  C.Toast(getApplicationContext(),"From Date Must be before To Date",getLayoutInflater());
//                                    Toast.makeText(getActivity(), "Fromdate is After Todate", Toast.LENGTH_LONG).show();
//                                    img_notfound.setImageResource(R.drawable.icon_noresult);
//                                    noenquirylayout.setVisibility(View.VISIBLE);
//                                    layout_listview.setVisibility(View.GONE);
//                                    Log.e("todate11111", "22222");
//                                }
//                                else {
//                                    noenquirylayout.setVisibility(View.GONE);
//                                    layout_listview.setVisibility(View.VISIBLE);
                                    ed_fromdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                                    getCallEnquiryAllListBySearch(fromdate_to_be_send,todate_to_be_send);
//                                    Log.e("todate11111", "33333");
//
//                                }
                            }
                            catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        else {
//                            Log.e("todate11111", "4444");
//                            noenquirylayout.setVisibility(View.GONE);
//                            layout_listview.setVisibility(View.VISIBLE);
                            ed_fromdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                            getFormEnquiryAllListBySearch(fromdate_to_be_send,todate_to_be_send);
                        }

                    }
                    //
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void todate() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        final String mYear2 = String.valueOf(mYear);
        final String mMonth2 = String.valueOf(mMonth);
        final String mDay2 = String.valueOf(mDay);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        todate_to_be_send = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;


                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar calendar1 = Calendar.getInstance();
                        Calendar calendar2 = Calendar.getInstance();

                        Log.e("todate", "" + todate_to_be_send);
                        Log.e("fromdate", "" + fromdate_to_be_send);


                        if (!fromdate_to_be_send.equalsIgnoreCase("")) {
                            // new GetOrderHistory(DistSalesRetailerOrderHistActivity.this).execute();
                            try {


                                Date fromDate1 = null;
                                try {
                                    fromDate1 = sdf.parse(fromdate_to_be_send);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Date toDate1 = sdf.parse(todate_to_be_send);
                                Log.e("todate", "" + fromDate1);
                                Log.e("todate", "" + toDate1);

                                Log.e("todate", "" + todate_to_be_send);
                                Log.e("todate", "" + fromdate_to_be_send);


//                                if (toDate1.before(fromDate1)) {
//                                    //  C.Toast(getApplicationContext(),"From Date Must be before To Date",getLayoutInflater());
//                                    Toast.makeText(getActivity(), "Fromdate is Greater than Todate", Toast.LENGTH_LONG).show();
//                                    img_notfound.setImageResource(R.drawable.icon_noresult);
//                                    noenquirylayout.setVisibility(View.VISIBLE);
//                                    layout_listview.setVisibility(View.GONE);
//                                    Log.e("todate11111", "22222");
//                                } else {
//                                    noenquirylayout.setVisibility(View.GONE);
//                                    layout_listview.setVisibility(View.VISIBLE);
                                    ed_todate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                                    getCallEnquiryAllListBySearch(fromdate_to_be_send,todate_to_be_send);
//                                    Log.e("todate11111", "33333");
//
//                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("todate11111", "4444");
                            ed_todate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void getBooked() {
        appPref=new AppPref(getApplicationContext());
        final Custom_ProgressDialog loadingView = new Custom_ProgressDialog(BookingActivity.this, "");
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

                            String date = jObj.getString("status");
                            if (date.equalsIgnoreCase("false")) {
                                String Message = jObj.getString("message");
                                Toast.makeText(BookingActivity.this, "" + Message, Toast.LENGTH_LONG).show();
                                // GlobalFile.CustomToast(Activity_Login.this,""+Message, getLayoutInflater());
                                loadingView.dismiss();
                                //layout_login.setVisibility(View.VISIBLE);
                                //avi.hide();
                            } else {
                                Log.e("ffff","fffff");

                                String Message = jObj.getString("message");
                                Toast.makeText(BookingActivity.this, "" + Message, Toast.LENGTH_LONG).show();
                                //Globals.CustomToast(MainActivity.this,"Available Laminate Categories", getLayoutInflater());
                                loadingView.dismiss();


                                Intent i = new Intent(BookingActivity.this, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();



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
                params.put("bookVenue", "");
                params.put("user_id", userid);
                params.put("venue_id", venueid);
                params.put("bookingDate",fromdate_to_be_send);
                params.put("leavingDate", todate_to_be_send);
                params.put("price", price);
                params.put("booking_for", str_booking);
                params.put("address1",str_address);
                params.put("address2","");
                params.put("address3","");

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
