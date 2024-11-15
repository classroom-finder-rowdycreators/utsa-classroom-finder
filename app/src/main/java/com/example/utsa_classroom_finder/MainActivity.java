package com.example.utsa_classroom_finder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utsa_classroom_finder.model.Building;
import com.example.utsa_classroom_finder.model.BuildingDataManager;
import com.example.utsa_classroom_finder.model.SaveDataManager;
import com.example.utsa_classroom_finder.model.ScheduleAdapter;
import com.example.utsa_classroom_finder.model.UserClass;
import com.example.utsa_classroom_finder.model.UserClassDataManager;
import com.example.utsa_classroom_finder.model.checkLogin;
import com.google.android.gms.location.FusedLocationProviderClient;
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

        // Request location permission and get the current location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            fetchCurrentLocation();
        }

        // Handle window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up the button to navigate to the MapviewActivity
        Button nextscreen = findViewById(R.id.button);
        nextscreen.setOnClickListener(e -> {
            Intent intent = new Intent(this, MapviewActivity.class);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            startActivity(intent);
        });

        Button back = findViewById(R.id.back);
        back.setOnClickListener(
                e -> {
                    //send intent tezxt to mapview with location name equal to MH
                    String locationName = "MH";
                    checkLogin.setLoggedIn(this, false);
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                });

        Button addNewSchedule = findViewById(R.id.addNewSchedule);
        addNewSchedule.setOnClickListener(
                e -> {

                    Intent intent2 = new Intent(this, ScheduleUploaderActivity.class);
                    startActivity(intent2);
                }
        );
        //load data from file
        userClasses = UserClassDataManager.loadUserClasses(this);
        if (userClasses == null) {
            userClasses = new ArrayList<>();
        }
        buildingList = BuildingDataManager.loadBuildings(this);
        if (buildingList == null) {
            buildingList = new ArrayList<>();
        }

        try {
            buildings = SaveDataManager.loadBuildings(this);
            if (buildings == null) {
                buildings = new ArrayList<>();
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduleAdapter = new ScheduleAdapter(buildings);
        recyclerView.setAdapter(scheduleAdapter);




    }

    private void fetchCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("Location", "Location permission not granted.");
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.d("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
            } else {
                Log.e("Location", "Failed to retrieve location.");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchCurrentLocation(); // Fetch location after permission granted
            } else {
                Log.e("Location", "Permission denied.");
            }
        }
    }


}
