package com.example.utsa_classroom_finder.model;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BuildingDataManager {

    private static final String FILE_NAME = "buildings_data.ser";

    // Save the list of buildings to a file
    public static void saveBuildings(Context context, List<Building> buildings) {
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(buildings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the list of buildings from the file
    public static List<Building> loadBuildings(Context context) {
        List<Building> buildings = null;
        File file = new File(context.getFilesDir(), FILE_NAME);

        if (file.exists()) {
            try (FileInputStream fis = context.openFileInput(FILE_NAME);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                buildings = (List<Building>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            // If the file does not exist, return an empty list or handle as necessary
            buildings = new ArrayList<>(); // Or you can return null if that's preferred
        }
        return buildings;
    }
}
