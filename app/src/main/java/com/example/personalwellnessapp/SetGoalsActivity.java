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

public class SetGoalsActivity extends AppCompatActivity {

    EditText setWaterIntake, setCalorieIntake, setCalorieBurn;
    Button backButton, setGoalsButton;
    String userName;
    DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        if(user!=null) {
            userName = user.getDisplayName();
        }

        setWaterIntake = findViewById(R.id.setWaterGoal);
        setCalorieIntake = findViewById(R.id.setCaloriesIntakeGoal);
        setCalorieBurn = findViewById(R.id.setCaloriesBurnGoal);
        backButton = findViewById(R.id.back_set_goals_button);
        setGoalsButton = findViewById(R.id.set_goals);
        backButton.setOnClickListener(v -> onClickBackButton());
        setGoalsButton.setOnClickListener(v -> onClickSetGoalsButton());
        Toast.makeText(SetGoalsActivity.this, "set Goals", Toast.LENGTH_SHORT).show();

    }

    public void onClickSetGoalsButton(){
        try {

            int waterIntake = Integer.parseInt(setWaterIntake.getText().toString().trim());
            int calorieIntake = Integer.parseInt(setCalorieIntake.getText().toString().trim());
            int calorieBurn = Integer.parseInt(setCalorieBurn.getText().toString().trim());

            Calendar calendar = Calendar.getInstance();
            String year = Integer.toString(calendar.get(Calendar.YEAR));
            String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
            String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            String dateTime = year +"/"+month+"/"+day;

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("date",dateTime );
            userInfo.put("waterIntake", waterIntake);
            userInfo.put("calorieIntake", calorieIntake);
            userInfo.put("calorieBurn", calorieBurn);

            databaseReference.child("/" + userName + "/setGoals").setValue(userInfo);
            Toast.makeText(SetGoalsActivity.this, "Goals have been set", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(SetGoalsActivity.this, HomeActivity.class);
            startActivity(i);
        }
        catch (Exception e){
            Toast.makeText(SetGoalsActivity.this, "Value must be Integer", Toast.LENGTH_LONG).show();
        }
    }
    public void onClickBackButton(){
        Intent i = new Intent(SetGoalsActivity.this, HomeActivity.class);
        startActivity(i);
    }
}