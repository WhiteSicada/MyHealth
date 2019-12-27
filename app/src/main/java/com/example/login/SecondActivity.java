package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {


    //attributes
    private FirebaseAuth firebaseAuth;
    private TextView mydoctors , mymedicalfile , myconsultations;
    private EditText searchfordoctor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        searchfordoctor = (EditText)findViewById(R.id.etSearchForDoctor);
        mydoctors = (TextView)findViewById(R.id.tvWhatsAppDoctors);
        mymedicalfile = (TextView)findViewById(R.id.tvMedicalFile);
        myconsultations = (TextView)findViewById(R.id.tvConsultations);

        // when evver you click on my doctors -->espace list chatroom
        mydoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this , MyDoctorsList.class));
            }
        });

        /*mymedicalfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this , InfoMedicament.class));
            }
        });*/

        myconsultations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this , PatientConsultations.class));
            }
        });


    }

    // method to sign out
    private void signout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this , MainActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }


    // onclick event on menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            //if logout is clicked
            case R.id.logoutMenu:
                signout();
                break;
            case R.id.profileMenu:
                startActivity(new Intent(SecondActivity.this , ProfileActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
