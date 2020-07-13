package com.example.musicplayer;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;


import android.content.Intent;

import android.os.Bundle;

import android.view.MenuItem;





import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView navigation;




    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String name,email,userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.bottom_nav);



        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        navigation.setOnNavigationItemSelectedListener(this);
        Load_fragment(new LibraryFragment());





        DocumentReference documentReference = fStore.collection("users").document(userId);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                    if (documentSnapshot != null) {
                        name = documentSnapshot.getString("username");
                        email = documentSnapshot.getString("email");
                    }


                }
            });

    }

    private boolean Load_fragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {

            case R.id.library:
                fragment = new LibraryFragment();
                break;
            case R.id.search:
               startActivity(new Intent(getApplicationContext(),search.class));
                break;
            case R.id.account:
                fragment = new AccountFragment();
                Bundle data = new Bundle();
                data.putString("name",name);
                data.putString("email",email);
                fragment.setArguments(data);


                break;
        }
        return Load_fragment(fragment);
    }
}