package com.example.a.party_booker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import java.util.Date;
import java.util.List;

/**
 * Created by SEO on 5/11/2017.
 */

public class HistroyAdapter extends BaseAdapter {

    List<BeanHistroy> beanHistroys;
    Context context;
    Activity activity;
    String toSplit="";
    Date date1;


    public HistroyAdapter(Context context, List<BeanHistroy> beanHistroys, Activity activity){
        this.context=context;
        this.beanHistroys=beanHistroys;
        this.activity=activity;
    }


    @Override
    public int getCount() {
        return beanHistroys.size();
    }

    @Override
    public Object getItem(int position) {
        return beanHistroys.get(position);
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

        final BeanHistroy items=beanHistroys.get(position);
        view = inflater.inflate(R.layout.custom_histroy, null);
        TextView date = (TextView) view.findViewById(R.id.txtDate);
        TextView event = (TextView) view.findViewById(R.id.txtEvent);
        TextView hall = (TextView) view.findViewById(R.id.txtHall);
        TextView venue = (TextView) view.findViewById(R.id.txtVenue);
        TextView price = (TextView) view.findViewById(R.id.txtPrice);
        TextView status = (TextView) view.findViewById(R.id.txtStatus);
        //LinearLayout venueClicked=(LinearLayout)view.findViewById(R.id.venueClicked);

       // ImageView imgHall= (ImageView) view.findViewById(R.id.hall_image);

       // date.setText(beanHistroys.get(position).getBookingdate());

        toSplit=beanHistroys.get(position).getBookingdate();
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            date1 = sdf.parse(toSplit);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        date.setText("" + new SimpleDateFormat("dd MMM yyyy").format(date1));

        event.setText(beanHistroys.get(position).getBookingfor());
        hall.setText(beanHistroys.get(position).getVenuename());
        venue.setText(beanHistroys.get(position).getAddress());
        price.setText(beanHistroys.get(position).getPrice());
        status.setText(beanHistroys.get(position).getStatus());

        if(beanHistroys.get(position).getStatus().equalsIgnoreCase("Completed"))
        {
            status.setBackgroundResource(R.drawable.orderhistroy_background);
        }
        if(beanHistroys.get(position).getStatus().equalsIgnoreCase("Pending"))
        {
            status.setBackgroundResource(R.drawable.ontheway_background);
        }


//        Picasso.with(context)
//                .load(GlobalFile.image_link+ beanHistroys.get(position).getHallimage())
//                .placeholder(R.drawable.noimagefound)
//                .into(imgHall);


//        venueClicked.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent ii=new Intent(context,DescriptionActivity.class);
//                ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                ii.putExtra("beanvenue",beanHistroys.get(position));
//                //ii.putExtra("areaid",position_area);
//                activity.startActivity(ii);
//
//            }
//        });

        appPref=new AppPref(context);

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.d("Size in Adapter",beanHistroys.size()+"");
    }

    public void updateData(List<BeanHistroy> beanHistroys)
    {
        this.beanHistroys=beanHistroys;
        notifyDataSetChanged();
    }


}
