package com.example.personalwellnessapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {
    Button backButton, viewMoreButton;
    String userName;
    TextView testingDash;
    DatabaseReference databaseReference;

    ProgressBar waterProgress, calorieBurnProgress;
    Map<String, Integer> caloriesBurntData, waterIntakeData;


    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        caloriesBurntData = new LinkedHashMap<>();
        waterIntakeData = new LinkedHashMap<>();
        if (user != null) {
            userName = user.getDisplayName();
        }
        backButton = findViewById(R.id.back_dashboard_button);
        viewMoreButton = findViewById(R.id.view_more_button);
        testingDash = findViewById(R.id.detailed_view);
        waterProgress = findViewById(R.id.progressWater);
        calorieBurnProgress = findViewById(R.id.progressCaloriesBurned);
        backButton.setOnClickListener(v -> onClickBackButton());
        viewMoreButton.setOnClickListener( v-> onClickViewMoreButton());
        try{
            getCaloriesBurntFromFireBase(userName);
            getWaterIntakeFromFireBase(userName);
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickViewMoreButton() {

        if(testingDash.getVisibility() == TextView.GONE ) {
            testingDash.setVisibility(TextView.VISIBLE);
        }
        else{
            testingDash.setVisibility(TextView.GONE);
        }
    }

    public void onClickBackButton() {
        Intent i = new Intent(DashboardActivity.this, HomeActivity.class);
        startActivity(i);
    }

    public void getCaloriesBurntFromFireBase(String userName){
        databaseReference = FirebaseDatabase.getInstance().getReference("/" + userName + "/caloriesBurnt");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                caloriesBurntData.clear(); // Clear previous totals
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CaloriesBurntEntry entry = snapshot.getValue(CaloriesBurntEntry.class);
                    if (entry != null) {
                        String date = entry.getDate().split(" ")[0]; // Normalize the date
                        int calories = entry.getCalories();
                        if (caloriesBurntData.containsKey(date)) {
                            if (caloriesBurntData.get(date) != null) {
                                //noinspection DataFlowIssue
                                caloriesBurntData.put(date, caloriesBurntData.get(date) + calories);
                            }
                        }
                        else {
                            caloriesBurntData.put(date, calories);
                        }
                    }
                }
                getCaloriesBurntGoal(userName, caloriesBurntData);

            }
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("DBError", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public void getWaterIntakeFromFireBase(String userName){
        databaseReference = FirebaseDatabase.getInstance().getReference("/" + userName + "/waterIntake");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                waterIntakeData.clear(); // Clear previous totals
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    WaterIntakeEntry entry = snapshot.getValue(WaterIntakeEntry.class);
                    if (entry != null) {
                        String date = entry.getDate().split(" ")[0]; // Normalize the date
                        int gallons = entry.getGallons();

                        if (waterIntakeData.containsKey(date)) {
                            if (waterIntakeData.get(date) != null) {
                                //noinspection DataFlowIssue
                                waterIntakeData.put(date, waterIntakeData.get(date) + gallons);
                            }

                        }
                        else {
                            waterIntakeData.put(date, gallons);
                        }
                    }
                }
//
                getWaterIntakeGoal(userName, waterIntakeData);
            }
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("DBError", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public void getCaloriesBurntGoal( String userName, Map<String, Integer> caloriesBurntData){

        databaseReference = FirebaseDatabase.getInstance().getReference("/"+userName+"/setGoals");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int calorieBurnGoal = 0;
                if (dataSnapshot.exists()) {
                    SetGoals goals = dataSnapshot.getValue(SetGoals.class);
                    if (goals != null) {
                        calorieBurnGoal = goals.getCalorieBurn();
                        Log.d("FirebaseData", "getCalorieBurn: " + goals.getCalorieBurn());
                    }
                } else {
                    Log.d("FirebaseData", "User not found");
                }
               calculateCaloriesBurntPercentage(calorieBurnGoal, caloriesBurntData);
               }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("FirebaseData", "loadUser:onCancelled", databaseError.toException());
            }
        });
    }

    public void getWaterIntakeGoal( String userName, Map<String, Integer> waterIntakeData){

        databaseReference = FirebaseDatabase.getInstance().getReference("/"+userName+"/setGoals");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int waterIntakeGoal = 0;
                if (dataSnapshot.exists()) {
                    SetGoals goals = dataSnapshot.getValue(SetGoals.class);
                    if (goals != null) {
                        waterIntakeGoal = goals.getWaterIntake();
                        Log.d("FirebaseData", "getWaterIntake: " + goals.getWaterIntake());
                    }
                } else {
                    Log.d("FirebaseData", "User not found");
                }
                calculateWaterIntakePercentage(waterIntakeGoal, waterIntakeData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("FirebaseData", "loadUser:onCancelled", databaseError.toException());
            }
        });
    }


    @SuppressLint("SetTextI18n")
    public void calculateCaloriesBurntPercentage(int calorieBurnGoal, Map<String, Integer> caloriesBurntData) {
        Map.Entry<String, Integer> lastEntry = null;
        Calendar calendar = Calendar.getInstance();
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        String dateTime = year +"/"+month+"/"+day;
        int percentage = 0;
        int caloriesBurntToday = 0;
        for (Map.Entry<String, Integer> entry : caloriesBurntData.entrySet()) {
            lastEntry = entry;
            Log.d("DailyCalories", "Date: " + entry.getKey() + ", Calories: " + entry.getValue());

        }

        if (lastEntry != null) {
            if(lastEntry.getKey().equals(dateTime)) {
                caloriesBurntToday = lastEntry.getValue();
                percentage = (caloriesBurntToday* 100) / calorieBurnGoal;
            }
            calorieBurnProgress.setProgress(percentage);
            testingDash.setText("Detailed View: \n" + "calorie Burn Goal: "+Integer.toString(calorieBurnGoal) +
                    "\ncalories Burnt Today: "+ Integer.toString(caloriesBurntToday) +
                    "\npercentage: "+ Integer.toString(percentage));

        }
    }

    @SuppressLint("SetTextI18n")
    public void calculateWaterIntakePercentage(int waterIntakeGoal, Map<String, Integer> waterIntakeData) {
        Map.Entry<String, Integer> lastEntry = null;
        Calendar calendar = Calendar.getInstance();
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        String dateTime = year +"/"+month+"/"+day;
        int percentage = 0;
        int waterConsumedToday = 0;
        for (Map.Entry<String, Integer> entry : waterIntakeData.entrySet()) {
            lastEntry = entry;
            Log.d("DailyWaterIntake", "Date: " + entry.getKey() + ", Gallons: " + entry.getValue());

        }

        if (lastEntry != null) {
            if(lastEntry.getKey().equals(dateTime)) {
                waterConsumedToday = lastEntry.getValue();
                percentage = (waterConsumedToday* 100) / waterIntakeGoal ;
            }
            waterProgress.setProgress(percentage,true);
            String testingDashData = testingDash.getText().toString();
            testingDash.setText(testingDashData + "\nwater Intake Goal: "+Integer.toString(waterIntakeGoal) +
                    "\nwater Consumed Today: "+ Integer.toString(waterConsumedToday) +
                    "\npercentage: "+ Integer.toString(percentage));

        }
    }
}










// for (Map.Entry<String, Integer> entry : caloriesBurntData.entrySet()) {
//                    Log.d("DailyWaterIntake", "Date: " + entry.getKey() + ", Calories: " + entry.getValue());
//                    String testingDashData = testingDash.getText().toString();
//                    testingDash.setText(testingDashData + "\n Date: " + entry.getKey() + ", Calories: " + entry.getValue());
//
//                }
//                String testingDashData = testingDash.getText().toString();
//                testingDash.setText(testingDashData + "\n calorieBurnGoal: " + Integer.toString(calorieBurnGoal));
//



