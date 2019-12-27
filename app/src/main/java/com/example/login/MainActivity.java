package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText Name , Password ;
    private TextView info , userRegistration;
    private Button Login;
    private int counter = 5 ;
    private ProgressDialog progressDialog ;
    private FirebaseAuth firebaseAuth;
    private TextView forgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUIViews();

        // the attemps we have max 5
        info.setText("No of attemps remaining : " + String.valueOf(counter));
        Login.setText("Go");
        // fire base shit
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        // if the user already logged in --> go to second activity
        if (user != null)
        {
            finish();
            startActivity(new Intent(MainActivity.this , SecondActivity.class));
        }


        // if the user click the button execute Validate function below
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString() , Password.getText().toString());
            }
        });


        // if the user want to create an account go to this page
        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , RegistrationActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , PasswordActivity.class));
            }
        });

    }


    // bind vars with objects in the design
    private void setupUIViews()
    {
        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        info = (TextView)findViewById(R.id.tvInfo);
        Login = (Button)findViewById(R.id.btnLogin);
        userRegistration = (TextView)findViewById(R.id.tvRegister);
        forgotPassword = (TextView)findViewById(R.id.tvForgotPassword);
    }

    // takes username password and checks
    private void validate(String userName , String userPassword){

        // progress dialog in the mean time the red=gistration takes place
        progressDialog.setMessage("smile you shit");
        progressDialog.show();



        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    // if login is successful destroy progress dialog
                    progressDialog.dismiss();
                    //check the email
                    //checkEmailVerification();
                    startActivity(new Intent(MainActivity.this , SecondActivity.class));
                }else {
                    // if login is failed destroy progress dialog
                    progressDialog.dismiss();
                    // decrement the counter
                    counter--;
                    //String.valueOf(counter) --> cnvert an int to a string
                    info.setText("No of attempts remaining : " + String.valueOf(counter));
                    // afficher login failed
                    Toast.makeText(MainActivity.this , "Login failed" , Toast.LENGTH_SHORT).show();
                    // if the counter reaches 0 --> disable the button
                    if (counter == 0){
                        Login.setEnabled(false);
                    }
                }
            }
        });

    }

    private void checkEmailVerification()
    {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        boolean emailflag = firebaseUser.isEmailVerified();

        if(emailflag)
        {
            finish();
            startActivity(new Intent(MainActivity.this , SecondActivity.class));
        }else{
            Toast.makeText(MainActivity.this , "Verify your email" , Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }


    }
}
