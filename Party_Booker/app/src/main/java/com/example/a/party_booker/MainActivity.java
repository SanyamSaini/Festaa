package com.example.a.party_booker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout myDrawerLayout;
    NavigationView myNavigationView;
    FragmentManager myFragmentManager;
    FragmentTransaction myFragmentTransaction;
    TextView txt_title;
    ImageView ecofms, logout;

    AppPref appPref;
    ImageView logoutimg;
    ImageView search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appPref=new AppPref(getApplicationContext());
        search=(ImageView)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                //finish();
            }
        });


        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        myNavigationView = (NavigationView) findViewById(R.id.nav_drawer);

        myFragmentManager = getSupportFragmentManager();
        myFragmentTransaction = myFragmentManager.beginTransaction();
        myFragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();

        myNavigationView = (NavigationView) findViewById(R.id.nav_drawer);
        myNavigationView.setNavigationItemSelectedListener(this);

        myNavigationView.setItemIconTintList(null);
        myNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem selectedMenuItem) {
                myDrawerLayout.closeDrawers();

                if (selectedMenuItem.getItemId() == R.id.histroy) {
                    Log.e("home", "home");
//                    FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();
                    Intent i = new Intent(MainActivity.this, HistroyActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    //finish();

                }

                else if (selectedMenuItem.getItemId() == R.id.contactus) {
                    Log.e("contact", "contact");

                    //finish();
                } else if (selectedMenuItem.getItemId() == R.id.aboutus) {
                    Log.e("about", "about");

                    //finish();
                }
                else if(selectedMenuItem.getItemId() == R.id.shares)
                {
                    Log.e("share", "share");

                    try {
                        String shareBody = GlobalFile.share;
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        //sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,""+s+"(Open it in Google Play Store to Download the Application)");

                        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    }
                    catch (Exception e) {

                    }
                }

                else if (selectedMenuItem.getItemId() == R.id.logout) {

                    Log.e("logout", "logout");
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                            MainActivity.this);

                    alertDialog.setTitle(Html.fromHtml("<font color='#000'>Log Out application?</font>"));
                    alertDialog.setMessage(Html.fromHtml("<font color='#000'>Are you sure to logout?</font>"));
                    alertDialog.setIcon(R.drawable.plogout);

                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    appPref = new AppPref(MainActivity.this);
                                    Intent i = new Intent(MainActivity.this, Login.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    appPref.setLoggedIn(false);
                                    appPref.setSelected(false);
                                    //appPref.setUserId("");
                                    startActivity(i);
                                    finish();

                                }
                            });
                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // Write your code here to invoke NO event
                                    dialog.cancel();
                                }
                            });
                    // Showing Alert Message
                    alertDialog.show();
                }

                else
                {

                }

                return false;
            }

        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //ecofms = (ImageView) findViewById(R.id.ecofms);
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);

        myDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        if (appPref.isLoggedIn()) {
            Menu menu = myNavigationView.getMenu();

            MenuItem bedMenuItem = menu.findItem(R.id.login);
            bedMenuItem.setVisible(false);

            MenuItem bed1MenuItem = menu.findItem(R.id.logout);
            bed1MenuItem.setVisible(true);

        } else {
            Menu menu = myNavigationView.getMenu();

            MenuItem bedMenuItem = menu.findItem(R.id.login);
            MenuItem bed1MenuItem = menu.findItem(R.id.logout);

            bedMenuItem.setVisible(true);

            bed1MenuItem.setVisible(false);
        }
}

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}

