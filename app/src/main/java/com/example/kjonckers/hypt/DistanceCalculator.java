package com.example.kjonckers.hypt;

import android.location.Location;
import android.util.Log;

import domain.GeoLocation;

/**
 * Created by kjonckers on 16/08/15.
 */
public class DistanceCalculator {
    public static double calc(GeoLocation locA, GeoLocation locB) {
        Location a = new Location("Point A");

        Location b = new Location("Point B");

        a.setLatitude(locA.getLatitude());
        a.setLongitude(locA.getLongitude());


        b.setLatitude(locB.getLatitude());
        b.setLongitude(locB.getLongitude());

        Log.i("distance between", "(" + locA.getLatitude() + "," + locA.getLongitude() + ")" + " en (" + locB.getLatitude() + "," + locB.getLongitude() + ")");
        return ((double)(int)(a.distanceTo(b)/100))/10;
        //for converting to x.x format

    }

}