package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Date;


public class RegistrationActivity extends AppCompatActivity {
    // attributes
    private EditText userName ,userPassword , userEmail , userAge ;
    private Button regButton ;
    private TextView userLogin;
    private ImageView userProfilePic ;
    private String name,password,age,email,usertype , usergender;
    private Spinner userGender , userType;
    private FirebaseAuth firebaseAuth ;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private static int PICK_IMAGE = 123 ;
    Uri imagePath ;

    // the main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        // if the user click on the button do this shit
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if the inputs are not empty
                if (validate()){
                    // get the values of email and password
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    //create the user based n email and password
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                //if the user is added successfully send his data to the database
                                sendUserData();
                                Toast.makeText(RegistrationActivity.this,"Registration successfully , Upload completed !" , Toast.LENGTH_SHORT).show();
                                finish();
                                firebaseAuth.signOut();
                                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                            }else {
                                Toast.makeText(RegistrationActivity.this,"registration failed",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });


        //if the user click on already registrated? login
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // rediret from   RegistrationActivity  to MainActivity
                startActivity(new Intent(RegistrationActivity.this , MainActivity.class));
            }
        });

        //when you click on an image to choose one
        userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent , "select image") , PICK_IMAGE);
            }
        });

        //when youclick on the spinner we take the value
        userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                usertype = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        userGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                usergender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //choose image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null)
        {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver() , imagePath);
                userProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // this function attach the design inputs with a variables
    private void setupUIViews()
    {
        userProfilePic = (ImageView)findViewById(R.id.ivProfilePic);
        userName = (EditText)findViewById(R.id.etUserName);
        userEmail = (EditText)findViewById(R.id.etUserEmail);
        userPassword = (EditText)findViewById(R.id.etUserPassword);
        userAge = (EditText) findViewById(R.id.etAge);
        // gender spinner
        userGender = (Spinner)findViewById(R.id.gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userGender.setAdapter(adapter);

        // type of user spinner
        userType = (Spinner)findViewById(R.id.typeofuser);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.typeofuser , android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(adapter1);

        regButton = (Button)findViewById(R.id.btnRegister);
        userLogin = (TextView) findViewById(R.id.tvUserLogin);
        // Initialize Firebase Auth , storage
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        // Create a storage reference from our app , specifies where the data will be stored
        storageReference = firebaseStorage.getReference();
    }

    // this function verify if the inputs are valid
    private boolean validate()
    {
        boolean result = false;
        name = userName.getText().toString().trim();
        email = userEmail.getText().toString().trim();
        password = userPassword.getText().toString().trim();
        age = userAge.getText().toString().trim();
        // if one or all of these variables are empty  --> error message
        if (name.isEmpty() || password.isEmpty() || email.isEmpty() || age.isEmpty() || imagePath == null || userGender == null || userGender.getSelectedItem() == null || userType == null || userType.getSelectedItem() == null)
        {
            Toast.makeText(this,"please enter all details" , Toast.LENGTH_SHORT).show();
        }else {
            result = true ;
        }
        return result;
    }

    private void sendEmailVerification()
    {
        //get the current user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        //if he exists
        if (firebaseUser!=null)
        {
            //send email verification
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //if the mail is sent successfully
                    if (task.isSuccessful())
                    {
                        sendUserData();
                        Toast.makeText(RegistrationActivity.this,"successfully Registrated , Verification mail sent !" , Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                    }else{
                        //if the mail isn't sent successfully
                        Toast.makeText(RegistrationActivity.this,"Verification mail hasn't been sent !" , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData()
    {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());

        // sending the user
        UserProfile userProfile = new UserProfile(age, email, name , usertype , usergender);
        myRef.child("General Informations").setValue(userProfile);

        // sending the image
        StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrationActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(RegistrationActivity.this, "Upload successful!", Toast.LENGTH_SHORT).show();
            }
        });

        /*String titlemaladie1 = "Grippe";
        String description_maladie1 = "La grippe est une maladie infectieuse due à un virus . ";

        String titlemaladie2 = "cancer poumons";
        String description_maladie2 = "cancer poumon dangereux . ";

        String titlemaladie3 = "VIH";
        String description_maladie3 = "AY VIH . ";

        UserMaladie userMaladie1 = new UserMaladie(titlemaladie1 , description_maladie1);
        UserMaladie userMaladie2 = new UserMaladie(titlemaladie2 , description_maladie2);
        UserMaladie userMaladie3 = new UserMaladie(titlemaladie3 , description_maladie3);


        myRef.child("Consultations").child("consultation 1").child("maladies").child("maladie 1").setValue(userMaladie1);
        myRef.child("Consultations").child("consultation 1").child("maladies").child("maladie 2").setValue(userMaladie2);
        myRef.child("Consultations").child("consultation 2").child("maladies").child("maladie 1").setValue(userMaladie3);*/


    }
}
