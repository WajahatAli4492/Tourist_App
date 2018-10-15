package com.dbeqiraj.guideapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import com.dbeqiraj.guideapp.R;
import com.dbeqiraj.guideapp.adapter.GridAdapter;
import com.dbeqiraj.guideapp.http_rest_utils.ApiClient;
import com.dbeqiraj.guideapp.model.Spot;
import com.dbeqiraj.guideapp.utilities.GPSTracker;
import com.dbeqiraj.guideapp.utilities.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;

public class SpotsActivity extends AppCompatActivity {



    private GridView gridView;
    public double longitude = 0 ;
    public double latitude = 0;
    String spotName;
    private static GridAdapter adapter;
    List<Spot> spotList;
    Spot spot;
    MenuItem searchViewItem;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spots);

        gridView = (GridView) findViewById(R.id.grid_view);




        Intent n=getIntent();
        spotName=n.getStringExtra("spot_name");

        Utils.setActionBarTitle(getSupportActionBar(),n.getStringExtra("title"));
        if(spotName.equals("all")){
            Utils.createConnectivityListener(SpotsActivity.this);
            getLocationFillList(ApiClient.getClient().getAll());
        }
        else if (spotName.equals("food")) {
            Utils.createConnectivityListener(SpotsActivity.this);
            getLocationFillList(ApiClient.getClient().getFoodAndDrinks());
        }
        else if (spotName.equals("hotel")) {
            Utils.createConnectivityListener(SpotsActivity.this);
            getLocationFillList(ApiClient.getClient().getHotels());
        }
        else if (spotName.equals("museums")) {
            Utils.createConnectivityListener(SpotsActivity.this);
            getLocationFillList(ApiClient.getClient().getMuseums());

        }
        else if (spotName.equals("attractions")) {
            Utils.createConnectivityListener(SpotsActivity.this);
            getLocationFillList(ApiClient.getClient().getAttractions());
        }
        else if (spotName.equals("atm")) {
            Utils.createConnectivityListener(SpotsActivity.this);
            getLocationFillList(ApiClient.getClient().getATM());
        }
        else if (spotName.equals("shopping")) {
            Utils.createConnectivityListener(SpotsActivity.this);
            getLocationFillList(ApiClient.getClient().getShopping());
        }
        else if (spotName.equals("bank")) {
            Utils.createConnectivityListener(SpotsActivity.this);
            getLocationFillList(ApiClient.getClient().getBank());
        }
        else if (spotName.equals("bus_stop")) {
            Utils.createConnectivityListener(SpotsActivity.this);
            getLocationFillList(ApiClient.getClient().getBusStop());
        }
        else if (spotName.equals("mosque")) {
            Utils.createConnectivityListener(SpotsActivity.this);
            getLocationFillList(ApiClient.getClient().getMosque());
        }
        else if (spotName.equals("petrol_pump")) {
            Utils.createConnectivityListener(SpotsActivity.this);
            getLocationFillList(ApiClient.getClient().getPetrolPump());
        }
        else if (spotName.equals("hospital")) {
            Utils.createConnectivityListener(SpotsActivity.this);
            getLocationFillList(ApiClient.getClient().getHospital());
        }
    }










    public void getLocationFillList(final Call<List<Spot>> call) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GPSTracker gps = new GPSTracker(SpotsActivity.this);
                if ( gps.canGetLocation() ) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    gps.stopUsingGPS();
                    new Utils.GetData(SpotsActivity.this, call).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);




                    } else {
                    Toast.makeText(SpotsActivity.this, getString(R.string.cannot_continue), Toast.LENGTH_SHORT).show();

                }
            }
        }, 1000);
    }


    public void SpotsActivity(List<Spot> listOfSpots) {

        spotList=listOfSpots;

        for(int i=1;i<spotList.size();i++) {
            spot.setDistance(Utils.calculateDistance(spotList.get(i).getLatitude(), spotList.get(i).getLongitude(), latitude, longitude));
        }
    }



    public void populateWithSpots(final List<Spot> spots) {
        gridView.setAdapter(null);
        // sort by 5 to lower ratings
        Collections.sort(spots,Spot.SORT_BY_RATINGS);
        adapter = new GridAdapter(this, spots);
        gridView.setAdapter(adapter);



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.spotmenu, menu);
        searchViewItem = menu.findItem(R.id.action_Search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        searchViewAndroidActionBar.setQueryHint("Search By Name Or Rent");

        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                String userInput=newText;
                List<Spot> newList= new ArrayList<>();

                boolean digitsOnly = TextUtils.isDigitsOnly(userInput);

                if(!userInput.equals("")) {


                    if (digitsOnly == true) {
                        for (Spot spots : spotList) {
                            if (spots.getRent() <= Integer.parseInt(userInput)) {
                                newList.add(spots);
                            }
                        }
                    } else {
                        for (Spot spots : spotList) {
                            if (spots.getName().toLowerCase().contains(userInput.toLowerCase())) {
                                newList.add(spots);
                            }
                        }
                    }
                    adapter = new GridAdapter(SpotsActivity.this, newList);
                    gridView.setAdapter(adapter);
                }else {
                    adapter = new GridAdapter(SpotsActivity.this, spotList);
                    gridView.setAdapter(adapter);
                }


                return false;
            }
        });



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_sortByRent:
                Collections.sort(spotList,Spot.SORT_BY_RENT);
                adapter = new GridAdapter(this, spotList);
                gridView.setAdapter(adapter);

                Toast.makeText(SpotsActivity.this,"sorted by Rent",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_sortByRating:
                Collections.sort(spotList,Spot.SORT_BY_RATINGS);
                adapter = new GridAdapter(this, spotList);
                gridView.setAdapter(adapter);

                Toast.makeText(SpotsActivity.this,"sorted by rating",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_sortByAlphabet:
                Collections.sort(spotList,Spot.SORT_BY_ALPHABET);
                adapter = new GridAdapter(this, spotList);
                gridView.setAdapter(adapter);
                Toast.makeText(SpotsActivity.this,"sorted by alphabet",Toast.LENGTH_SHORT).show();
                return true;




            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
