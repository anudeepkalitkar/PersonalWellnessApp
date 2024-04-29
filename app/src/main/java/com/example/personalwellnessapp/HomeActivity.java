package com.example.personalwellnessapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalwellnessapp.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class HomeActivity extends AppCompatActivity {
    TextView userName;
    Button  dashboardBtn, addMoodBtn, addWaterBtn, addCalorieIntakeBtn, addCalorieBurntBtn, setGoalsBtn, signOutBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userName = findViewById(R.id.userNameTextView);
        dashboardBtn = findViewById(R.id.button_dashboard);
        addMoodBtn = findViewById(R.id.button_add_mood);
        addWaterBtn = findViewById(R.id.button_add_water);
        addCalorieIntakeBtn = findViewById(R.id.button_add_calorie_intake);
        addCalorieBurntBtn = findViewById(R.id.button_add_calorie_burnt);
        setGoalsBtn = findViewById(R.id.button_set_goals);
        signOutBtn = findViewById(R.id.signOutButton);
        dashboardBtn.setOnClickListener(v -> onClickDashboardButton());
        addMoodBtn.setOnClickListener(v -> onClickAddMoodButton());
        addWaterBtn.setOnClickListener(v -> onClickAddWaterButton());
        addCalorieIntakeBtn.setOnClickListener(v -> onClickAddCalorieIntakeButton());
        addCalorieBurntBtn.setOnClickListener(v -> onClickAddCalorieBurntButton());
        setGoalsBtn.setOnClickListener(v -> onClickSetGoalsButton());
        signOutBtn.setOnClickListener(v -> onClickSignOutButton());
        accessUserInformation();
    }
    public void onClickDashboardButton(){
        Intent i = new Intent(HomeActivity.this, DashboardActivity.class);
        startActivity(i);
    }
    public void onClickAddWaterButton(){
        Intent i = new Intent(HomeActivity.this, AddWaterActivity.class);
        startActivity(i);
    }
    public void onClickAddMoodButton(){
        Intent i = new Intent(HomeActivity.this, AddMoodActivity.class);
        startActivity(i);
    }

    public void onClickAddCalorieIntakeButton(){
        Intent i = new Intent(HomeActivity.this, AddCaloriesIntakeActivity.class);
        startActivity(i);
    }
    public void onClickAddCalorieBurntButton(){
        Intent i = new Intent(HomeActivity.this, AddCalorieBurntActivity.class);
        startActivity(i);
    }
    public void onClickSetGoalsButton(){
        Intent i = new Intent(HomeActivity.this, SetGoalsActivity.class);
        startActivity(i);
    }


    public void onClickSignOutButton(){
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
            Toast.makeText(getApplicationContext(), "Signing Out", Toast.LENGTH_SHORT).show();
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
    }

    public void accessUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            name = "Welcome  " + name;
            userName.setText(name);
        }


    }






}