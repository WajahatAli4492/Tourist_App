package com.dbeqiraj.guideapp.model;

import android.widget.Toast;

import java.io.Serializable;
import java.util.Comparator;

public class Spot implements Serializable {

    private String name;
    private double latitude;
    private double longitude;
    private String img_url;
    private String short_dsc;
    private String address;
    private String phone;
    private String site;
    private String email;
    private String hours;
    private String long_desc;
    private String distance;
    private int ratings;
    private int rent;



    public static Comparator<Spot> SORT_BY_RATINGS = new Comparator<Spot>() {


        @Override
        public int compare(Spot spot, Spot t1) {

            // descending order
            Long value1 = new Long(spot.getRatings());
            Long value2 = new Long(t1.getRatings());
            return value2.compareTo(value1);

        }

    };








    public static Comparator<Spot> SORT_BY_RENT= new Comparator<Spot>() {


        @Override
        public int compare(Spot spot, Spot t1) {

            // Ascending order
            Long value1 = new Long(spot.getRent());
            Long value2 = new Long(t1.getRent());
            return value1.compareTo(value2);

        }

    };

    public static Comparator<Spot> SORT_BY_ALPHABET = new Comparator<Spot>() {


        @Override
        public int compare(Spot spot, Spot t1) {

            // Ascending order

            return spot.name.compareTo(t1.name);

        }

    };

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getShort_dsc() {
        return short_dsc;
    }

    public void setShort_dsc(String short_dsc) {
        this.short_dsc = short_dsc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getLong_desc() {
        return long_desc;
    }

    public void setLong_desc(String long_desc) {
        this.long_desc = long_desc;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
