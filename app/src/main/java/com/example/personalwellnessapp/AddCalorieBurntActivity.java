package com.example.personalwellnessapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddCalorieBurntActivity extends AppCompatActivity {
    EditText foodBurntValue;
    Button backButton, addFoodBurntButton;
    String userName;
    DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calories_burnt);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        if(user!=null) {
            userName = user.getDisplayName();
        }
        foodBurntValue = findViewById(R.id.calories_burnt);
        addFoodBurntButton =  findViewById(R.id.add_calorie_burnt_button);
        backButton = findViewById(R.id.back_calorie_burnt_button);
        backButton.setOnClickListener(v -> onClickBackButton());
        addFoodBurntButton.setOnClickListener(v -> onClickAddWaterButton());
    }
    public void onClickAddWaterButton() {
        try {

            int foodBurnt = Integer.parseInt(foodBurntValue.getText().toString().trim());

            Calendar calendar = Calendar.getInstance();
            String year = Integer.toString(calendar.get(Calendar.YEAR));
            String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
            String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            String dateTime = year +"/"+month+"/"+day;
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("date", dateTime);
            userInfo.put("Calories", foodBurnt);

            databaseReference.child("/" + userName + "/caloriesBurnt").push().setValue(userInfo);
            Toast.makeText(getApplicationContext(), "Food burnt Added", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(AddCalorieBurntActivity.this, HomeActivity.class);
            startActivity(i);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Value must be Integer", Toast.LENGTH_LONG).show();

        }
    }
    public void onClickBackButton(){
        Intent i = new Intent(AddCalorieBurntActivity.this, HomeActivity.class);
        startActivity(i);
    }
}