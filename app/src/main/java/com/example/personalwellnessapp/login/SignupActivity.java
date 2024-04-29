package com.example.personalwellnessapp.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalwellnessapp.R;
import com.example.personalwellnessapp.SetGoalsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity{

    EditText signUpNameEditTxt;
    EditText signUpEmailEditTxt;
    EditText signupPasswordEditTxt;
    EditText signUpConfirmPasswordEditTxt;
    TextView returnToLoginTextV;
    Button signUpBtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth = FirebaseAuth.getInstance();
        signUpNameEditTxt = findViewById(R.id.signUpNameEditText);
        signUpEmailEditTxt =  findViewById(R.id.signUpEmailEditText);
        signupPasswordEditTxt =  findViewById(R.id.signUpPasswordEditText);
        signUpConfirmPasswordEditTxt =  findViewById(R.id.signUpConfirmPasswordEditText);
        returnToLoginTextV =  findViewById(R.id.loginReturnTextView);
        signUpBtn = findViewById(R.id.signUpButton);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        signUpBtn.setOnClickListener(v ->  createUserAccount());
        returnToLoginTextV.setOnClickListener(v-> {
            Intent i2 = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(i2);});
        createAuthStateListener();

    }



    public void createUserAccount(){
        final String userName = signUpNameEditTxt.getText().toString().trim();
        final String userEmail = signUpEmailEditTxt.getText().toString().trim();
        String password = signupPasswordEditTxt.getText().toString().trim();
        String confrimPassword = signUpConfirmPasswordEditTxt.getText().toString().trim();

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this,"Please enter Name",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(confrimPassword)){
            Toast.makeText(this,"Please enter confrim password", Toast.LENGTH_LONG).show();
            return;
        }
        if( !TextUtils.equals(password, confrimPassword)) {
            Toast.makeText(this,"passwords does not match", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(userEmail, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("name", userName);
                        userInfo.put("email", userEmail);
                        databaseReference.child("/"+userName).setValue(userInfo);

                        } else {
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                });
    }


    private void createAuthStateListener() {
        firebaseAuthListener = firebaseAuth -> {
            final String userName = signUpNameEditTxt.getText().toString().trim();
            final FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(userName)
                        .build();
                user.updateProfile(profileUpdates);
                Toast.makeText(SignupActivity.this, "Welcome!",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignupActivity.this, SetGoalsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }


}
