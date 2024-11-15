package com.example.utsa_classroom_finder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.utsa_classroom_finder.model.Building;
import com.example.utsa_classroom_finder.model.BuildingDataManager;
import com.example.utsa_classroom_finder.model.SaveDataManager;
import com.example.utsa_classroom_finder.model.UserClass;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleUploaderActivity extends AppCompatActivity {

    private List<Building> buildings;
    private List<UserClass> userClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_uploader);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize building data for suggestions
        buildings = BuildingDataManager.loadBuildings(this);
        if (buildings == null) {
            buildings = new ArrayList<>();
        }
        /*
        buildings.add(new Building("NPB", "Main Campus", 4));
        buildings.add(new Building("FLN", "Main Campus", 4));
        buildings.add(new Building("JPL", "Main Campus", 4));
        buildings.add(new Building("BB", "Main Campus", 4));
        buildings.add(new Building("MB", "Main Campus", 4));
        buildings.add(new Building("MS", "Main Campus", 4));
        buildings.add(new Building("MH", "Main Campus", 4));
        buildings.add(new Building("ART", "Main Campus", 4));
*/
        // Buildings for suggestions
        String[] suggestions = {"MH", "NPB", "MS", "BB", "MB", "JPL", "FLN", "ART"};
        AutoCompleteTextView buildingName = findViewById(R.id.buildingName);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggestions);
        buildingName.setAdapter(adapter);
        buildingName.setThreshold(1);

        userClasses = new ArrayList<>();

        // UI elements
        EditText className = findViewById(R.id.className);
        EditText roomNumber = findViewById(R.id.roomNumber);
        Button submitSchedule = findViewById(R.id.submitSchedule);

        submitSchedule.setOnClickListener(e -> {
            // Get user inputs
            String classInput = className.getText().toString();
            String buildingInput = buildingName.getText().toString();
            String roomInput = roomNumber.getText().toString();
            Log.d("Debug", "Class Input: " + classInput);
            Log.d("Debug", "Building Input: " + buildingInput);
            Log.d("Debug", "Room Input: " + roomInput);

            if (!classInput.isEmpty() && !buildingInput.isEmpty() && !roomInput.isEmpty()) {
                // Create UserClass and add it to the list
                UserClass userClass = new UserClass(classInput, null, null, roomInput);
                userClasses.add(userClass);

                // Find or create the building
                Building selectedBuilding = null;
                for (Building b : buildings) {
                    if (b.getName().equalsIgnoreCase(buildingInput)) {
                        selectedBuilding = b;
                        break;
                    }
                }
                Log.d("Debug", "Buildings: " + buildings.toString());
                Log.d("Debug", "User Classes: " + userClasses.toString());
                //Log.d("Debug", "Selected Building: " + selectedBuilding.toString());

                if (selectedBuilding == null) {
                    selectedBuilding = new Building(buildingInput, "Main Campus", 4);
                    buildings.add(selectedBuilding);
                }
                selectedBuilding.getUserClasses().add(userClass);


                // Save the updated building data
                BuildingDataManager.saveBuildings(this, buildings);

                try {
                    SaveDataManager.saveBuildings(this,buildings);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                // Clear inputs
                className.setText("");
                buildingName.setText("");
                roomNumber.setText("");

                // Send data back to MainActivity
                //Intent resultIntent = new Intent();
                //resultIntent.putExtra("userClasses", new ArrayList<>(userClasses)); // Pass as Serializable
                //setResult(RESULT_OK, resultIntent);
                //finish();



                Log.d("Debug", "Schedule Submitted and Saved");
            } else {
                Log.d("Debug", "Please fill all fields.");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load existing buildings from storage if available
        List<Building> loadedBuildings = null;
        try {
            loadedBuildings = SaveDataManager.loadBuildings(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (loadedBuildings != null) {
            buildings = loadedBuildings;
            Log.d("Debug", "Buildings Loaded: " + buildings.toString());
        } else {
            Log.d("Debug", "No saved building data found.");
        }
    }
}
