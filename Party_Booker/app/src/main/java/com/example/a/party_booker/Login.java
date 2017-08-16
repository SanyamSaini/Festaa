package com.example.a.party_booker;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.example.a.party_booker.GlobalFile.noInternet;
import static com.example.a.party_booker.GlobalFile.serverError;

public class Login extends AppCompatActivity {
    EditText email,password;
    String str_email="",str_password="";
    Button login;
    TextView signup;
    AppPref appPref;
    boolean isMdevice;
    boolean pstatus;
    int code=1;
    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE ,
            android.Manifest.permission.CAMERA,android.Manifest.permission.INTERNET, android.Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS,
            android.Manifest.permission.ACCESS_WIFI_STATE, android.Manifest.permission.ACCESS_NETWORK_STATE, android.Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        isMdevice=isMarshmallowPlusDevice();
        pstatus = isPermissionRequestRequired(Login.this, perms, code);
        appPref=new AppPref(getApplicationContext());
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);



        login=(Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Email ID",Toast.LENGTH_LONG).show();
                    email.requestFocus();
                }
                else if(password.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Password",Toast.LENGTH_LONG).show();
                    password.requestFocus();
                }
//                else if(!email.getText().toString().equalsIgnoreCase(appPref.getEmailId()))
//                {
//                    Toast.makeText(getApplicationContext(),"Please Enter Correct Email ID",Toast.LENGTH_LONG).show();
//                    email.requestFocus();
//                }
//                else if(!password.getText().toString().equalsIgnoreCase(appPref.getPassword()))
//                {
//                    Toast.makeText(getApplicationContext(),"Please Enter Correct Password",Toast.LENGTH_LONG).show();
//                    password.requestFocus();
//                }

                else
                {
//                    Intent i=new Intent(getApplicationContext(),SelectCity.class);
//                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    appPref.setLoggedIn(true);
//                    startActivity(i);
//                    finish();

                    str_email = email.getText().toString().trim();
                    str_password = password.getText().toString().trim();

                    if (GlobalFile.isOnline(getApplicationContext())) {

                        // new get_Otp().execute();
                        getLogin(str_email,str_password);
                        hideSoftKeyboard();
                    } else
                    {
                        Toast.makeText(Login.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                    }


                }


            }
        });

        signup=(TextView) findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ii=new Intent(getApplicationContext(),Register.class);
                startActivity(ii);
                finish();
            }
        });



    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static boolean isMarshmallowPlusDevice() {

        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isPermissionRequestRequired(Activity activity, @NonNull String[] permissions, int requestCode) {
        if (isMarshmallowPlusDevice() && permissions.length > 0) {
            List<String> newPermissionList = new ArrayList<>();
            for (String permission : permissions) {
                if (PERMISSION_GRANTED != activity.checkSelfPermission(permission)) {
                    newPermissionList.add(permission);

                }
            }
            if (newPermissionList.size() > 0) {
                activity.requestPermissions(newPermissionList.toArray(new String[newPermissionList.size()]), requestCode);
                return true;
            }


        }

        return false;
    }

    public void getLogin(final String Email, final String Password) {
        appPref=new AppPref(getApplicationContext());
        final Custom_ProgressDialog loadingView = new Custom_ProgressDialog(Login.this, "");
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
                                Toast.makeText(Login.this, "" + Message, Toast.LENGTH_LONG).show();
                                // GlobalFile.CustomToast(Activity_Login.this,""+Message, getLayoutInflater());
                                loadingView.dismiss();
                                //layout_login.setVisibility(View.VISIBLE);
                                //avi.hide();
                            } else {
                                Log.e("ffff","fffff");

                                String Message = jObj.getString("message");
                                //Globals.CustomToast(MainActivity.this,"Available Laminate Categories", getLayoutInflater());
                                loadingView.dismiss();

                                JSONObject dataObject=jObj.getJSONObject("data");
                                //JSONObject subObject=dataObject.getJSONObject("User");


                                appPref.setUserid(dataObject.getString("userid"));
                                Log.e("USER ID",""+appPref.getUserid());
                                //appPref.setRole(subObject.getString("role_id"));
                                appPref.setEmailId(dataObject.getString("email"));
                                appPref.setfName(dataObject.getString("firstname"));
                                appPref.setlName(dataObject.getString("lastname"));
                                appPref.setMobileNo(dataObject.getString("mobileno"));
                                //appPref.setProfilePic(subObject.getString("image"));

                                appPref.setLoggedIn(true);


                                Intent i = new Intent(Login.this, SelectCity.class);
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
                params.put("loginUser", "");
                params.put("email_id", Email);
                params.put("password", Password);
                params.put("role","3");

                Log.e("params", params.toString());
                return params;
            }
        };
        PartyBooker.getInstance().addToRequestQueue(request);
    }
}
