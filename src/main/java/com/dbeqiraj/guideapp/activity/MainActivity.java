package com.dbeqiraj.guideapp.activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.dbeqiraj.guideapp.R;


public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout food,hotel,museum,attraction,atm,shopping,bank,busStop, mosque,petrolPump,hospital,viewAll;




    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        food=(LinearLayout) findViewById(R.id.ll_Food);
        hotel=(LinearLayout) findViewById(R.id.ll_Hotel);
        museum=(LinearLayout) findViewById(R.id.ll_Museum);
        attraction=(LinearLayout) findViewById(R.id.ll_Attraction);
        atm=(LinearLayout) findViewById(R.id.ll_ATM);
        shopping=(LinearLayout) findViewById(R.id.ll_Shopping);
        bank=(LinearLayout) findViewById(R.id.ll_Bank);
        busStop=(LinearLayout) findViewById(R.id.ll_BusStop);
        mosque =(LinearLayout) findViewById(R.id.ll_Mosque);
        petrolPump=(LinearLayout) findViewById(R.id.ll_PetrolPump);
        hospital=(LinearLayout) findViewById(R.id.ll_Hospital);
        viewAll=(LinearLayout) findViewById(R.id.ll_ViewAll);

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this, SpotsActivity.class);
                n.putExtra("title", "Food & Drink");
                n.putExtra("spot_name", "food");
                startActivity(n);
            }
        });

        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this, SpotsActivity.class);
                n.putExtra("title", "Hotels");
                n.putExtra("spot_name", "hotel");
                startActivity(n);
            }
        });

        museum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this, SpotsActivity.class);
                n.putExtra("title", "Museums");
                n.putExtra("spot_name", "museums");
                startActivity(n);
            }
        });
        attraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this, SpotsActivity.class);
                n.putExtra("title", "Attractions");
                n.putExtra("spot_name", "attractions");
                startActivity(n);
            }
        });

        atm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this, SpotsActivity.class);
                n.putExtra("title", "ATMs");
                n.putExtra("spot_name", "atm");
                startActivity(n);
            }
        });

        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this, SpotsActivity.class);
                n.putExtra("title", "Shoping Centers");
                n.putExtra("spot_name", "shopping");
                startActivity(n);
            }
        });

        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this, SpotsActivity.class);
                n.putExtra("title", "Banks");
                n.putExtra("spot_name", "bank");
                startActivity(n);
            }
        });
        busStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this, SpotsActivity.class);
                n.putExtra("title", "Bus Stops");
                n.putExtra("spot_name", "bus_stop");
                startActivity(n);
            }
        });
        mosque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this, SpotsActivity.class);
                n.putExtra("title", "Mosque");
                n.putExtra("spot_name", "mosque");
                startActivity(n);
            }
        });
        petrolPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this, SpotsActivity.class);
                n.putExtra("title", "Petrol Pumps");
                n.putExtra("spot_name", "petrol_pump");
                startActivity(n);
            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this, SpotsActivity.class);
                n.putExtra("title", "Hospitals");
                n.putExtra("spot_name", "hospital");
                startActivity(n);
            }
        });
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this, SpotsActivity.class);
                n.putExtra("title", "All Spots");
                n.putExtra("spot_name", "all");
                startActivity(n);
            }
        });






        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setTitle("Exit");
            alert.setMessage("Do You Realy Want To Exit");

            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();

                }
            });

            alert.show();

        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.nav_all_spots) {
            Intent n = new Intent(MainActivity.this, SpotsActivity.class);
            n.putExtra("title", "All Spots");
            n.putExtra("spot_name", "all");
            startActivity(n);

        } else if (id == R.id.nav_food) {
            Intent n = new Intent(MainActivity.this, SpotsActivity.class);
            n.putExtra("title", "Food & Drink");
            n.putExtra("spot_name", "food");
            startActivity(n);
        } else if (id == R.id.nav_hotel) {
            Intent n = new Intent(MainActivity.this, SpotsActivity.class);
            n.putExtra("title", "Hotels");
            n.putExtra("spot_name", "hotel");
            startActivity(n);
        } else if (id == R.id.nav_museums) {
            Intent n = new Intent(MainActivity.this, SpotsActivity.class);
            n.putExtra("title", "Museums");
            n.putExtra("spot_name", "museums");
            startActivity(n);
        } else if (id == R.id.nav_attractions) {
            Intent n = new Intent(MainActivity.this, SpotsActivity.class);
            n.putExtra("title", "Attractions");
            n.putExtra("spot_name", "attractions");
            startActivity(n);
        } else if (id == R.id.nav_atm) {
            Intent n = new Intent(MainActivity.this, SpotsActivity.class);
            n.putExtra("title", "ATMs");
            n.putExtra("spot_name", "atm");
            startActivity(n);
        }
        else if (id == R.id.nav_shopping) {
            Intent n = new Intent(MainActivity.this, SpotsActivity.class);
            n.putExtra("title", "Shoping Centers");
            n.putExtra("spot_name", "shopping");
            startActivity(n);
        }
        else if (id == R.id.nav_bank) {
            Intent n = new Intent(MainActivity.this, SpotsActivity.class);
            n.putExtra("title", "Banks");
            n.putExtra("spot_name", "bank");
            startActivity(n);
        }
        else if (id == R.id.nav_bus_stop) {
            Intent n = new Intent(MainActivity.this, SpotsActivity.class);
            n.putExtra("title", "Bus Stops");
            n.putExtra("spot_name", "bus_stop");
            startActivity(n);
        }
        else if (id == R.id.nav_mosque) {
            Intent n = new Intent(MainActivity.this, SpotsActivity.class);
            n.putExtra("title", "Mosque");
            n.putExtra("spot_name", "mosque");
            startActivity(n);
        }
        else if (id == R.id.nav_petrol_pump) {
            Intent n = new Intent(MainActivity.this, SpotsActivity.class);
            n.putExtra("title", "Petrol Pumps");
            n.putExtra("spot_name", "petrol_pump");
            startActivity(n);
        }
        else if (id == R.id.nav_hospital) {
            Intent n = new Intent(MainActivity.this, SpotsActivity.class);
            n.putExtra("title", "Hospitals");
            n.putExtra("spot_name", "hospital");
            startActivity(n);
        }




            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }


}