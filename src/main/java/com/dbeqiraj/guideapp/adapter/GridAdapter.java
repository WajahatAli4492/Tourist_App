package com.dbeqiraj.guideapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dbeqiraj.guideapp.R;
import com.dbeqiraj.guideapp.activity.DetailActivity;
import com.dbeqiraj.guideapp.activity.SpotsActivity;
import com.dbeqiraj.guideapp.model.Spot;
import com.dbeqiraj.guideapp.utilities.ImageHandler;
import com.dbeqiraj.guideapp.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {

    private SpotsActivity activity;
    private List<Spot> spots;
    Context ctx;


    public GridAdapter(SpotsActivity activity, List<Spot> spots) {
        this.activity = activity;
        this.spots = spots;
    }


    public void updateList(List<Spot> newList){
        spots=new ArrayList<>();
        spots.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return spots.size();
    }

    @Override
    public Object getItem(int i) {
        return spots.get(i);
    }




    @Override
    public long getItemId(int i) {
        return 0;
    }



    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if ( view == null )
            view = activity.getLayoutInflater().inflate(R.layout.spot_grid, viewGroup, false);
        ImageView spotImg     = (ImageView) view.findViewById(R.id.spot_image);
        TextView        spotName    = (TextView) view.findViewById(R.id.spot_name);
        TextView        spotDist    = (TextView) view.findViewById(R.id.spot_distance);
        TextView        spotRatings =(TextView ) view.findViewById(R.id.tvratingsSpot);
        LinearLayout    singleItem=(LinearLayout) view.findViewById(R.id.spotLayout);


        spots.get(i).setDistance(Utils.calculateDistance(spots.get(i).getLatitude(), spots.get(i).getLongitude(), activity.latitude, activity.longitude));


            Glide.with(activity).load(spots.get(i).getImg_url())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new ImageHandler(spotImg));
            spotName.setText(spots.get(i).getName());
            spotDist.setText(spots.get(i).getDistance()+"Km");

            if(spots.get(i).getRatings()!=0){
                spotRatings.setText(spots.get(i).getRatings()+" "+"Stars");
            }
            else {
                spotRatings.setVisibility(View.GONE);
            }


            /* Item Click Listener */

            singleItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, DetailActivity.class);
                    intent.putExtra("spot", spots.get(i));
                    activity.startActivity(intent);

                }

            });



        return view;

    }

}