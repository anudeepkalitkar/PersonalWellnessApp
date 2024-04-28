package com.example.personalwellnessapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddMoodActivity extends AppCompatActivity {
    Button backButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood);
        backButton = findViewById(R.id.back_add_mood_button);
        backButton.setOnClickListener(v -> onClickBackButton());
    }
    public void onClickBackButton(){
        Intent i = new Intent(AddMoodActivity.this, HomeActivity.class);
        startActivity(i);
    }
}