package com.example.personalwellnessapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddCaloriesIntakeActivity extends AppCompatActivity {
    Button backButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calories_consumed);
        backButton = findViewById(R.id.back_calorie_intake_button);
        backButton.setOnClickListener(v -> onClickBackButton());
    }
    public void onClickBackButton(){
        Intent i = new Intent(AddCaloriesIntakeActivity.this, HomeActivity.class);
        startActivity(i);
    }
}