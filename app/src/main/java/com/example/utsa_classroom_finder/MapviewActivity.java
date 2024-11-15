package com.example.utsa_classroom_finder;

import static androidx.core.location.LocationManagerCompat.getCurrentLocation;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;

import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

public class MapviewActivity extends AppCompatActivity {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load configuration and initialize the map
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_mapview);
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        // Retrieve latitude and longitude from the intent
        double latitude = getIntent().getDoubleExtra("latitude", 0.0);
        double longitude = getIntent().getDoubleExtra("longitude", 0.0);

        // Center the map on the retrieved coordinates
        org.osmdroid.api.IMapController mapController = map.getController();
        mapController.setZoom(17);  // Zoom level; adjust as necessary
        mapController.setCenter(new org.osmdroid.util.GeoPoint(29.5856387, -98.6193095));  // Latitude and Longitude
        //combine lat and long into one string
        //String location = latitude + "," + longitude;

        fetchDirections("29.5856387, -98.6193095", "29.5842083, -98.6180271");

        //uncomment if not using physical device, the location would be google headquarters otherwise
        //mapController.setCenter(new org.osmdroid.util.GeoPoint(latitude, longitude));
        map.setMultiTouchControls(true);
    }


    @Override
    public void onResume() {
        super.onResume();
        map.onResume(); // needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause(); // needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permissions[i]);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    private void fetchDirections(String origin, String destination) {
        /*

         */

        String apiKey =""; // Replace with your Google API Key
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination=" + destination + "&key=" + apiKey;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    parseDirections(response);
                },
                error -> {
                    // Log the error
                    Log.e("Volley Error", error.toString());

                    // Show a Toast message to the user
                    Toast.makeText(getApplicationContext(), "Error fetching directions. Please try again.", Toast.LENGTH_LONG).show();
                });

        queue.add(stringRequest);
    }

    private void parseDirections(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray routes = jsonObject.getJSONArray("routes");
            if (routes.length() > 0) {
                JSONObject route = routes.getJSONObject(0);
                String polyline = route.getJSONObject("overview_polyline").getString("points");
                drawPolyline(polyline);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

            GeoPoint p = new GeoPoint((double) (lat / 1E5), (double) (lng / 1E5));
            poly.add(p);
        }
        //Log.d("Polyline", poly.toString());

        return poly;
    }
    public void drawPolyline(String encoded) {
        List<GeoPoint> points = decodePolyline(encoded);
        Polyline polyline = new Polyline();
        polyline.setPoints(points);
        polyline.setColor(Color.BLUE);
        polyline.setWidth(5f);
        map.getOverlayManager().add(polyline);
        map.invalidate();
    }


}


