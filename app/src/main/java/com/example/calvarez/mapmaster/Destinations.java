package com.example.calvarez.mapmaster;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by calvarez on 11/17/2016.
 */
public class Destinations {

    private LatLng latLng;
    private String locationName;
    private int numericID;

    /**
     *
     * @param latLng
     *  Lat and Long of the locations GPS position
     * @param locationName
     *  String of the locations name
     * @param numericID
     *  This can literally be any number as long as it is unique
     */
    public Destinations(LatLng latLng, String locationName, int numericID){
        this.latLng = latLng;
        this.locationName = locationName;
        this.numericID = numericID;
    }

    public LatLng getLatLng(){
        return latLng;
    }

    public String getLocationName(){
        return locationName;
    }

    public int getNumericID(){
        return numericID;
    }

}
