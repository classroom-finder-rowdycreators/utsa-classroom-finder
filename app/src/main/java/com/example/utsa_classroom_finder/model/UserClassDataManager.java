package com.example.utsa_classroom_finder.model;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.List;

public class UserClassDataManager {

    // File name for saving UserClass data
    private static final String FILE_NAME = "user_classes_data.ser";

    // Save the list of UserClass objects to a file
    public static void saveUserClasses(Context context, List<UserClass> userClasses) {
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(userClasses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the list of UserClass objects from the file
    public static List<UserClass> loadUserClasses(Context context) {
        List<UserClass> userClasses = null;
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            userClasses = (List<UserClass>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userClasses;
    }
}
