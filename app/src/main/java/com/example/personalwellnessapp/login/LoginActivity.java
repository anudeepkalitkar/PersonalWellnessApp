package com.example.personalwellnessapp.login;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalwellnessapp.HomeActivity;
import com.example.personalwellnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth firebaseAuth;
    EditText emailEditText;
    EditText passwordEditText;
    Button loginBtn;
    TextView resetBtn;
    TextView createAccountBtn;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText =  findViewById(R.id.emailEditText);
        passwordEditText =  findViewById(R.id.passwordEditText);
        loginBtn =  findViewById(R.id.login_button);
        loginBtn.setOnClickListener(this);
        resetBtn =  findViewById(R.id.resetPwTextView);
        resetBtn.setOnClickListener(this);
        createAccountBtn =  findViewById(R.id.createAccTextView);
        createAccountBtn.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onClick(View v) {
        // default method for handling onClick Events..

        if(v.getId() == R.id.login_button) {
            loginUser();
        } else
        if(v.getId() == R.id.createAccTextView) {

                Intent i2 = new Intent(LoginActivity.this,
                        SignupActivity.class);
                startActivity(i2);
        }

       else if(v.getId() == R.id.resetPwTextView) {


                Intent i3 = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(i3);

        }
    }

    public void loginUser(){

        String email = emailEditText.getText().toString().trim();
        String password  = passwordEditText.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_LONG).show();
            return;
        }




        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                        // updateUI(user);
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        // updateUI(null);
                    }


                });

    }


}
