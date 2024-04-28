package com.example.personalwellnessapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SetGoalsActivity extends AppCompatActivity {
    Button backButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals);
//        backButton = findViewById(R.id.back);
//        backButton.setOnClickListener(v -> onClickBackButton());
    }
    public void onClickBackButton(){
        Intent i = new Intent(SetGoalsActivity.this, HomeActivity.class);
        startActivity(i);
    }
}