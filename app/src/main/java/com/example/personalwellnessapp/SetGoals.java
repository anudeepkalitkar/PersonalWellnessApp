package com.example.personalwellnessapp;

public class SetGoals {
    private int calorieBurn;
    private int calorieIntake;
    private int waterIntake;
    private String date;

    public SetGoals() {
        // Default constructor required for Firebase
    }

    public SetGoals(int calorieBurn, int calorieIntake, String date, int waterIntake ) {
        // Default constructor required for Firebase
        this.calorieBurn = calorieBurn;
        this.calorieIntake = calorieIntake;
        this.waterIntake = waterIntake;
        this.date = date;

    }

    public int getCalorieBurn() {
        return calorieBurn;
    }



    public int getCalorieIntake() {
        return calorieIntake;
    }



    public int getWaterIntake() {
        return waterIntake;
    }


    public String getDate() {
        return date;
    }


}

