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
import com.example.app.models.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NearbyHospitals {

    public static ArrayList<HospitalLocationModel> getHospitals(){

        /* choose between 5 to 11 hospitals at random */
        return fetchNearbyHospitals(5 + new Random().nextInt(7));
    }

    private static ArrayList<HospitalLocationModel> fetchNearbyHospitals(int n){
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

        List<String> l = Arrays.asList(Constants.NEARBY_HOSPITAL_NAME_LIST);
        Collections.shuffle(l);

        ArrayList<HospitalLocationModel> list = new ArrayList<>();

        Random r = new Random();
        double latUtil = -19.50139 + (64.85694 - 19.50139);
        double lngUtil = -161.75583 + (-68.01197 - 161.75583);

        for(int i=0; i<n; i++){

            double lat = latUtil * r.nextDouble();
            double lng = lngUtil * r.nextDouble();

            list.add(new HospitalLocationModel(
                    l.get(i),
                    new LatLng(lat, lng), /* storing the location object for future reference */
                    r.nextInt(10*1000) /* distance b/w 0 - 10,000 m*/
            ));
        }

        return list;

    }

}
