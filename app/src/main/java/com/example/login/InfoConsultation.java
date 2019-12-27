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

public class InfoConsultation extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    private ListView listMaladies ,listMeds;
    private ArrayList<MaladieListViewModel> maladieArrayList;
    private MaladieAdapterModel adapter;


    private TextView NameDoctor;
    private Button getInTouch;
    private ImageView DoctorPhoto;
    private String doctoruid;

    //private ArrayList<MedsListViewModel> medArrayList;
    //private MedAdapterModel adapterMed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_consultation);
        setup();
        loadDoctorImage();
        loadeverything();
        listMaladies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String consultationnum = ConsultationNumber();
                Intent intent = new Intent(InfoConsultation.this,PatientMaladie.class);
                intent.putExtra("maladie" , maladieArrayList.get(position).getMaladienum().toString());
                intent.putExtra("consultation" , consultationnum);
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
        //listMeds  = (ListView)findViewById(R.id.ListOfMeds);
        //medArrayList = new ArrayList<MedsListViewModel>();
        //adapterMed = new MedAdapterModel(InfoConsultation.this,medArrayList);
        //listMeds.setAdapter(adapterMed);
        listMaladies = (ListView)findViewById(R.id.listMaladies);
        maladieArrayList = new ArrayList<MaladieListViewModel>();
        adapter = new MaladieAdapterModel(InfoConsultation.this,maladieArrayList);
        listMaladies.setAdapter(adapter);

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
                Toast.makeText(InfoConsultation.this, "failed to get the image", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void loadeverything()
    {
        final String consultationnum = ConsultationNumber();
        DatabaseReference databaseReferencecurrentuser = firebaseDatabase.getReference(firebaseAuth.getUid());
        DatabaseReference DoctorUid = databaseReferencecurrentuser.child("Consultations").child(consultationnum).child("Doctor");
        DoctorUid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctoruid = dataSnapshot.getValue().toString();
                setDoctorname(doctoruid);
                populatelistview(consultationnum);
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
    public void populatelistview(String consultationnum)
    {
        DatabaseReference refMaladies = firebaseDatabase.getReference(firebaseAuth.getUid()).child("Consultations").child(consultationnum).child("Maladies");
        DatabaseReference refMeds = refMaladies.child("Medicaments");
        refMaladies.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot maladie :  dataSnapshot.getChildren())
                {
                    String maladiename = maladie.child("Title").getValue().toString();
                    String maladienumber = maladie.getKey().toString();
                    //String maladiedegree = maladie.child("Degree").getValue().toString();
                    //String maladiedescription = maladie.child("Description").getValue().toString();
                    MaladieListViewModel customListView = new MaladieListViewModel(maladiename,maladienumber);
                    maladieArrayList.add(customListView);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(InfoConsultation.this, "we coudn't fetch infos from users from database" , Toast.LENGTH_SHORT).show();
            }
        });
        /*refMeds.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot med :  dataSnapshot.getChildren())
                {
                    String medtitle = med.child("Title").getValue().toString();
                    String medduree = med.child("Duree").getValue().toString();
                    String meddescription = med.child("Description").getValue().toString();
                    String medaconsommer = med.child("Aconsommer").getValue().toString();
                    boolean avantPtitDej = (boolean) med.child("Avantptitdej").getValue();
                    boolean aprePtitDej = (boolean) med.child("Apresptitdej").getValue();
                    boolean avantDej = (boolean) med.child("Avantdej").getValue();
                    boolean apresDej = (boolean) med.child("Apresdej").getValue();
                    boolean avantDinner = (boolean) med.child("Avantdinner").getValue();
                    boolean apresDinner = (boolean) med.child("Apresdinner").getValue();
                    MedsListViewModel customMedListView = new MedsListViewModel(medtitle,meddescription,medduree,medaconsommer,avantPtitDej,aprePtitDej,avantDej,apresDej,avantDinner,apresDinner);
                    medArrayList.add(customMedListView);
                    adapterMed.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(InfoConsultation.this, "we coudn't fetch infos from users from database" , Toast.LENGTH_SHORT).show();
            }
        });*/

    }
}
