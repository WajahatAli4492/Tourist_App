package com.dbeqiraj.guideapp.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.dbeqiraj.guideapp.R;
import com.dbeqiraj.guideapp.activity.MainActivity;
import com.dbeqiraj.guideapp.activity.SpotsActivity;
import com.dbeqiraj.guideapp.model.Spot;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;

public class Utils {

    public static void setActionBarTitle(ActionBar actionBar, String title){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if ( actionBar != null ){
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setTitle(title);
            }
        }
    }

    public static class GetData extends AsyncTask< Void, Void, Void> {

        Dialog          dialog;
        SpotsActivity activity;
        boolean         timeout;
        Call<List<Spot>> call;

        public GetData(SpotsActivity activity, Call<List<Spot>> call) {
            this.activity     = activity;
            this.call     = call;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = showProgressDialog(activity, activity.getString(R.string.please_wait));
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            timeout             =   true;
            long    start       =   System.currentTimeMillis();
            CallWS.getSpots(activity, call);
            while( System.currentTimeMillis() - start < 15  *   1000 ){
                if ( CallWS.listOfSpots != null || CallWS.respondStatus != 0 ) {
                    timeout     =   false;
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if ( timeout ) {
                Utils.showSimpleDialog(activity, activity.getString(R.string.app_name), activity.getString(R.string.connection_error), null);
            } else if ( CallWS.listOfSpots != null && CallWS.respondStatus != 0 ){
                try{
                    activity.populateWithSpots(CallWS.listOfSpots);
                    activity.SpotsActivity(CallWS.listOfSpots);

                } catch ( Exception e ){
                    e.printStackTrace();
                }
            } else {
                Utils.showSimpleDialog(activity, activity.getString(R.string.app_name), activity.getString(R.string.no_data), null);
            }
        }
    }

    static boolean isNetworkOK;
    private static NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
    public static void createConnectivityListener(final SpotsActivity thisActivity) {

        networkStateReceiver.connected  =   isNetworkOK;

        networkStateReceiver.setListener(new NetworkStateReceiver.NetworkStateReceiverListener() {
            @Override
            public void networkAvailable() {
                isNetworkOK =   true;
                Log.d("NETWORK CHANGE", "THERE IS INTERNET CONNECTION");
            }

            @Override
            public void networkUnavailable() {
                isNetworkOK =   false;
                Log.d("NETWORK CHANGE", "THERE IS NO INTERNET CONNECTION");
            }
        });
        thisActivity.registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    static void showSimpleDialog(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", listener);
        builder.show();
    }

    private static Dialog showProgressDialog(Context context, String message) {
        ProgressDialog alertDialog = new ProgressDialog(context);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        return alertDialog;
    }

   /* public static String calculateDistance( double lat1, double lon1, double lat2, double lon2 ){
        double theta = lon1 - lon2;
        double distance = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));

        distance = Math.acos(distance);
        distance = Math.toDegrees(distance);
        distance = distance * 60 * 1.1515 * 1.609344;

        return  new DecimalFormat("####0.00").format(distance) + " Km";
    }*/

    public static String calculateDistance( double lat1, double lon1, double lat2, double lon2 ){
        LatLng from = new LatLng(lat1,lon1);
        LatLng to = new LatLng(lat2,lon2);

        Double distance= SphericalUtil.computeDistanceBetween(from,to);
        //Double distance = SphericalUtil.computeDistanceBetween(from, to);

        return  new DecimalFormat("####0.00").format(distance/1000);
    }


   /* public static String calculateDistance( double lat1, double lon1, double lat2, double lon2 ){
        double theta = lon1 - lon2;
        double distance = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        double rlat1 = Math.PI * lat1/180.0f;
        double rlat2 = Math.PI * lat2/180.0f;
        double rlon1 = Math.PI * lon1/180.0f;
        double rlon2 = Math.PI * lon2/180.0f;

        theta = lon1-lon2;
        double rtheta = Math.PI * theta/180.0f;

        double dist = Math.sin(rlat1) * Math.sin(rlat2) + Math.cos(rlat1) *     Math.cos(rlat2) * Math.cos(rtheta);
        dist = Math.acos(dist);
        dist = dist * 180.0f/Math.PI;
        dist = dist * 60.0f * 1.1515f;
        return  new DecimalFormat("####0.00").format(dist) + " Km";

    }*/

    /*public static String calculateDistance( double lat1, double lon1, double lat2, double lon2 ){
        double earthRadius = 6371; //K meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return  new DecimalFormat("####0.00").format(dist) + " Km";
    }*/

   /* public static String calculateDistance( double lat1, double lon1, double lat2, double lon2 ){

        float distance[]= new float[10];
        Location.distanceBetween(lat1,lon1,lat2,lon2,distance);

        return String.format(String.valueOf(distance[0]/1000), "####0.00")+  "Km";
       *//* return  new DecimalFormat("####0.00").format(distance) + " Km";*//*
    }*/



    public static void restartApp(Activity activity){
        Intent intent = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
        Intent mainIntent = IntentCompat.makeRestartActivityTask(intent.getComponent());
        activity.startActivity(mainIntent);
        System.exit(0);
    }
}
