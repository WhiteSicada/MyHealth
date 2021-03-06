package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    private EditText emailRecapPassword;
    private Button btnRecapPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        emailRecapPassword = (EditText)findViewById(R.id.etRecapPasswordWithEmail);
        btnRecapPassword = (Button)findViewById(R.id.btnRecapPasswordWithEmail);
        firebaseAuth = FirebaseAuth.getInstance();

        btnRecapPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = emailRecapPassword.getText().toString().trim();

                if (useremail.equals(""))
                {
                    Toast.makeText(PasswordActivity.this , "Please enter your registration email" , Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(PasswordActivity.this , "Password reset email sent !" , Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PasswordActivity.this,MainActivity.class));
                            }else{
                                Toast.makeText(PasswordActivity.this , "Error in sending password reset" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
