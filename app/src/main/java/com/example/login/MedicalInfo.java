package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
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

public class MedicalInfo extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    private TextView NameDoctor , medname , meddescription , medduree , medconsommer;
    private Button getInTouch;
    private ImageView DoctorPhoto,medphoto,medptitdej,meddej,meddinner;
    private String doctoruid;
    private RadioButton AvantPtitDej , AvantDej , AvantDiner , ApresPtitDej , ApresDej , ApresDinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_info);

        setup();
        loadImage(DoctorPhoto);
        loadImage(medphoto);
        loadImage(medptitdej);
        loadImage(meddej);
        loadImage(meddinner);
        loadeverything();
    }




    public void setup()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        NameDoctor = (TextView)findViewById(R.id.tvProfileNameDoctor);
        getInTouch = (Button)findViewById(R.id.getintouch);
        DoctorPhoto = (ImageView)findViewById(R.id.MyProfileImageDoctor);

        medname = (TextView)findViewById(R.id.tvNameMed);
        meddescription = (TextView)findViewById(R.id.tvMedDescription);
        medduree = (TextView)findViewById(R.id.tvDureeTrait);
        medconsommer = (TextView)findViewById(R.id.tvAconsomer);

        medphoto = (ImageView)findViewById(R.id.imMed);
        medptitdej = (ImageView)findViewById(R.id.imPtitDej);
        meddej = (ImageView)findViewById(R.id.imDej);
        meddinner = (ImageView)findViewById(R.id.imDinner);

        AvantPtitDej = (RadioButton) findViewById(R.id.rdAvantPtitDej);
        AvantDej = (RadioButton) findViewById(R.id.rdAvantDej);
        AvantDiner = (RadioButton) findViewById(R.id.rdAvantDinner);
        ApresPtitDej = (RadioButton) findViewById(R.id.rdApresPtitDej);
        ApresDej = (RadioButton) findViewById(R.id.rdApresDej);
        ApresDinner = (RadioButton) findViewById(R.id.rdApresDinner);

    }
    public void loadImage(final ImageView someimage)
    {
        StorageReference storageReference = firebaseStorage.getReference();

        // idea is to create a node "doctors" that gonna contain doctors images , audio , video ..
        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //load the image in the imageview
                Picasso.get().load(uri).fit().centerCrop().into(someimage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MedicalInfo.this, "failed to get the image", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void loadeverything()
    {
        final String consultationnum = ConsultationNumber();
        final String maladienum = MaladieNumber();
        final String mednum = MedNumber();
        DatabaseReference databaseReferencecurrentuser = firebaseDatabase.getReference(firebaseAuth.getUid());
        DatabaseReference DoctorUid = databaseReferencecurrentuser.child("Consultations").child(consultationnum).child("Doctor");
        DoctorUid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctoruid = dataSnapshot.getValue().toString();
                setDoctorname(doctoruid);
                populatelistview(consultationnum,maladienum,mednum);
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
    public String MedNumber()
    {
        String valuefromlastactivity = null;
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null)
        {
            valuefromlastactivity = bundle.getString("med") ;
        }
        return valuefromlastactivity;
    }
    public void choufliradiobutton(String someshit , RadioButton someradio){
        if (someshit.equals("true"))
        {
            someradio.setChecked(true);
        }else {
            someradio.setChecked(false);
        }
    }
    public void populatelistview(String consultationnum,String maladienum , String mednum)
    {
        DatabaseReference refMaladies = firebaseDatabase.getReference(firebaseAuth.getUid()).child("Consultations").child(consultationnum).child("Maladies").child(maladienum);
        DatabaseReference refMeds = refMaladies.child("Medicaments").child(mednum);
        refMeds.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                medname.setText(dataSnapshot.child("Title").getValue().toString());
                meddescription.setText(dataSnapshot.child("Description").getValue().toString());
                medduree.setText(dataSnapshot.child("Duree").getValue().toString());
                medconsommer.setText(dataSnapshot.child("Aconsommer").getValue().toString());

                String avantPtitDej =  dataSnapshot.child("Avantptitdej").getValue().toString();
                Log.d("avantPtitDej",avantPtitDej);
                String aprePtitDej =  dataSnapshot.child("Apresptitdej").getValue().toString();
                String avantDej =  dataSnapshot.child("Avantdej").getValue().toString();
                String apresDej =  dataSnapshot.child("Apresdej").getValue().toString();
                String avantDinner =  dataSnapshot.child("Avantdinner").getValue().toString();
                String apresDinner =  dataSnapshot.child("Apresdinner").getValue().toString();

                choufliradiobutton(avantPtitDej,AvantPtitDej);
                choufliradiobutton(aprePtitDej,ApresPtitDej);
                choufliradiobutton(avantDej,AvantDej);
                choufliradiobutton(apresDej,ApresDej);
                choufliradiobutton(avantDinner,AvantDiner);
                choufliradiobutton(apresDinner,ApresDinner);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MedicalInfo.this, "we coudn't fetch infos from users from database" , Toast.LENGTH_SHORT).show();
            }
        });


    }
}
