package com.example.utsa_classroom_finder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utsa_classroom_finder.model.Building;
import com.example.utsa_classroom_finder.model.SaveDataManager;
import com.example.utsa_classroom_finder.model.ScheduleAdapter;
import com.example.utsa_classroom_finder.model.UserClass;
import com.example.utsa_classroom_finder.model.checkLogin;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private List<UserClass> userClasses = new ArrayList<>(); // List to store schedule data
    private List<Building> buildingList = new ArrayList<>(); // List to store building data
    private List<Building> buildings = new ArrayList<>();
    private ScheduleAdapter scheduleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is logged in
        if (!checkLogin.isLoggedIn(this)) {
            // User is not logged in, start LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize the FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Show loading spinner until location is fetched
        ProgressBar loadingSpinner = findViewById(R.id.loading_spinner);
        loadingSpinner.setVisibility(View.VISIBLE); // Show spinner

        // Request location permission and get the current location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            requestLocationUpdates();
        }

        // Handle window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        /*
        // Set up the button to navigate to the MapviewActivity
        Button nextscreen = findViewById(R.id.button);
        nextscreen.setOnClickListener(e -> {
            Log.d("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
            Intent intent = new Intent(this, MapviewActivity.class);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            startActivity(intent);
        });
        */

        Button settings = findViewById(R.id.Settings);
        settings.setOnClickListener(e -> {
            Intent intent1 = new Intent(this, Settings.class);
            startActivity(intent1);
        });

        Button addNewSchedule = findViewById(R.id.addNewSchedule);
        addNewSchedule.setOnClickListener(e -> {
            Intent intent2 = new Intent(this, ScheduleUploaderActivity.class);
            startActivity(intent2);
        });

        // Load data from file
        try {
            buildings = SaveDataManager.loadBuildings(this);
            if (buildings == null) {
                buildings = new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Check if longitude and latitude are valid
        if (longitude == 0.0 || latitude == 0.0) {
            // Add a wait timer or retry logic to fetch valid coordinates
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                // Retry fetching coordinates or inform the user
                requestLocationUpdates();
                Log.e("Location", "Location coordinates not available.");
            }, 500); // Wait for 1 second
        } else {
            // Proceed only if longitude and latitude are valid
            initializeRecyclerView();
        }
    }

    // Request location updates
    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("Location", "Location permission not granted.");
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000); // 10 seconds
        locationRequest.setFastestInterval(5000); // 5 seconds
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // Use GPS

        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null && !locationResult.getLocations().isEmpty()) {
                    Location location = locationResult.getLocations().get(0); // Get the most recent location
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Log.d("Location", "Latitude: " + latitude + ", Longitude: " + longitude);

                    // Hide the loading spinner once the location is fetched
                    ProgressBar loadingSpinner = findViewById(R.id.loading_spinner);
                    loadingSpinner.setVisibility(View.GONE); // Hide spinner

                    // Initialize RecyclerView after location is updated
                    initializeRecyclerView();
                } else {
                    Log.e("Location", "Location result is empty.");
                }
            }
        }, Looper.getMainLooper());
    }

    // Method to initialize the RecyclerView
    private void initializeRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduleAdapter = new ScheduleAdapter(buildings, this, longitude, latitude);
        recyclerView.setAdapter(scheduleAdapter);
        Log.d("RecyclerView", "Initialized with updated location.");
    }

    // Handle the location permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocationUpdates(); // Fetch location after permission granted
            } else {
                Log.e("Location", "Permission denied.");
            }
        }
    }
}
