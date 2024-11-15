
package com.example.utsa_classroom_finder.model;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utsa_classroom_finder.R;
import com.example.utsa_classroom_finder.model.UserClass;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<Building> userClassList;

    // Constructor to initialize the buildingList
    public ScheduleAdapter(List<Building> buildingList) {
        List<Building> validBuildings = new ArrayList<>();

        // Filter out invalid buildings (where userClasses is null or empty)
        for (Building building : buildingList) {
            if (building != null && building.getUserClasses() != null && !building.getUserClasses().isEmpty()) {
                validBuildings.add(building);
            }
        }

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

        // Initialize views
        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.className);
            buildingName = itemView.findViewById(R.id.buildingName);
            roomNumber = itemView.findViewById(R.id.roomNumber);
        }
    }
}

