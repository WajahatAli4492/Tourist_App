package com.dbeqiraj.guideapp.http_rest_utils;


import com.dbeqiraj.guideapp.model.Spot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIPlug {
    @GET("get_all.php")
    Call<List<Spot>> getAll();

    @GET("get_food_and_drinks.php")
    Call<List<Spot>> getFoodAndDrinks();

    @GET("get_hotels.php")
    Call<List<Spot>> getHotels();

    @GET("get_museums.php")
    Call<List<Spot>> getMuseums();

    @GET("get_attractions.php")
    Call<List<Spot>> getAttractions();

    @GET("get_atm.php")
    Call<List<Spot>> getATM();

    @GET("get_shopping.php")
    Call<List<Spot>> getShopping();

    @GET("get_bank.php")
    Call<List<Spot>> getBank();

    @GET("get_bus_stop.php")
    Call<List<Spot>> getBusStop();

    @GET("get_mosque.php")
    Call<List<Spot>> getMosque();

    @GET("get_petrol_pump.php")
    Call<List<Spot>> getPetrolPump();

    @GET("get_hospital.php")
    Call<List<Spot>> getHospital();
}
