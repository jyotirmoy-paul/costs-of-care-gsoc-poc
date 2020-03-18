package com.example.app.utils;

/*
* This Java Class is responsible for fetching the user's current location and storing all the hospitals within vicinity of 10 kms
* This class keeps a cached list of all hospitals which is refreshed as soon the user's location is changed within 500m
* This class provides, when required, all the hospitals name available from the cache
*
* FOR DEMONSTRATION PURPOSE
*
* For POC, I have hard coded 20 sample hospitals, and one real hospital, viz. Penn Presbyterian Medical Center
* This class will act as if those hospitals are available within 10 km radius
*/

import android.location.Location;

import com.example.app.models.HospitalLocationModel;

import java.util.ArrayList;
import java.util.Random;

public class NearbyHospitals {

    private ArrayList<HospitalLocationModel> nearbyHospitals;
    private int withinRadius;/* parameter to find hospitals withing given radius value in meter*/

    public NearbyHospitals(int withinRadius){
        nearbyHospitals = fetchNearbyHospitals(withinRadius);
        this.withinRadius = withinRadius;
    }

    public ArrayList<HospitalLocationModel> getHospitals(){

        if(hasLocationChanged()){
            nearbyHospitals = fetchNearbyHospitals(withinRadius);
        }

        return nearbyHospitals;
    }

    private boolean hasLocationChanged(){
        return false; /* FOR DEMONSTRATION PURPOSE, ALWAYS RETURN FALSE*/
    }

    private ArrayList<HospitalLocationModel> fetchNearbyHospitals(int radius){
        /*
        * This method actually fetches nearby hospitals based on location using Google Map Service
        * And stores them for later use
        *
        * The list is refreshed only when the user has moved by, let's say 2 km
        * the list is then updated and the saved
        * */

        /*
        * FOR DEMONSTRATION PURPOSE
        * the list is filled by randomly chosen 20 sample hospitals, as in the backend server
        * */

        ArrayList<HospitalLocationModel> list = new ArrayList<>();

        for(int i=0; i<Constants.NEARBY_HOSPITALS.length; i++){
            list.add(new HospitalLocationModel(
                    Constants.NEARBY_HOSPITALS[i],
                    null, /* storing the location object for future reference */
                    new Random().nextInt(10*1000) /* distance b/w 0 - 10,000 m*/
            ));
        }

        return list;

    }

}
