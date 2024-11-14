package com.example.utsa_classroom_finder.model;
import java.io.Serializable;

public class UserClass implements Serializable {
    private String className;
    private String professor;
    private String schedule;  // Schedule could be a string, e.g., "Mon 9:00 AM - 10:30 AM"
    private String classNumber;  // New field for the class number (e.g., 1.2345)

    // Constructor now includes classNumber
    public UserClass(String className, String professor, String schedule, String classNumber) {
        this.className = className;
        this.professor = professor;
        this.schedule = schedule;
        this.classNumber = classNumber;  // Set the class number
    }

    // Getter and setter for classNumber
    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    // Other getters and setters
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "UserClass{" +
                "className='" + className + '\'' +
                ", professor='" + professor + '\'' +
                ", schedule='" + schedule + '\'' +
                ", classNumber='" + classNumber + '\'' +  // Display the class number
                '}';
    }
}

