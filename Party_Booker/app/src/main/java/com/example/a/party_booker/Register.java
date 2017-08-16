package com.example.a.party_booker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.a.party_booker.GlobalFile.noInternet;
import static com.example.a.party_booker.GlobalFile.serverError;

public class Register extends AppCompatActivity {
    EditText fname,lname,password,mobile,email;
    String str_fname="",str_lname="",str_mobile="",str_email="",str_password="";
    AppPref appPref;
    Button register,login;

    //String fname,lname,mobile,email,password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        appPref=new AppPref(Register.this);
        fetchid();


    }

    private void fetchid() {
        fname=(EditText)findViewById(R.id.fname);
        lname=(EditText)findViewById(R.id.lname);
        password=(EditText)findViewById(R.id.password);
        mobile=(EditText)findViewById(R.id.mobile);
        email=(EditText)findViewById(R.id.email);

        register=(Button)findViewById(R.id.signup);
        login=(Button)findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fname.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter First Name",Toast.LENGTH_LONG).show();
                    fname.requestFocus();
                }
                else if(lname.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Last Name",Toast.LENGTH_LONG).show();
                    lname.requestFocus();
                }
                else if(email.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Email Id",Toast.LENGTH_LONG).show();
                    email.requestFocus();
                }
                else if(mobile.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Mobile No.",Toast.LENGTH_LONG).show();
                    mobile.requestFocus();
                }

                else if(mobile.length()<10)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Valid Mobile No.",Toast.LENGTH_LONG).show();
                    mobile.requestFocus();
                }
                else if(password.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Password",Toast.LENGTH_LONG).show();
                    password.requestFocus();
                }
                else
                {
//                    appPref.setfName(fname.getText().toString().trim());
//                    appPref.setlName(lname.getText().toString().trim());
//                    appPref.setEmailId(email.getText().toString().trim());
//                    appPref.setMobileNo(mobile.getText().toString().trim());
//                    appPref.setPassword(password.getText().toString().trim());
//
//                    Intent i=new Intent(getApplicationContext(),SelectCity.class);
//                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    appPref.setLoggedIn(true);
//                    startActivity(i);
//                    finish();

                    str_email = email.getText().toString().trim();
                    str_password = password.getText().toString().trim();
                    str_fname=fname.getText().toString().trim();
                    str_lname=lname.getText().toString().trim();
                    str_mobile=mobile.getText().toString().trim();

                    if (GlobalFile.isOnline(getApplicationContext())) {

                        // new get_Otp().execute();
                        getRegister(str_fname,str_lname,str_mobile,str_email,str_password);
                        hideSoftKeyboard();
                    } else
                    {
                        Toast.makeText(Register.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                    }



                }

            }
        });
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void getRegister(final String Fname,final String Lname,final String Mobile,final String Email, final String Password) {
        appPref=new AppPref(getApplicationContext());
        final Custom_ProgressDialog loadingView = new Custom_ProgressDialog(Register.this, "");
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
                                Toast.makeText(Register.this, "" + Message, Toast.LENGTH_LONG).show();
                                // GlobalFile.CustomToast(Activity_Login.this,""+Message, getLayoutInflater());
                                loadingView.dismiss();
                                //layout_login.setVisibility(View.VISIBLE);
                                //avi.hide();
                            } else {
                                Log.e("ffff","fffff");

                                String Message = jObj.getString("message");
                                Toast.makeText(Register.this, "" + Message, Toast.LENGTH_LONG).show();
                                //Globals.CustomToast(MainActivity.this,"Available Laminate Categories", getLayoutInflater());
                                loadingView.dismiss();

                                Intent i = new Intent(Register.this, Login.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();


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
                params.put("registerUser", "");
                params.put("first_name", Fname);
                params.put("last_name", Lname);
                params.put("mobile",Mobile);
                params.put("password",Password);
                params.put("email",Email);

                Log.e("params", params.toString());
                return params;
            }
        };
        PartyBooker.getInstance().addToRequestQueue(request);
    }

}
