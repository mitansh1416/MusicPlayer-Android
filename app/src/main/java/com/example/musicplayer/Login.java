package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {
    public static final int GOOGLE_SIGN_IN_CODE = 11416;
    TextView notregister,forgotpassword;
    EditText email,password;
    Button login;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    SignInButton googlesignin;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient signInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        notregister = findViewById(R.id.notregistered);
        forgotpassword = findViewById(R.id.forgotpassword);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar2);
        googlesignin = findViewById(R.id.googlesignin);
        fAuth = FirebaseAuth.getInstance();
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("581138984495-vslq5530h2nivbt769jl5jjp2hn67qeo.apps.googleusercontent.com").requestEmail().build();
        signInClient = GoogleSignIn.getClient(this,googleSignInOptions);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
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
                progressBar.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this,MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });
        notregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetmail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDailog = new AlertDialog.Builder(v.getContext());
                passwordResetDailog.setTitle("Reset Password ?");
                passwordResetDailog.setMessage("Enter Email.");
                passwordResetDailog.setView(resetmail);
                passwordResetDailog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       String mail = resetmail.getText().toString();
                       fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                               Toast.makeText(getApplicationContext(),"Reset Link Sent To Your Mail.",Toast.LENGTH_SHORT).show();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(getApplicationContext(),"Error ! Reset Link is Not Sent."+e.getMessage(),Toast.LENGTH_SHORT).show();
                           }
                       });
                    }
                });
                passwordResetDailog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDailog.create().show();
            }
        });
        googlesignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign = signInClient.getSignInIntent();
                startActivityForResult(sign,GOOGLE_SIGN_IN_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GOOGLE_SIGN_IN_CODE)
        {
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount signacc = signInAccountTask.getResult(ApiException.class);
                assert signacc != null;
                AuthCredential authCredential = GoogleAuthProvider.getCredential(signacc.getIdToken(),null);
                fAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error Logining In"+e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });



            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }
}
