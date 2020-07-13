package com.example.musicplayer;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText email,password,username;
    Button register;
    TextView alreadyregistered;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        username = findViewById(R.id.Username);
        register = findViewById(R.id.login);
        alreadyregistered = findViewById(R.id.alreadyregistered);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(Register.this,MainActivity.class));
            finish();
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                final String Username = username.getText().toString();
                if(TextUtils.isEmpty(Email))
                {
                  email.setError("Email is Required");
                  return;
                }
                if(TextUtils.isEmpty(Password))
                {
                    password.setError("Password is Required");
                    return;
                }
                if(Password.length()<8)
                {
                   password.setError("Password Must be >= 8 Characters");
                   return;
                }
                if(TextUtils.isEmpty(Username))
                {
                    username.setError("Username is Required");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful())
                         {
                             Toast.makeText(getApplicationContext(),"User Created.",Toast.LENGTH_SHORT).show();
                            final String  userID = fAuth.getCurrentUser().getUid();
                             DocumentReference documentReference = fStore.collection("users").document(userID);
                             Map<String,Object> user = new HashMap<>();
                             user.put("username",Username);
                             user.put("email",Email);
                              documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                  @Override
                                  public void onSuccess(Void aVoid) {
                                      Log.d("TAG", "onSuccess: user Profile is created for "+ userID);
                                  }
                              }).addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {
                                      Log.d("TAG", "onFailure: " + e.toString());
                                  }
                              });


                             startActivity(new Intent(Register.this,MainActivity.class));
                             finish();
                         }
                         else
                         {
                             Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                             progressBar.setVisibility(View.GONE);
                         }
                    }
                });


            }
        });
        alreadyregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
                finish();
            }
        });

    }
}
