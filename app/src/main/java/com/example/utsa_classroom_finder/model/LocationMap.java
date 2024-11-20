package com.example.utsa_classroom_finder.model;

import java.util.HashMap;
import java.util.Map;

public class LocationMap {
    // A map to store the location coordinates
    private static final Map<String, double[]> locations = new HashMap<>();

    // Static block to initialize the location data
    static {
        //more npb:(29.5856387, -98.6193095)(29°35'09"N 98°37'11"W),(29°35'10"N 98°37'08"W),(29°35'08"N 98°37'09"W),(29°35'09"N 98°37'09"W)
        locations.put("MH", new double[]{29.5847331, -98.6189427});
        locations.put("NPB", new double[]{29.5856387, -98.6193095});
        locations.put("MS", new double[]{29.5835849, -98.6188163});
        locations.put("BB", new double[]{29.5848882, -98.6185716});
        locations.put("MB", new double[]{29.5847246, -98.6168338});
        locations.put("JPL", new double[]{29.5842083, -98.6180271});
        locations.put("FLN", new double[]{29.5831102, -98.6185384});
        locations.put("ART", new double[]{29.5829717, -98.6175165});
    }
    //static to and from map


    // Method to get coordinates based on location key
    public static double[] getCoordinates(String locationKey) {
        return locations.get(locationKey);
    }

    //combo of all the locations, BB to NPB , etc

}


