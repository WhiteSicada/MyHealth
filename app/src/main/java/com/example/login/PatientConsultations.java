package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PatientConsultations extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ListView listView ;
    private ArrayList<ConsultationListViewModel> userArrayList;
    private ConsultationAdapterModel adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_consultations);

        listView = (ListView) findViewById(R.id.list);
        userArrayList = new ArrayList<ConsultationListViewModel>();
        adapter = new ConsultationAdapterModel(PatientConsultations.this,userArrayList);
        listView.setAdapter(adapter);



        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myuser = firebaseDatabase.getReference().child(firebaseAuth.getUid());

        myuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile user = dataSnapshot.child("General Informations").getValue(UserProfile.class);
                if (user.getUserType().equals("Patient"))
                {
                    for (DataSnapshot consulation :  dataSnapshot.child("Consultations").getChildren())
                    {
                        String Consultationname = consulation.getKey().toString();
                        String date = consulation.child("Date").getValue().toString();
                        ConsultationListViewModel customListView = new ConsultationListViewModel(Consultationname,date);
                        userArrayList.add(customListView);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PatientConsultations.this, "we coudn't fetch infos from users from database" , Toast.LENGTH_SHORT).show();
            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PatientConsultations.this,InfoConsultation.class);
                intent.putExtra("consultation" , userArrayList.get(position).getNameconsultation().toString());
                startActivity(intent);

            }
        });
    }
    // method to sign out
    private void signout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(PatientConsultations.this , MainActivity.class));
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
                startActivity(new Intent(PatientConsultations.this , ProfileActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void getDataFromFirebase() {

    }
}
