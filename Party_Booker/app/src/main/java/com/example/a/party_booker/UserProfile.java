package com.example.a.party_booker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by A on 19-03-2017.
 */

public class UserProfile extends Fragment {

    TextView name,email,mobile;
    AppPref appPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.userprofile, container, false);

        appPref=new AppPref(getActivity());

        name=(TextView)rootView.findViewById(R.id.txtUserType);
        email=(TextView)rootView.findViewById(R.id.txtEmail);
        mobile=(TextView)rootView.findViewById(R.id.txtMobile);

        name.setText(appPref.getfName()+" "+appPref.getlName());
        email.setText(appPref.getEmailId());
        mobile.setText(appPref.getMobileNo());



        return rootView;
    }
}
