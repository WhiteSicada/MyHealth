package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.squareup.picasso.Target;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1 ;
    private RelativeLayout profilePic;
    private TextView profileName,profileAge,profileEmail,profileType,profileGender;
    private View Dialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private ImageView MyImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilePic = (RelativeLayout) findViewById(R.id.ivProfilePic);
        MyImage = (ImageView)findViewById(R.id.MyProfileImage);
        profileName = (TextView)findViewById(R.id.tvProfileName);
        profileAge = (TextView)findViewById(R.id.tvProfileAge);
        profileEmail = (TextView)findViewById(R.id.tvProfileEmail);
        profileType = (TextView)findViewById(R.id.tvProfileType);
        profileGender = (TextView)findViewById(R.id.tvProfileGender);
        Dialog = (View)findViewById(R.id.Dialog);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid()).child("General Informations");

        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //load the image in the imageview
                Picasso.get().load(uri).fit().centerCrop().into(MyImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this, "failed to get the image", Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //when ever data change in db
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                profileName.setText(userProfile.getUserName());
                profileAge.setText(userProfile.getUserAge());
                profileEmail.setText(userProfile.getUserEmail());
                profileGender.setText(userProfile.getUserGender());
                profileType.setText(userProfile.getUserType());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this , databaseError.getCode() , Toast.LENGTH_SHORT).show();
            }
        });



        Dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void openDialog()
    {
        DialogForProfilePage dialogForProfilePage = new DialogForProfilePage();
        dialogForProfilePage.show(getSupportFragmentManager(),"example");
    }
}
