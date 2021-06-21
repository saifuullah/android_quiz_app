package com.example.quizapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.quizapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginIntro extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_intro);

        mAuth = FirebaseAuth.getInstance();


        if(mAuth.getCurrentUser() != null){
            Toast.makeText(this, "Already Logged in!", Toast.LENGTH_SHORT).show();
            redirect("MAIN");
        }

        Button btnstart;
        btnstart = findViewById(R.id.btnGetStarted);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirect("LOGIN");
            }
        });
    }



    public void redirect(String name){
        if ( name == "MAIN"){
            Intent myIntent = new Intent(LoginIntro.this, MainActivity.class);
            startActivity(myIntent);
            finish();
        }//if

        else if(name == "LOGIN"){
            Intent myIntent = new Intent(LoginIntro.this, LoginActivity.class);
            startActivity(myIntent);
            finish();
        }//else if
    }
}