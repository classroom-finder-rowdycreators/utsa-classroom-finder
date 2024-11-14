package com.example.utsa_classroom_finder.model;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class Mapviewmodel {
    private final Context context;
    private final String apiKey = ""; // Replace with your Google API Key

    public Mapviewmodel(Context context) {
        this.context = context;
    }

    public interface DirectionsCallback {
        void onDirectionsFetched(List<GeoPoint> points);
    }

    public void fetchDirections(String origin, String destination, DirectionsCallback callback) {
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination=" + destination + "&key=" + apiKey;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> callback.onDirectionsFetched(parseDirections(response)),
                error -> {
                    Log.e("Volley Error", error.toString());
                    Toast.makeText(context, "Error fetching directions. Please try again.", Toast.LENGTH_LONG).show();
                });
        queue.add(stringRequest);
    }

    private List<GeoPoint> parseDirections(String response) {
        List<GeoPoint> points = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray routes = jsonObject.getJSONArray("routes");
            if (routes.length() > 0) {
                JSONObject route = routes.getJSONObject(0);
                String polyline = route.getJSONObject("overview_polyline").getString("points");
                points = decodePolyline(polyline);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return points;
    }

    private List<GeoPoint> decodePolyline(String encoded) {
        List<GeoPoint> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result >> 1) ^ -(result & 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result >> 1) ^ -(result & 1));
            lng += dlng;

            poly.add(new GeoPoint((double) (lat / 1E5), (double) (lng / 1E5)));
        }

        return poly;
    }
}

