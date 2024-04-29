package com.example.personalwellnessapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddCaloriesIntakeActivity extends AppCompatActivity {
    Button backButton, addCalorieIntakeButton;
    EditText foodConsumedValue;
    RadioGroup selectMeal;
    RadioButton breakfastButton, lunchButton, dinnerButton;
    String userName;

    int selectMealValue;

    DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calories_consumed);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        if(user!=null) {
            userName = user.getDisplayName();
        }
        foodConsumedValue = findViewById(R.id.calories_consumed);
        addCalorieIntakeButton = findViewById(R.id.add_calorie_intake_button);
        backButton = findViewById(R.id.back_calorie_intake_button);

        selectMeal = findViewById(R.id.select_meal);
        breakfastButton = findViewById(R.id.breakfast);
        lunchButton = findViewById(R.id.lunch);
        dinnerButton = findViewById(R.id.dinner);
        addCalorieIntakeButton.setOnClickListener(v -> onClickAddCalorieIntakeButton());
        backButton.setOnClickListener(v -> onClickBackButton());

        selectMeal.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == breakfastButton.getId())
                selectMealValue = 1;
            else if (checkedId == lunchButton.getId())
                selectMealValue = 2;
            else if (checkedId == dinnerButton.getId())
                selectMealValue = 3;
        });
    }
    public void onClickAddCalorieIntakeButton(){
        int caloriesIntake = Integer.parseInt(foodConsumedValue.getText().toString().trim());

        Calendar calendar = Calendar.getInstance();
        long dateTime = calendar.getTimeInMillis();

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("date",dateTime );
        userInfo.put("Calories", caloriesIntake);
        userInfo.put("Meal", selectMealValue);

        databaseReference.child("/" + userName + "/caloriesIntake").push().setValue(userInfo);
        Toast.makeText(getApplicationContext(), "Food Consumed  Added", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(AddCaloriesIntakeActivity.this, HomeActivity.class);
        startActivity(i);
    }

    public void onClickBackButton(){
        Intent i = new Intent(AddCaloriesIntakeActivity.this, HomeActivity.class);
        startActivity(i);
    }
}