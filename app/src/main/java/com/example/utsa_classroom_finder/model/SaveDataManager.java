package com.example.utsa_classroom_finder.model;
import android.content.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveDataManager {

    private static final String SAVE_FILE_NAME = "buildings.dat";

    public static void saveBuildings(Context context, List<Building> buildings) throws IOException {
        try ( FileOutputStream fileOutputStream = context.openFileOutput(SAVE_FILE_NAME, Context.MODE_PRIVATE)){
            //FileOutputStream fileOutputStream = new FileOutputStream(SAVE_FILE_NAME);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(buildings);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Building> loadBuildings(Context context) throws IOException, ClassNotFoundException {
        File saveFile = new File(context.getFilesDir(), SAVE_FILE_NAME);
        if (!saveFile.exists()) {
            return new ArrayList<>();
        }

        FileInputStream fileInputStream = context.openFileInput(SAVE_FILE_NAME);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        @SuppressWarnings("unchecked")
        List<Building> buildings = (List<Building>) objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
        return buildings;
    }


}