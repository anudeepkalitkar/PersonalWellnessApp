package com.example.personalwellnessapp;

public class CaloriesBurntEntry {
    private String date;
    private int Calories;

    public CaloriesBurntEntry() {
        // Default constructor required for Firebase
    }
    public CaloriesBurntEntry(String date, int Calories) {
        // Default constructor required for Firebase
        this.date= date;
        this.Calories = Calories;
    }

    public String getDate() {
        return date;
    }

    public int getCalories() {
        return Calories;
    }


}
