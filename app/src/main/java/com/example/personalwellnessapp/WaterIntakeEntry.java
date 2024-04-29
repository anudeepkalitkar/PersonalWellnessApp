package com.example.personalwellnessapp;

public class WaterIntakeEntry {
    private String date;
    private int gallons;

    public WaterIntakeEntry() {
        // Default constructor required for Firebase
    }

    public WaterIntakeEntry(String date, int gallons) {
        this.date = date;
        this.gallons = gallons;
    }

    public String getDate() {
        return date;
    }

    public int getGallons() {
        return gallons;
    }


}
