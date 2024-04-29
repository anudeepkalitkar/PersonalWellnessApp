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

public class AddMoodActivity extends AppCompatActivity {
    Button backButton, addMoodButton;
    EditText descBoxValue;
    RadioGroup selectMoodButton;
    RadioButton happyButton, sadButton, sleepyButton, coolButton, depressedButton, scaredButton, neutralButton, sickButton, angryButton ;
    String userName;
    int moodValue;
    DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        if(user!=null) {
            userName = user.getDisplayName();
        }
        selectMoodButton = findViewById(R.id.selectMood);
        happyButton = findViewById(R.id.happy);
        sadButton = findViewById(R.id.sad);
        sleepyButton = findViewById(R.id.sleepy);
        coolButton = findViewById(R.id.cool);
        depressedButton = findViewById(R.id.depressed);
        scaredButton = findViewById(R.id.scared);
        neutralButton = findViewById(R.id.neutral);
        sickButton = findViewById(R.id.sick);
        angryButton = findViewById(R.id.angry);

        descBoxValue = findViewById(R.id.descBox);
        addMoodButton = findViewById(R.id.add_mood);
        backButton = findViewById(R.id.back_add_mood_button);
        backButton.setOnClickListener(v -> onClickBackButton());
        addMoodButton.setOnClickListener(v -> onClickAddMoodButton());
        selectMoodButton.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == happyButton.getId())
                moodValue = 1;
            else if (checkedId == sadButton.getId())
                moodValue = 2;
            else if (checkedId == sleepyButton.getId())
                moodValue = 3;
            else if (checkedId == coolButton.getId())
                moodValue = 4;
            else if (checkedId == depressedButton.getId())
                moodValue = 5;
            else if (checkedId == scaredButton.getId())
                moodValue = 6;
            else if (checkedId == neutralButton.getId())
                moodValue = 7;
            else if (checkedId == sickButton.getId())
                moodValue = 8;
            else if (checkedId == angryButton.getId())
                moodValue = 9;
        });
    }
    public void onClickAddMoodButton(){
        String caloriesIntake = descBoxValue.getText().toString().trim();

        Calendar calendar = Calendar.getInstance();
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        String dateTime = year +"/"+month+"/"+day;

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("date",dateTime );
        userInfo.put("because", caloriesIntake);
        userInfo.put("mood", moodValue);

        databaseReference.child("/" + userName + "/mood").push().setValue(userInfo);
        Toast.makeText(getApplicationContext(), "Mood  Added", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(AddMoodActivity.this, HomeActivity.class);
        startActivity(i);
    }
    public void onClickBackButton(){
        Intent i = new Intent(AddMoodActivity.this, HomeActivity.class);
        startActivity(i);
    }
}