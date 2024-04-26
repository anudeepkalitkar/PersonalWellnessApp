package com.example.personalwellnessapp;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    EditText signUpNameEditTxt;
    EditText signUpEmailEditTxt;
    EditText signUppasswordEditTxt;
    EditText signUpConfirmPasswordEditTxt;
    TextView returnToLoginTextV;
    Button signUpBtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth = FirebaseAuth.getInstance();

        signUpNameEditTxt = (EditText) findViewById(R.id.signUpNameEditText);
        signUpEmailEditTxt = (EditText) findViewById(R.id.signUpEmailEditText);
        signUppasswordEditTxt = (EditText) findViewById(R.id.signUpPasswordEditText);
        signUpConfirmPasswordEditTxt = (EditText) findViewById(R.id.signUpConfirmPasswordEditText);
        returnToLoginTextV = (TextView) findViewById(R.id.loginReturnTextView);
        returnToLoginTextV.setOnClickListener(this);
        signUpBtn = (Button) findViewById(R.id.signUpButton);
        signUpBtn.setOnClickListener(this);

        createAuthStateListener();

    }


    @Override
    public void onClick(View v) {
        // default method for handling onClick Events..
      if(v.getId() == R.id.signUpButton) {

                createUserAccount();
      }  if(v.getId() == R.id.loginReturnTextView) {

                Intent i2 = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i2);
        }
    }


    public void createUserAccount(){
        final String createName = signUpNameEditTxt.getText().toString().trim();
        final String createEmail = signUpEmailEditTxt.getText().toString().trim();
        String createPassword = signUppasswordEditTxt.getText().toString().trim();
        String createConfrimPassword = signUpConfirmPasswordEditTxt.getText().toString().trim();



        if(TextUtils.isEmpty(createEmail)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(createPassword)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_LONG).show();
            return;
        }


        firebaseAuth.createUserWithEmailAndPassword(createEmail, createPassword)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(SignupActivity.this, "Welcome!",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                });

    }


    private void createAuthStateListener() {
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(SignupActivity.this, ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
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
