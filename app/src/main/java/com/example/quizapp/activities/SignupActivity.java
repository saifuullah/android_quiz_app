package com.example.quizapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        mAuth = FirebaseAuth.getInstance();

        Button btnSignUp;
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            signUpUser();
            }
        });

        //When user click on Login....Already have an account
        TextView login;
        login = findViewById(R.id.btnLoginSignUp);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

    } // This is onCreate


    public void signUpUser(){
        EditText email=findViewById(R.id.fldSignUpEmail);
        String userEmail=email.getText().toString();

        EditText pass =findViewById(R.id.fldSignUpPassword);
        String password=pass.getText().toString();

        EditText passConfirm=findViewById(R.id.fldSignUpConfirmPassword);
        String confirmPassword=passConfirm.getText().toString();

        Log.i("SAIF", "pass: "+password);
        Log.i("SAIF", "Confirm_pass: "+confirmPassword);
        if (userEmail.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            Toast.makeText(getApplicationContext(), "Fields cannot be empty!", Toast.LENGTH_LONG).show();
            return;
        }
        if (!confirmPassword.equals(password)){
            Toast.makeText(getApplicationContext(), "Password and confirm password must be same!", Toast.LENGTH_LONG).show();
            return;
        }


        mAuth.createUserWithEmailAndPassword(userEmail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Sign up successful",Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(myIntent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Error Creating user!",Toast.LENGTH_SHORT).show();
                        }
                    }

                });

    }


}