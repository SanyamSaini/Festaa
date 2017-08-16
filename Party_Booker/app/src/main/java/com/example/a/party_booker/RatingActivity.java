package com.example.a.party_booker;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.example.a.party_booker.GlobalFile.noInternet;
import static com.example.a.party_booker.GlobalFile.serverError;

public class RatingActivity extends AppCompatActivity implements Serializable {
    ImageView t_b,t_g,b_b,b_g,a_b,a_g,s_b,s_g,m_b,m_g;
    EditText edt_comment,edt_email;
    FrameLayout frame1,frame2,frame3,frame4,frame5;
    String rate="",email,comment;
    String userid="",venueid="";
    AppPref appPrefs;
    LinearLayout btn_giveratings;
    Custom_ProgressDialog loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        appPrefs=new AppPref(getApplicationContext());
        setToolbar();
        userid=appPrefs.getUserid();
        Intent intent = getIntent();
        venueid = intent.getExtras().getString("venueid");
        fetchid();
    }

    private void fetchid() {
        frame1=(FrameLayout)findViewById(R.id.frame_1);
        frame2=(FrameLayout)findViewById(R.id.frame_2);
        frame3=(FrameLayout)findViewById(R.id.frame_3);
        frame4=(FrameLayout)findViewById(R.id.frame_4);
        frame5=(FrameLayout)findViewById(R.id.frame_5);
        edt_comment=(EditText)findViewById(R.id.edit_comment);
        //edt_email=(EditText)findViewById(R.id.edit_email);
        //edt_email.setText(appPrefs.getEmailId());
        btn_giveratings=(LinearLayout)findViewById(R.id.btn_giveratings);

        t_b=(ImageView)findViewById(R.id.angry_b);
        t_g=(ImageView)findViewById(R.id.angry_g);
        b_b=(ImageView)findViewById(R.id.sad_b);
        b_g=(ImageView)findViewById(R.id.sad_g);
        a_b=(ImageView)findViewById(R.id.silent_b);
        a_g=(ImageView)findViewById(R.id.silent_g);
        s_b=(ImageView)findViewById(R.id.smile_b);
        s_g=(ImageView)findViewById(R.id.smile_g);
        m_b=(ImageView)findViewById(R.id.moresmile_b);
        m_g=(ImageView)findViewById(R.id.moresmile_g);

        frame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t_g.setVisibility(View.GONE);
                t_b.setVisibility(View.VISIBLE);
                b_b.setVisibility(View.GONE);
                b_g.setVisibility(View.VISIBLE);
                a_b.setVisibility(View.GONE);
                a_g.setVisibility(View.VISIBLE);
                s_b.setVisibility(View.GONE);
                s_g.setVisibility(View.VISIBLE);
                m_b.setVisibility(View.GONE);
                m_g.setVisibility(View.VISIBLE);
            }
        });

        frame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t_b.setVisibility(View.GONE);
                t_g.setVisibility(View.VISIBLE);
                b_g.setVisibility(View.GONE);
                b_b.setVisibility(View.VISIBLE);
                a_b.setVisibility(View.GONE);
                a_g.setVisibility(View.VISIBLE);
                s_b.setVisibility(View.GONE);
                s_g.setVisibility(View.VISIBLE);
                m_b.setVisibility(View.GONE);
                m_g.setVisibility(View.VISIBLE);
            }
        });

        frame3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t_b.setVisibility(View.GONE);
                t_g.setVisibility(View.VISIBLE);
                b_b.setVisibility(View.GONE);
                b_g.setVisibility(View.VISIBLE);
                a_g.setVisibility(View.GONE);
                a_b.setVisibility(View.VISIBLE);
                s_b.setVisibility(View.GONE);
                s_g.setVisibility(View.VISIBLE);
                m_b.setVisibility(View.GONE);
                m_g.setVisibility(View.VISIBLE);
            }
        });

        frame4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t_b.setVisibility(View.GONE);
                t_g.setVisibility(View.VISIBLE);
                b_b.setVisibility(View.GONE);
                b_g.setVisibility(View.VISIBLE);
                a_b.setVisibility(View.GONE);
                a_g.setVisibility(View.VISIBLE);
                s_g.setVisibility(View.GONE);
                s_b.setVisibility(View.VISIBLE);
                m_b.setVisibility(View.GONE);
                m_g.setVisibility(View.VISIBLE);
            }
        });

        frame5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t_b.setVisibility(View.GONE);
                t_g.setVisibility(View.VISIBLE);
                b_b.setVisibility(View.GONE);
                b_g.setVisibility(View.VISIBLE);
                a_b.setVisibility(View.GONE);
                a_g.setVisibility(View.VISIBLE);
                s_b.setVisibility(View.GONE);
                s_g.setVisibility(View.VISIBLE);
                m_g.setVisibility(View.GONE);
                m_b.setVisibility(View.VISIBLE);
            }
        });

        btn_giveratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(t_b.getVisibility()==View.GONE && b_b.getVisibility()==View.GONE && a_b.getVisibility()==View.GONE && s_b.getVisibility()==View.GONE && m_b.getVisibility()==View.GONE)
                {
                    Toast.makeText(getApplicationContext(),"Please Give Rating",Toast.LENGTH_LONG).show();
                    frame1.requestFocus();
                }
               else if(edt_comment.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getApplicationContext(),"Please enter Comments",Toast.LENGTH_LONG).show();
                    edt_comment.requestFocus();
                }
                else
                {
                    if(t_b.getVisibility()==View.VISIBLE)
                    {
                        rate="1";
                    }
                    if(b_b.getVisibility()==View.VISIBLE)
                    {
                        rate="2";
                    }
                    if(a_b.getVisibility()==View.VISIBLE)
                    {
                        rate="3";
                    }
                    if(s_b.getVisibility()==View.VISIBLE)
                    {
                        rate="4";
                    }
                    if(m_b.getVisibility()==View.VISIBLE)
                    {
                        rate="5";
                    }

                    comment=edt_comment.getText().toString().trim();
                    //email=edt_email.getText().toString().trim();

                    if (GlobalFile.isOnline(getApplicationContext())) {

                        Ratings();
                        //new Comments().execute();
                        Log.e("rate",""+rate);
                        //Globals.CustomToast(FeedBack.this, "Done", getLayoutInflater());
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please check your Internet Connection",Toast.LENGTH_LONG).show();

                    }

                }
            }
        });

    }

    public void Ratings() {
        //appPref=new AppPref(getApplicationContext());
        final Custom_ProgressDialog loadingView = new Custom_ProgressDialog(RatingActivity.this, "");
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
                                Toast.makeText(RatingActivity.this, "" + Message, Toast.LENGTH_LONG).show();
                                // GlobalFile.CustomToast(Activity_Login.this,""+Message, getLayoutInflater());
                                loadingView.dismiss();
                                //layout_login.setVisibility(View.VISIBLE);
                                //avi.hide();
                            } else {
                                Log.e("ffff","fffff");

                                String Message = jObj.getString("message");
                                //Globals.CustomToast(MainActivity.this,"Available Laminate Categories", getLayoutInflater());

                                    Comments();
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
                params.put("makeRating", "");
                params.put("user_id", userid);
                params.put("venue_id", venueid);
                params.put("rating",rate);

                Log.e("params", params.toString());
                return params;
            }
        };
        PartyBooker.getInstance().addToRequestQueue(request);
    }

    public void Comments() {
        //appPref=new AppPref(getApplicationContext());
        final Custom_ProgressDialog loadingView = new Custom_ProgressDialog(RatingActivity.this, "");
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
                                Toast.makeText(RatingActivity.this, "" + Message, Toast.LENGTH_LONG).show();
                                // GlobalFile.CustomToast(Activity_Login.this,""+Message, getLayoutInflater());
                                loadingView.dismiss();
                                //layout_login.setVisibility(View.VISIBLE);
                                //avi.hide();
                            } else {
                                Log.e("ffff","fffff");

                                String Message = jObj.getString("message");
                                //Globals.CustomToast(MainActivity.this,"Available Laminate Categories", getLayoutInflater());

                                Toast.makeText(RatingActivity.this,"Thanks For Rating", Toast.LENGTH_LONG).show();

                                onBackPressed();
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
                params.put("makeComment", "");
                params.put("user_id", userid);
                params.put("venue_id", venueid);
                params.put("comment",comment);

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
