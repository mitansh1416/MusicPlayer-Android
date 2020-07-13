package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
   ImageView asset_1;
   TextView asset_2;
   Animation top_anim,bottom_anim;
   FirebaseAuth fAuth;
   GoogleSignInAccount signInAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        fAuth = FirebaseAuth.getInstance();
        asset_1=findViewById(R.id.asset_1);
        asset_2=findViewById(R.id.asset_2);
        top_anim= AnimationUtils.loadAnimation(this,R.anim.background_asset_1_animation);
        bottom_anim=AnimationUtils.loadAnimation(this,R.anim.background_asset_2_animation);
        asset_1.setAnimation(top_anim);
        asset_2.setAnimation(bottom_anim);
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {

               if(fAuth.getCurrentUser() != null || signInAccount !=null){
                   startActivity(new Intent(SplashScreen.this,MainActivity.class));
                   finish();
               }
               else {
                   startActivity(new Intent(SplashScreen.this, Login.class));
                   finish();
               }
           }
       },3200);




    }
}
