package com.example.a.party_booker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Dhruv on 14/04/2017.
 */

public class VenueAdapter extends BaseAdapter {
    List<BeanVenue> beanVenues;
    Context context;
    Activity activity;


    public VenueAdapter(Context context, List<BeanVenue> beanVenues, Activity activity){
        this.context=context;
        this.beanVenues=beanVenues;
        this.activity=activity;
    }


    @Override
    public int getCount() {
        return beanVenues.size();
    }

    @Override
    public Object getItem(int position) {
        return beanVenues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view;
        final AppPref appPref;

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view=inflater.inflate(R.layout.custom_cepr_list_layout,parent,false);

        final BeanVenue items=beanVenues.get(position);
        view = inflater.inflate(R.layout.custom_list_venue, null);
        TextView hallTitle = (TextView) view.findViewById(R.id.halltitle);
        TextView hallDescrip = (TextView) view.findViewById(R.id.halldescrip);
        LinearLayout venueClicked=(LinearLayout)view.findViewById(R.id.venueClicked);

        ImageView imgHall= (ImageView) view.findViewById(R.id.hall_image);

        hallTitle.setText(beanVenues.get(position).getVenuename());
        hallDescrip.setText(beanVenues.get(position).getDescription());

        Picasso.with(context)
                .load(GlobalFile.image_link+ beanVenues.get(position).getHallimage())
                .placeholder(R.drawable.noimagefound)
                .into(imgHall);


        venueClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(context,DescriptionActivity.class);
                ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ii.putExtra("beanvenue",beanVenues.get(position));
                //ii.putExtra("areaid",position_area);
                activity.startActivity(ii);

            }
        });

        appPref=new AppPref(context);

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.d("Size in Adapter",beanVenues.size()+"");
    }

    public void updateData(List<BeanVenue> beanVenues)
    {
        this.beanVenues=beanVenues;
        notifyDataSetChanged();
    }

}
