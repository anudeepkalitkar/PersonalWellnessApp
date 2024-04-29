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

public class AddWaterActivity extends AppCompatActivity {

    EditText waterIntakeValue;
    Button backButton, addWaterButton;
    String userName;
    DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_water);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        if(user!=null) {
            userName = user.getDisplayName();
        }
        waterIntakeValue = findViewById(R.id.water_consumed);
        backButton = findViewById(R.id.back_water_button_button);
        addWaterButton = findViewById(R.id.add_water_button);
        backButton.setOnClickListener(v -> onClickBackButton());
        addWaterButton.setOnClickListener(v -> onClickAddWaterButton());
    }
    public void onClickAddWaterButton(){
        try {

            int waterIntake = Integer.parseInt(waterIntakeValue.getText().toString().trim());

            Calendar calendar = Calendar.getInstance();
            String year = Integer.toString(calendar.get(Calendar.YEAR));
            String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
            String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            String dateTime = year +"/"+month+"/"+day;

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("date",dateTime );
            userInfo.put("gallons", waterIntake);

            databaseReference.child("/" + userName + "/waterIntake").push().setValue(userInfo);
            Toast.makeText(getApplicationContext(), "Water intake Added", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(AddWaterActivity.this, HomeActivity.class);
            startActivity(i);
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Value must be Integer", Toast.LENGTH_LONG).show();

        }
    }

    public void onClickBackButton(){
        Intent i = new Intent(AddWaterActivity.this, HomeActivity.class);
        startActivity(i);
    }

}
