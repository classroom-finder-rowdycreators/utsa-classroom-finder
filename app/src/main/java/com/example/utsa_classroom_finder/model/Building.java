package com.example.utsa_classroom_finder.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Building implements Serializable {
    private String name;
    private String location;
    private int floors;
    private List<UserClass> userClasses;  // List to store the classes for this building
//, List<UserClass> userClasses
    public Building(String name, String location, int floors) {
        this.name = name;
        this.location = location;
        this.floors = floors;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public List<UserClass> getUserClasses() {
        if (userClasses == null) {
            userClasses = new ArrayList<>();
        }
        return userClasses;
    }

    public void setUserClasses(List<UserClass> userClasses) {
        this.userClasses = userClasses;
    }

    @Override
    public String toString() {
        return "Building{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", floors=" + floors +
                '}';
    }


}
