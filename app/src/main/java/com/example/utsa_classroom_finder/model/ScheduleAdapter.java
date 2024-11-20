
package com.example.utsa_classroom_finder.model;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utsa_classroom_finder.MapviewActivity;
import com.example.utsa_classroom_finder.R;
import com.example.utsa_classroom_finder.model.UserClass;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<Building> userClassList;
    //intent
    private Context context;
    private double curlatitude, curlongitude;

    // Constructor to initialize the buildingList
    public ScheduleAdapter(List<Building> buildingList,Context intent, double latitude, double longitude) {
        List<Building> validBuildings = new ArrayList<>();

        // Filter out invalid buildings (where userClasses is null or empty)
        for (Building building : buildingList) {
            if (building != null && building.getUserClasses() != null && !building.getUserClasses().isEmpty()) {
                validBuildings.add(building);
            }
        }
        this.context = intent;
        this.curlatitude = longitude;
        this.curlongitude = latitude;


        // Log the valid list
        Log.d("ScheduleAdapter", "Valid list: " + validBuildings.toString());
        this.userClassList = validBuildings;
    }

    // ViewHolder to hold references to the layout views
    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    // Bind the data to the views in each item
    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        Building building = userClassList.get(position);  // Get the Building object at this position

        // Ensure that 'userClasses' inside the Building object is not null and not empty
        if (building != null && building.getUserClasses() != null && !building.getUserClasses().isEmpty()) {
            // Assuming you want to display the first class in the list
            UserClass userClass = building.getUserClasses().get(0);  // Get the first UserClass (or implement logic for multiple classes)

            // Set the UI components
            holder.buildingName.setText(building.getName());
            holder.roomNumber.setText(userClass.getClassNumber());
            holder.className.setText(userClass.getClassName());  // Set the class name here
            holder.button.setOnClickListener(view -> {
                // Handle button click here
                /*
                locations.put("MH", new double[]{29.5847331, -98.6189427});
                locations.put("NPB", new double[]{29.5856387, -98.6193095});
                locations.put("MS", new double[]{29.5835849, -98.6188163});
                locations.put("BB", new double[]{29.5848882, -98.6185716});
                locations.put("MB", new double[]{29.5847246, -98.6168338});
                locations.put("JPL", new double[]{29.5842083, -98.6180271});
                locations.put("FLN", new double[]{29.5831102, -98.6185384});
                locations.put("ART", new double[]{29.5829717, -98.6175165});
                 */
                Log.d("Debug", "Button clicked");
                if(holder.buildingName.getText().toString().equals("MS")){
                    Log.d("Debug", "MS button clicked");
                    double latitude = 29.5835849;
                    double longitude = -98.6188163;
                    Log.d("Destination", "Destination Latitude: " + latitude + ", Longitude: " + longitude);
                    Log.d("Current", "Current Latitude: " + curlatitude + ", Longitude: " + curlongitude);
                    Intent intent = new Intent(context, MapviewActivity.class);
                    intent.putExtra("destinationLatitude", latitude);
                    intent.putExtra("destinationLongitude", longitude);
                    intent.putExtra("currentLatitude", curlatitude);
                    intent.putExtra("currentLongitude", curlongitude);
                    context.startActivity(intent);
                }
                else if(holder.buildingName.getText().toString().equals("BB")){
                    Log.d("Debug", "BB button clicked");
                    double latitude = 29.5848882;
                    double longitude = -98.6185716;
                    Log.d("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
                    Intent intent = new Intent(context, MapviewActivity.class);
                    intent.putExtra("destinationLatitude", latitude);
                    intent.putExtra("destinationLongitude", longitude);
                    intent.putExtra("currentLatitude", curlatitude);
                    intent.putExtra("currentLongitude", curlongitude);
                    context.startActivity(intent);

                }
                else if(holder.buildingName.getText().toString().equals("MB")){
                    Log.d("Debug", "MB button clicked");
                    double latitude = 29.5847246;
                    double longitude = -98.6168338;
                    Log.d("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
                    Intent intent = new Intent(context, MapviewActivity.class);
                    intent.putExtra("destinationLatitude", latitude);
                    intent.putExtra("destinationLongitude", longitude);
                    intent.putExtra("currentLatitude", curlatitude);
                    intent.putExtra("currentLongitude", curlongitude);
                    context.startActivity(intent);
                }
                else if(holder.buildingName.getText().toString().equals("JPL")){
                    Log.d("Debug", "JPL button clicked");
                    double latitude = 29.5842083;
                    double longitude = -98.6180271;
                    Log.d("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
                    Intent intent = new Intent(context, MapviewActivity.class);
                    intent.putExtra("destinationLatitude", latitude);
                    intent.putExtra("destinationLongitude", longitude);
                    intent.putExtra("currentLatitude", curlatitude);
                    intent.putExtra("currentLongitude", curlongitude);
                    context.startActivity(intent);
                }
                else if (holder.buildingName.getText().toString().equals("FLN")) {
                    Log.d("Debug", "FLN button clicked");
                    double latitude = 29.5831102;
                    double longitude = -98.6185384;
                    Log.d("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
                    Intent intent = new Intent(context, MapviewActivity.class);
                    intent.putExtra("destinationLatitude", latitude);
                    intent.putExtra("destinationLongitude", longitude);
                    intent.putExtra("currentLatitude", curlatitude);
                    intent.putExtra("currentLongitude", curlongitude);
                    context.startActivity(intent);
                }
                else if (holder.buildingName.getText().toString().equals("ART")) {
                    Log.d("Debug", "ART button clicked");
                    double latitude = 29.5829717;
                    double longitude = -98.6175165;
                    Log.d("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
                    Intent intent = new Intent(context, MapviewActivity.class);
                    intent.putExtra("destinationLatitude", latitude);
                    intent.putExtra("destinationLongitude", longitude);
                    intent.putExtra("currentLatitude", curlatitude);
                    intent.putExtra("currentLongitude", curlongitude);
                    context.startActivity(intent);
                }
                else if (holder.buildingName.getText().toString().equals("MH")) {
                    Log.d("Debug", "MH button clicked");
                    double latitude = 29.5847331;
                    double longitude = -98.6189427;
                    Log.d("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
                    Intent intent = new Intent(context, MapviewActivity.class);
                    intent.putExtra("destinationLatitude", latitude);
                    intent.putExtra("destinationLongitude", longitude);
                    intent.putExtra("currentLatitude", curlatitude);
                    intent.putExtra("currentLongitude", curlongitude);
                    context.startActivity(intent);
                }
                else if (holder.buildingName.getText().toString().equals("NPB")) {
                    Log.d("Debug", "NPB button clicked");
                    double latitude = 29.5856387;
                    double longitude = -98.6193095;
                    Log.d("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
                    Intent intent = new Intent(context, MapviewActivity.class);
                    intent.putExtra("destinationLatitude", latitude);
                    intent.putExtra("destinationLongitude", longitude);
                    intent.putExtra("currentLatitude", curlatitude);
                    intent.putExtra("currentLongitude", curlongitude);
                    context.startActivity(intent);
                }
                else {
                    Log.d("Debug", "No button clicked");
                }
            });
        } else {
            // Handle null or empty data gracefully
            Log.d("Debug", "No data available for this building.");
            holder.buildingName.setText("No data");
            holder.roomNumber.setText("N/A");
            holder.className.setText("N/A");  // Set the className as N/A if no data available
        }
    }

    // Get the total number of items
    @Override
    public int getItemCount() {
        return userClassList.size();
    }

    // Method to update the data in the adapter
    public void updateData(List<Building> newUserClassList) {
        // Clear and filter new list
        List<Building> validBuildings = new ArrayList<>();
        for (Building building : newUserClassList) {
            if (building != null && building.getUserClasses() != null && !building.getUserClasses().isEmpty()) {
                validBuildings.add(building);
            }
        }

        this.userClassList.clear();  // Clear the old list
        this.userClassList.addAll(validBuildings);  // Add the valid buildings to the list
        notifyDataSetChanged();  // Notify RecyclerView that the data has changed
    }

    // ViewHolder class to represent each item
    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView className, buildingName, roomNumber;
        Button button;

        // Initialize views
        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.className);
            buildingName = itemView.findViewById(R.id.buildingName);
            roomNumber = itemView.findViewById(R.id.roomNumber);
            button = itemView.findViewById(R.id.button2);
        }
    }
}

