package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PatientMaladie extends AppCompatActivity {



    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    private ListView listMeds;
    private ArrayList<MedModel> medArrayList;
    private MedAdapterModel adapterMed;


    private TextView NameDoctor , MaladieName , MaladieDegree , MaladieDescription;
    private Button getInTouch;
    private ImageView DoctorPhoto;
    private String doctoruid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_maladie);
        setup();
        loadDoctorImage();
        loadeverything();

        listMeds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String consultationnum = ConsultationNumber();
                final String maladienum = MaladieNumber();
                Intent intent = new Intent(PatientMaladie.this,MedicalInfo.class);
                intent.putExtra("consultation" , consultationnum);
                intent.putExtra("maladie" , maladienum);
                intent.putExtra("med" , medArrayList.get(position).getMednum().toString());
                startActivity(intent);

            }
        });
    }

    public void setup()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        NameDoctor = (TextView)findViewById(R.id.tvProfileNameDoctor);
        getInTouch = (Button)findViewById(R.id.getintouch);
        DoctorPhoto = (ImageView)findViewById(R.id.MyProfileImageDoctor);


        listMeds  = (ListView)findViewById(R.id.ListOfMeds);
        medArrayList = new ArrayList<MedModel>();
        adapterMed = new MedAdapterModel(PatientMaladie.this,medArrayList);
        listMeds.setAdapter(adapterMed);

        MaladieName = (TextView)findViewById(R.id.tvMaladieName);
        MaladieDegree = (TextView)findViewById(R.id.tvDegreeMaladie);
        MaladieDescription = (TextView)findViewById(R.id.tvDescriptionMaladie);
    }
    public void loadDoctorImage()
    {
        StorageReference storageReference = firebaseStorage.getReference();

        // idea is to create a node "doctors" that gonna contain doctors images , audio , video ..
        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //load the image in the imageview
                Picasso.get().load(uri).fit().centerCrop().into(DoctorPhoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PatientMaladie.this, "failed to get the image", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void loadeverything()
    {
        final String consultationnum = ConsultationNumber();
        final String maladienum = MaladieNumber();
        DatabaseReference databaseReferencecurrentuser = firebaseDatabase.getReference(firebaseAuth.getUid());
        DatabaseReference DoctorUid = databaseReferencecurrentuser.child("Consultations").child(consultationnum).child("Doctor");
        DoctorUid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctoruid = dataSnapshot.getValue().toString();
                setDoctorname(doctoruid);
                populatelistview(consultationnum,maladienum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void setDoctorname(String doctoruid)
    {
        DatabaseReference databaseReferenceDocotr = firebaseDatabase.getReference(doctoruid).child("General Informations");
        databaseReferenceDocotr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile doctor = dataSnapshot.getValue(UserProfile.class);
                NameDoctor.setText("Dr." + doctor.getUserName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public String ConsultationNumber()
    {
        String valuefromlastactivity = null;
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null)
        {
            valuefromlastactivity = bundle.getString("consultation") ;
        }
        return valuefromlastactivity;
    }
    public String MaladieNumber()
    {
        String valuefromlastactivity = null;
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null)
        {
            valuefromlastactivity = bundle.getString("maladie") ;
        }
        return valuefromlastactivity;
    }
    public void populatelistview(String consultationnum,String maladienum)
    {
        DatabaseReference refMaladies = firebaseDatabase.getReference(firebaseAuth.getUid()).child("Consultations").child(consultationnum).child("Maladies").child(maladienum);
        Log.d("maladienum : " , maladienum);
        DatabaseReference refMeds = refMaladies.child("Medicaments");
        refMaladies.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String maladiename = dataSnapshot.child("Title").getValue().toString();
                    Log.d("maladiename : " , maladiename);
                    String maladiedegree = dataSnapshot.child("Degree").getValue().toString();
                    Log.d("maladiedegree : " , maladiedegree);
                    String maladiedescription = dataSnapshot.child("Description").getValue().toString();
                    Log.d("maladiedescription : " , maladiedescription);
                    MaladieModelInfo customListView = new MaladieModelInfo(maladiename,maladiedegree,maladiedescription);
                    MaladieName.setText(customListView.getMaladiename());
                    MaladieDegree.setText(customListView.getMaladiedegree());
                    MaladieDescription.setText(customListView.getMaladiedescription());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PatientMaladie.this, "we coudn't fetch infos from users from database" , Toast.LENGTH_SHORT).show();
            }
        });
        refMeds.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot med :  dataSnapshot.getChildren())
                {
                    String mednum = med.getKey().toString();
                    Log.d("mednum : " , mednum);
                    String medtitle = med.child("Title").getValue().toString();
                    Log.d("medtitle : " , medtitle);
//                    String medduree = med.child("Duree").getValue().toString();
//                    String meddescription = med.child("Description").getValue().toString();
//                    String medaconsommer = med.child("Aconsommer").getValue().toString();
//                    boolean avantPtitDej = (boolean) med.child("Avantptitdej").getValue();
//                    boolean aprePtitDej = (boolean) med.child("Apresptitdej").getValue();
//                    boolean avantDej = (boolean) med.child("Avantdej").getValue();
//                    boolean apresDej = (boolean) med.child("Apresdej").getValue();
//                    boolean avantDinner = (boolean) med.child("Avantdinner").getValue();
//                    boolean apresDinner = (boolean) med.child("Apresdinner").getValue();
                    MedModel customMedListView = new MedModel(mednum,medtitle);
                    medArrayList.add(customMedListView);
                    adapterMed.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PatientMaladie.this, "we coudn't fetch infos from users from database" , Toast.LENGTH_SHORT).show();
            }
        });

    }
}
