package com.dbeqiraj.guideapp.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dbeqiraj.guideapp.R;
import com.dbeqiraj.guideapp.adapter.RecyclerViewAdapter;
import com.dbeqiraj.guideapp.model.Forecaste;
import com.dbeqiraj.guideapp.model.Spot;
import com.dbeqiraj.guideapp.utilities.ImageHandler;
import com.dbeqiraj.guideapp.utilities.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity
        implements OnMapReadyCallback, View.OnClickListener {

    private Spot spot;
    private TextView temprature,condition,Location;
    private RecyclerView recyclerView;
    private ArrayList<Forecaste> list;
    private Double lat;
    private Double lng;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_detail);

        if ( getSupportActionBar() != null ) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        list=new ArrayList<>();
        recyclerView=(RecyclerView) findViewById(R.id.rvForecast);
        temprature=(TextView) findViewById(R.id.tvTemprature);
        condition=(TextView) findViewById(R.id.tvCondition);
        Location=(TextView) findViewById(R.id.tvLocation);


        spot = (Spot) getIntent().getSerializableExtra("spot");
        Utils.setActionBarTitle(getSupportActionBar(), spot.getName());

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TextView shortDesc = (TextView) findViewById(R.id.short_dsc);
        TextView address = (TextView) findViewById(R.id.address);
        TextView distance = (TextView) findViewById(R.id.distance);
        TextView web = (TextView) findViewById(R.id.web);
        TextView phone = (TextView) findViewById(R.id.phone);
        TextView email = (TextView) findViewById(R.id.email);
        TextView hours = (TextView) findViewById(R.id.hours);
        TextView navigate = (TextView) findViewById(R.id.navigate);
        TextView longDesc = (TextView) findViewById(R.id.long_desc);
        ImageView spotImage=(ImageView) findViewById(R.id.spotDetailsImage);
        TextView ratings=(TextView) findViewById(R.id.ratings);
        TextView rent=(TextView) findViewById(R.id.rent);

        Picasso.get().load(spot.getImg_url()).into(spotImage);
        spotImage.setScaleType(ImageView.ScaleType.FIT_XY);
        if(spot.getRatings()==0){
            ratings.setVisibility(View.GONE);
        }
        else {
            ratings.setText(""+spot.getRatings()+" "+"Star");
        }

        if(spot.getRent()==0){
            rent.setVisibility(View.GONE);
        }
        else {
            rent.setText(""+spot.getRent()+" "+"Per Person");
        }

        shortDesc.setText(spot.getShort_dsc());
        address.setText(spot.getAddress());
        distance.setText(spot.getDistance()+"Km");
        if(!spot.getSite().equals("")){
            web.setText(spot.getSite());
        }
        else {
            web.setVisibility(View.GONE);
        }

        phone.setText(spot.getPhone());

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0377778888"));

                if (ActivityCompat.checkSelfPermission(DetailActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);

            }
        });


        email.setText(spot.getEmail());
        hours.setText(spot.getHours());
        longDesc.setText(spot.getLong_desc());
        lat=spot.getLatitude();
        lng=spot.getLongitude();

        weatherRequest();
        navigate.setOnClickListener(this);
    }

    private void weatherRequest() {


        url="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(SELECT%20woeid%20FROM%20geo.places%20WHERE%20text%3D%22("+lat+"%2C%20+"+lng+")%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

        JsonObjectRequest request=new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {



                try {
                    JSONObject query=response.getJSONObject("query");

                    JSONObject results=query.getJSONObject("results");
                    JSONObject channel=results.getJSONObject("channel");
                    JSONObject units=channel.getJSONObject("units");
                    String tempUnit=units.getString("temperature");

                    JSONObject location=channel.getJSONObject("location");
                    String city=location.getString("city");
                    String country=location.getString("country");

                    JSONObject image=channel.getJSONObject("image");
                    String imageurl=image.getString("url");

                    JSONObject item=channel.getJSONObject("item");
                    JSONObject Condition=item.getJSONObject("condition");
                    int temp=Condition.getInt("temp");
                    String conditionText=Condition.getString("text");

                    JSONArray forecast=item.getJSONArray("forecast");

                    JSONObject data;
                    for (int i=0;i<forecast.length();i++){
                        data=forecast.getJSONObject(i);
                        Forecaste forecaste=new Forecaste();
                        forecaste.setDate(data.getString("date"));
                        forecaste.setDay(data.getString("day"));
                        /*forecaste.setHigh((int) ((data.getInt("high")-32)*0.5556));
                        forecaste.setLow((int) ((data.getInt("low")-32)*0.5556));*/

                        forecaste.setHigh(Integer.parseInt(data.getString("high")));
                        forecaste.setLow(Integer.parseInt(data.getString("low")));
                        forecaste.setText(data.getString("text"));

                        list.add(forecaste);


                    }


                    int tempCentigrade= (int) ((temp-32)*0.5556);

                    Location.setText(city+" "+","+country);
                    temprature.setText(tempCentigrade+"°C");
                    condition.setText(""+conditionText);



                } catch (JSONException e) {

                    Toast.makeText(DetailActivity.this,""+e,Toast.LENGTH_LONG).show();

                }

                // Setting up the recycler view
                RecyclerViewAdapter adapter=new RecyclerViewAdapter(DetailActivity.this,list);
                recyclerView.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
                recyclerView.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(DetailActivity.this,"Error",Toast.LENGTH_LONG).show();
                Toast.makeText(DetailActivity.this,""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(DetailActivity.this);
        requestQueue.add(request);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng marker = new LatLng(spot.getLatitude(), spot.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(marker).title(spot.getName()));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 13));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId() ) {
            case R.id.navigate:
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=17&q=%f,%f", spot.getLatitude(),spot.getLongitude(),spot.getLatitude(),spot.getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                break;
        }
    }
}
