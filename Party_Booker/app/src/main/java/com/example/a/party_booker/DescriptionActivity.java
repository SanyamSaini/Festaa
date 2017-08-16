package com.example.a.party_booker;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class DescriptionActivity extends AppCompatActivity implements Serializable {
    BeanVenue beanVenue;
    ImageView productimage;
    LinearLayout btn_rating;
    TextView hallname,halldescp,plotsize,capacity,rooms,halls,bookingprice,landmark,area,city,halladdress;
    String overallrating;
    LinearLayout ratinglayout;
    TextView rating,name,email,mobile;
    LinearLayout booknow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        setToolbar();
        Intent intent=getIntent();
        beanVenue=(BeanVenue)intent.getSerializableExtra("beanvenue");
        fetchid();
        //hallname=(TextView)findViewById(R.id.hallName);


        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setTitle(beanVenue.getVenuename());

        //collapsingToolbar.setTitle(beanCategoryItem.getCategory_name());

        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.colorAccent));
        collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.colorPrimary));

        collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedappbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productimage=(ImageView)collapsingToolbar.findViewById(R.id.product_image);

        Picasso.with(getApplicationContext())
                .load(GlobalFile.image_link+ beanVenue.getHallimage())
                .placeholder(R.drawable.noimagefound)
                .into(productimage);

    }

    private void fetchid() {
        booknow=(LinearLayout)findViewById(R.id.booknow);
        name=(TextView)findViewById(R.id.txtName);
        email=(TextView)findViewById(R.id.txtEmail);
        mobile=(TextView)findViewById(R.id.txtMobile);
        rating=(TextView)findViewById(R.id.txt_rating);
        ratinglayout=(LinearLayout)findViewById(R.id.ratinglayout);
        btn_rating=(LinearLayout)findViewById(R.id.btn_ratings);
        halldescp=(TextView)findViewById(R.id.hallDescription);
        plotsize=(TextView)findViewById(R.id.txt_plotsize);
        capacity=(TextView)findViewById(R.id.txt_capacity);
        rooms=(TextView)findViewById(R.id.txt_rooms);
        halls=(TextView)findViewById(R.id.txt_halls);
        bookingprice=(TextView)findViewById(R.id.txt_bookingprice);
        landmark=(TextView)findViewById(R.id.txt_landmark);
        area=(TextView)findViewById(R.id.txt_area);
        city=(TextView)findViewById(R.id.txt_city);
        halladdress=(TextView)findViewById(R.id.txt_venue);

        halldescp.setText(beanVenue.getDescription());
        plotsize.setText(beanVenue.getPlotsize());
        capacity.setText(beanVenue.getCapacity());
        rooms.setText(beanVenue.getRooms());
        halls.setText(beanVenue.getHalls());
        bookingprice.setText(getResources().getString(R.string.Rs)+" "+beanVenue.getBookingprice());
        landmark.setText(beanVenue.getLandmark());
        area.setText(beanVenue.getArea());
        city.setText(beanVenue.getCity());
        halladdress.setText(beanVenue.getVenueaddress1());
        name.setText(beanVenue.getOwnerfname()+" "+beanVenue.getOwnwerjname());
        email.setText(beanVenue.getOwneremail());
        mobile.setText(beanVenue.getOwnermobile());
        overallrating= beanVenue.getOverallrating();
        Log.e("overallrating",""+overallrating);

        if(overallrating.equalsIgnoreCase(null))
        {
            ratinglayout.setVisibility(View.GONE);
        }
        else
        {
            ratinglayout.setVisibility(View.VISIBLE);
            rating.setText("Overall Ratings : "+overallrating);
        }

        btn_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii=new Intent(getApplicationContext(),RatingActivity.class);
                ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ii.putExtra("venueid",beanVenue.getVenueid());
                startActivity(ii);
            }
        });

        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii=new Intent(getApplicationContext(),BookingActivity.class);
                ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ii.putExtra("venueid",beanVenue.getVenueid());
                ii.putExtra("price",beanVenue.getBookingprice());
                startActivity(ii);
            }
        });


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
